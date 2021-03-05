/**
 * Copyright 2019 Infinite Automation Systems Inc.
 * http://infiniteautomation.com/
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

const config = require('@infinite-automation/mango-client/test/setup');

describe('System settings data source ', function() {
    before('Login', config.login);

    const ds = {
            xid: 'DS_SS_TEST',
            name: 'System Settings Test',
            enabled: false,
            eventAlarmLevels: [
                {
                    eventType: 'POLL_ABORTED',
                    level: 'INFORMATION',
                 }
            ],
            purgeSettings: {
                override: true,
                frequency: {
                    periods: 7,
                    type: 'DAYS'
                }
            },
            editPermission: ['superadmin', 'test'],
            pollPeriod: {
                periods: 5,
                type: 'SECONDS'
            },
            modelType: 'EXAMPLE_SYSTEM_SETTINGS'
    };
    
    it('Create data source', () => {
      return client.restRequest({
          path: '/rest/v2/data-sources',
          method: 'POST',
          data: ds
      }).then((response) => {
          assert.isNumber(response.data.id);
          assert.strictEqual(response.data.xid, ds.xid);
          assert.strictEqual(response.data.name, ds.name);
          assert.strictEqual(response.data.enabled, ds.enabled);
          assert.strictEqual(response.data.pollPeriod.periods, ds.pollPeriod.periods);
          assert.strictEqual(response.data.pollPeriod.type, ds.pollPeriod.type);
      });
    });

    it('Update data source', () => {
        ds.name='Test again';
        return client.restRequest({
            path:  `/rest/v2/data-sources/${ds.xid}`,
            method: 'PUT',
            data: ds
        }).then((response) => {
            assert.isNumber(response.data.id);
            assert.strictEqual(response.data.xid, ds.xid);
            assert.strictEqual(response.data.name, ds.name);
            assert.strictEqual(response.data.enabled, ds.enabled);
            assert.strictEqual(response.data.pollPeriod.periods, ds.pollPeriod.periods);
            assert.strictEqual(response.data.pollPeriod.type, ds.pollPeriod.type);
        });
      });
    
    it('Delete example data source', () => {
        return client.restRequest({
            path: `/rest/v2/data-sources/${ds.xid}`,
            method: 'DELETE',
            data: {}
        }).then(response => {
            assert.strictEqual(response.data.xid, ds.xid);
            assert.strictEqual(response.data.name, ds.name);
            assert.strictEqual(response.data.enabled, ds.enabled);
            assert.strictEqual(response.data.pollPeriod.periods, ds.pollPeriod.periods);
            assert.strictEqual(response.data.pollPeriod.type, ds.pollPeriod.type);
        });
    });
});
