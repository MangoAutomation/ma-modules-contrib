/**
 * Copyright 2021 Radix IoT Inc.
 * https://www.radixiot.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

const testHelper = require('@infinite-automation/mango-module-tools/test-helper/testHelper');
const {createClient, uuid, defer, delay, assertValidationErrors, login} = testHelper;
const client = createClient();

describe('Example sites ', function() {
    before('Login', function() { return login.call(this, client); });

     function newSite(){
        return {
            xid: uuid(),
            name: 'Test site',
            readPermission: ['anonymous', 'user'],
            editPermission: ['user'],
            data: 'test'
          };
    };

    function assertSite(saved, local){
        assert.equal(saved.xid, local.xid);
        assert.equal(saved.name, local.name);

        assert.lengthOf(saved.readPermission, local.readPermission.length);
        for(let i=0; i<saved.readPermission.length; i++)
            assert.include(local.readPermission, saved.readPermission[i]);

        assert.lengthOf(saved.editPermission, local.editPermission.length);
        for(let i=0; i<saved.editPermission.length; i++)
            assert.include(local.editPermission, saved.editPermission[i]);

        assert.equal(saved.data, local.data);
    }
    
    it('Create site', () => {
    const site = newSite();
      return client.restRequest({
          path: '/rest/latest/example-sites',
          method: 'POST',
          data: site
      }).then((response) => {
          assertSite(response.data, site);
      }).finally(() => {
        return client.restRequest({
             path: `/rest/latest/example-sites/${site.xid}`,
             method: 'DELETE'
          });
      });
    });

    it('Update site', () => {
    const site = newSite();
      return client.restRequest({
          path: '/rest/latest/example-sites',
          method: 'POST',
          data: site
      }).then((response) => {
          assertSite(response.data, site);
          //Modify a little
          site.name = 'New name';
          return client.restRequest({
                path: `/rest/latest/example-sites/${site.xid}`,
                method: 'PUT',
                data: site
          }).then((response) => {
                assertSite(response.data, site);
          });
      }).finally(() => {
        return client.restRequest({
             path: `/rest/latest/example-sites/${site.xid}`,
             method: 'DELETE'
          });
      });
    });

    it('Gets websocket notifications for update', function() {

        let ws;
        const subscription = {
            eventTypes: ['add', 'delete', 'update']
        };

        const socketOpenDeferred = defer();
        const listUpdatedDeferred = defer();

        const site = newSite();

        return Promise.resolve().then(() => {
            ws = client.openWebSocket({
                path: '/rest/latest/websocket/example-sites'
            });

            ws.on('open', () => {
                socketOpenDeferred.resolve();
            });

            ws.on('error', error => {
                const msg = new Error(`WebSocket error, error: ${error}`);
                socketOpenDeferred.reject(msg);
                listUpdatedDeferred.reject(msg);
            });

            ws.on('close', (code, reason) => {
                const msg = new Error(`WebSocket closed, code: ${code}, reason: ${reason}`);
                socketOpenDeferred.reject(msg);
                listUpdatedDeferred.reject(msg);
            });

            ws.on('message', msgStr => {
                try{
                    assert.isString(msgStr);
                    const msg = JSON.parse(msgStr);
                    if(msg.payload.action === 'update') {
                        assert.strictEqual(msg.status, 'OK');
                        assert.strictEqual(msg.payload.object.xid, site.xid);
                        listUpdatedDeferred.resolve();
                    }
                }catch(e){
                    listUpdatedDeferred.reject(e);
                }
            });

            return socketOpenDeferred.promise;
        }).then(() => {
            const send = defer();
            ws.send(JSON.stringify(subscription), error => {
                if (error != null) {
                    send.reject(error);
                } else {
                    send.resolve();
                }
            });
            return send.promise;

        }).then(() => delay(1000)).then(() => {
            //TODO Fix DaoNotificationWebSocketHandler so we can remove this delay, only required for cold start
            return client.restRequest({
                path: `/rest/latest/example-sites`,
                method: 'POST',
                data: site
            }).then(response => {
                site.name = 'new name';
                return client.restRequest({
                    path: `/rest/latest/example-sites/${site.xid}`,
                    method: 'PUT',
                    data: site
                });
            });
        }).then(() => listUpdatedDeferred.promise).then((r)=>{
            ws.close();
            return r;
        },e => {
            ws.close();
            return Promise.reject(e);
        });
    });

});
