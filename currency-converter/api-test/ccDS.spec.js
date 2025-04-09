/**
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

const {createClient, login} = require('@radixiot/mango-module-tools/test-helper/testHelper');
const client = createClient();

describe('Currency converter data source ', function () {
    before('Login', function () {
        return login.call(this, client);
    });

    const ds = {
        xid: 'DS_EX_TEST',
        name: 'Example Test',
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
        editPermission: ['superadmin'],
        pollPeriod: {
            periods: 5,
            type: 'SECONDS'
        },
        modelType: 'EXAMPLE_POLLING'
    };

    it('Create data source', () => {
        return client.restRequest({
            path: '/rest/latest/data-sources',
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
        ds.name = 'Test again';
        return client.restRequest({
            path: `/rest/latest/data-sources/${ds.xid}`,
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
            path: `/rest/latest/data-sources/${ds.xid}`,
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
