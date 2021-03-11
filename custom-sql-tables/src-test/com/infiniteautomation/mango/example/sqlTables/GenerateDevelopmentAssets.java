/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables;

import java.util.HashMap;
import java.util.Map;

import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAssetVO;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;

public class GenerateDevelopmentAssets extends GenerateDevelopmentConfiguration {

    @Override
    protected Map<String, Object> generateImpl() {
        createData();
        Map<String, Object> data = new HashMap<>();
        data.put(ExampleAssetEmportDefinition.ELEMENT_ID, assetService.buildQuery().query());
        return data;
    }

    public void createData() {
        //First we need an example Site
        ExampleSiteVO site = new ExampleSiteVO();
        site.setName("Example site");
        siteService.insert(site);

        ExampleAssetVO asset1 = new ExampleAssetVO();
        asset1.setName("Example asset 1");
        asset1.setSiteId(site.getId());
        assetService.insert(asset1);

        ExampleAssetVO asset2 = new ExampleAssetVO();
        asset2.setName("Example asset 2");
        asset2.setSiteId(site.getId());
        assetService.insert(asset2);
    }

    @Override
    protected String getFilename() {
        return "dev-assets.json";
    }
}
