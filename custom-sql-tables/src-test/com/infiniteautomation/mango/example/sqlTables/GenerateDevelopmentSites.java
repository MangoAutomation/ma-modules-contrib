/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables;

import java.util.HashMap;
import java.util.Map;

import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;

public class GenerateDevelopmentSites extends GenerateDevelopmentConfiguration {

    @Override
    protected Map<String, Object> generateImpl() {
        createData();
        Map<String, Object> data = new HashMap<>();
        data.put(ExampleSiteEmportDefinition.ELEMENT_ID, siteService.buildQuery().query());
        return data;
    }

    public void createData() {
        ExampleSiteVO vo = new ExampleSiteVO();
        vo.setName("Example site");
        siteService.insert(vo);
    }

    @Override
    protected String getFilename() {
        return "dev-sites.json";
    }
}
