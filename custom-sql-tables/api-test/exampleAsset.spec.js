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

describe('Example assets ', function() {
    before('Login', function() { return login.call(this, client); });

     function newSite(){
        return {
            xid: uuid(),
            name: 'Test site with asset',
            readPermission: ['anonymous', 'user'],
            editPermission: ['user'],
            data: 'test'
          };
    };

    function newAsset(siteXid){
            return {
                xid: uuid(),
                name: 'Test asset',
                readPermission: ['anonymous', 'user'],
                editPermission: ['user'],
                siteXid: siteXid,
                data: 'test'
              };
    };

    function assertAsset(saved, local){
        assert.equal(saved.xid, local.xid);
        assert.equal(saved.name, local.name);

        assert.lengthOf(saved.readPermission, local.readPermission.length);
        for(let i=0; i<saved.readPermission.length; i++)
            assert.include(local.readPermission, saved.readPermission[i]);

        assert.lengthOf(saved.editPermission, local.editPermission.length);
        for(let i=0; i<saved.editPermission.length; i++)
            assert.include(local.editPermission, saved.editPermission[i]);

        assert.equal(saved.siteXid, local.siteXid);
        assert.equal(saved.data, local.data);
    }
    
    it('Create asset', () => {
      const site = newSite();
      let asset = null;
      return client.restRequest({
          path: '/rest/latest/example-sites',
          method: 'POST',
          data: site
      }).then((response) => {
      debugger;
          const site = response.data;
          asset = newAsset(site.xid);
          return client.restRequest({
                    path: '/rest/latest/example-assets',
                    method: 'POST',
                    data: asset
                }).then((response) => {
                    assertAsset(response.data, asset);
                });
      }).finally(() => {
          //Deleting the site will delete the assets on that site
          return client.restRequest({
                path: `/rest/latest/example-sites/${site.xid}`,
                method: 'DELETE'
          })
      });
    });
});
