/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

const testHelper = require('@radixiot/mango-module-tools/test-helper/testHelper');
const {createClient, uuid, assertValidationErrors, login} = testHelper;
const client = createClient();
const { DataSource, DataPoint } = client;

describe('Twitter data source', function () {
    before('Login', function () {
        return login.call(this, client);
    });

    it('Create data source', () => {
        const ds = newDataSource();
        const local = Object.assign({}, ds);
        return ds.save().then(saved => {
            testHelper.assertDataSource(saved, local, assertDataSourceAttributes);
        }, error => {
            assertValidationErrors([''], error);
        }).finally(() => {
            return ds.delete();
        });
    });

    it('Update data source', () => {
        const ds = newDataSource();
        const local = Object.assign({}, ds);
        return ds.save().then(saved => {
            testHelper.assertDataSource(saved, local, assertDataSourceAttributes);
            //Make changes
            saved.name = uuid();
            saved.polling = true;
            saved.consumerKey = 'dummy2';
            saved.consumerSecret = 'dummy2';
            saved.token = 'dummy2';
            saved.secret = 'dummy2';
            //add additional fields and modify them
            const localUpdate = Object.assign({}, saved);
            return saved.save().then(updated => {
                testHelper.assertDataSource(updated, localUpdate, assertDataSourceAttributes);
            });
        }, error => {
            assertValidationErrors([''], error);
        }).finally(() => {
            return ds.delete();
        });
    });

    it('Delete data source', () => {
        const ds = newDataSource();
        return ds.save().then(saved => {
            testHelper.assertDataSource(ds, saved, assertDataSourceAttributes);
            return ds.delete().then(() => {
                return DataSource.get(saved.xid).then(notFound => {
                    assert.fail('Should not have found ds ' + notFound.xid);
                }, error => {
                    assert.strictEqual(404, error.status);
                });
            });
        });
    });

    it('Create data point', () => {
        const ds = newDataSource();
        return ds.save().then(saved => {
            testHelper.assertDataSource(ds, saved, assertDataSourceAttributes);
            const dp = newDataPoint(ds.xid);
            return dp.save().then(saved => {
                testHelper.assertDataPoint(saved, dp, assertPointLocator);
            }, error => {
                assertValidationErrors([''], error);
            });
        }).finally(() => {
            return ds.delete();
        });
    });

    it('Update data point', () => {
        const ds = newDataSource();
        return ds.save().then(saved => {
            testHelper.assertDataSource(ds, saved, assertDataSourceAttributes);
            const dp = newDataPoint(ds.xid);
            const local = Object.assign({}, dp);
            return dp.save().then(saved => {
                testHelper.assertDataPoint(saved, local, assertPointLocator);
                saved.pointLocator.tweetFilter = ['#1', '#2'];
                const localUpdate = Object.assign({}, saved);
                return saved.save().then(updated => {
                    return DataPoint.get(updated.xid).then(found => {
                        testHelper.assertDataPoint(found, localUpdate, assertPointLocator);
                    });
                });
            });
        }).finally(() => {
            return ds.delete();
        });
    });

    it('Delete data point', () => {
        const ds = newDataSource();
        return ds.save().then(saved => {
            testHelper.assertDataSource(ds, saved, assertDataSourceAttributes);
            const dp = newDataPoint(ds.xid);
            return dp.save().then(saved => {
                testHelper.assertDataPoint(saved, dp, assertPointLocator);
                return saved.delete().then(() => {
                    return DataPoint.get(saved.xid).then(notFound => {
                        assert.fail('Should not have found point ' + notFound.xid);
                    }, error => {
                        assert.strictEqual(404, error.status);
                    });
                });
            });
        }).finally(() => {
            return ds.delete();
        });
    });

    function newDataPoint(dsXid) {
        return new DataPoint({
            dataSourceXid: dsXid,
            pointLocator: {
                dataType: 'ALPHANUMERIC',
                settable: true,
                modelType: 'PL.TWITTER',
                tweetFilter: ['#one', '#two']
            }
        });
    }

    function newDataSource() {
        return new DataSource({
            pollPeriod: {
                periods: 5,
                type: 'SECONDS'
            },
            quantize: true,
            useCron: false,
            editPermission: [],
            eventAlarmLevels: [
                {
                    eventType: 'POLL_ABORTED',
                    level: 'INFORMATION',
                },
                {
                    eventType: 'TWITTER_API_FAILURE',
                    level: 'URGENT',
                }
            ],
            polling: true,
            consumerKey: 'dummy',
            consumerSecret: 'dummy',
            token: 'dummy',
            secret: 'dummy',
            modelType: 'TWITTER_DS'
        });
    }

    function assertDataSourceAttributes(saved, local) {
        assert.strictEqual(saved.polling, local.polling);
        assert.strictEqual(saved.pollPeriod.periods, local.pollPeriod.periods);
        assert.strictEqual(saved.pollPeriod.type, local.pollPeriod.type);
        assert.strictEqual(saved.quantize, local.quantize);
        assert.strictEqual(saved.useCron, local.useCron);
        //add assertions for the added fields
        assert.strictEqual(saved.consumerKey, local.consumerKey);
        assert.strictEqual(saved.consumerSecret, local.consumerSecret);
        assert.strictEqual(saved.token, local.token);
        assert.strictEqual(saved.secret, local.secret);
    }

    function assertPointLocator(saved, local) {
        assert.strictEqual(saved.dataType, local.dataType);
        assert.isArray(saved.tweetFilter);
        assert.strictEqual(saved.tweetFilter.length, local.tweetFilter.length);
        for (let i = 0; i < saved.tweetFilter.length; i++) {
            assert.strictEqual(saved.tweetFilter[i], local.tweetFilter[i]);
        }
    }
});
