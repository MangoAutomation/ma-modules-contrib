/**
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

const {createClient, uuid, login} = require('@infinite-automation/mango-module-tools/test-helper/testHelper');
const client = createClient();

describe('Example assets ', function () {
    before('Login', function () {
        return login.call(this, client);
    });

    function newSite() {
        return {
            xid: uuid(),
            name: 'Test site with asset',
            readPermission: ['anonymous', 'user'],
            editPermission: ['user'],
            data: 'test'
        };
    }

    function newAsset(siteXid) {
        return {
            xid: uuid(),
            name: 'Test asset',
            readPermission: ['anonymous', 'user'],
            editPermission: ['user'],
            siteXid: siteXid,
            data: 'test'
        };
    }

    function assertAsset(saved, local) {
        assert.equal(saved.xid, local.xid);
        assert.equal(saved.name, local.name);

        assert.lengthOf(saved.readPermission, local.readPermission.length);
        for (let i = 0; i < saved.readPermission.length; i++)
            assert.include(local.readPermission, saved.readPermission[i]);

        assert.lengthOf(saved.editPermission, local.editPermission.length);
        for (let i = 0; i < saved.editPermission.length; i++)
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
            });
        });
    });
});
