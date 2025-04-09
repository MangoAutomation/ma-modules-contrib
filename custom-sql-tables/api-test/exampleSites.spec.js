/**
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

const {
    createClient,
    uuid,
    defer,
    delay,
    login
} = require('@infinite-automation/mango-module-tools/test-helper/testHelper');
const client = createClient();

describe('Example sites ', function () {
    before('Login', function () {
        return login.call(this, client);
    });

    function newSite() {
        return {
            xid: uuid(),
            name: 'Test site',
            readPermission: ['anonymous', 'user'],
            editPermission: ['user'],
            data: 'test'
        };
    };

    function assertSite(saved, local) {
        assert.equal(saved.xid, local.xid);
        assert.equal(saved.name, local.name);

        assert.lengthOf(saved.readPermission, local.readPermission.length);
        for (let i = 0; i < saved.readPermission.length; i++)
            assert.include(local.readPermission, saved.readPermission[i]);

        assert.lengthOf(saved.editPermission, local.editPermission.length);
        for (let i = 0; i < saved.editPermission.length; i++)
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

    it('Gets websocket notifications for update', function () {

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
                try {
                    assert.isString(msgStr);
                    const msg = JSON.parse(msgStr);
                    if (msg.payload.action === 'update') {
                        assert.strictEqual(msg.status, 'OK');
                        assert.strictEqual(msg.payload.object.xid, site.xid);
                        listUpdatedDeferred.resolve();
                    }
                } catch (e) {
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
        }).then(() => listUpdatedDeferred.promise).then((r) => {
            ws.close();
            return r;
        }, e => {
            ws.close();
            return Promise.reject(e);
        });
    });

});
