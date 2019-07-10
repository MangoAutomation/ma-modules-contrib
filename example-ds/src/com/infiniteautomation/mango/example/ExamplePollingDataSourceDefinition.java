/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.example;

import com.infiniteautomation.mango.example.vo.ExamplePollingDataSourceVO;
import com.serotonin.m2m2.module.DataSourceDefinition;
import com.serotonin.m2m2.module.ModuleRegistry;
import com.serotonin.m2m2.module.license.DataSourceTypePointsLimit;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;

/**
 * @author Terry Packer
 *
 */
public class ExamplePollingDataSourceDefinition extends DataSourceDefinition {
    
    public static final String TYPE_NAME = "EXAMPLE_POLLING";
    
    /*
     * One of the lifecycle hooks we can use to initialize and configure our module
     */
    @Override
    public void preInitialize(boolean install, boolean upgrade) { 
        //Here we add a restriction that says if this module is unlicensed, you can only create 2 points
        ModuleRegistry.addLicenseEnforcement(new DataSourceTypePointsLimit(getModule().getName(), TYPE_NAME, 2, null));
    }
    
    /*
     * This is used to uniquely describe the data source type internally in Mango
     */
    @Override
    public String getDataSourceTypeName() {
        return TYPE_NAME;
    }

    /*
     * This description shows up in the menu when selecting a data source
     */
    @Override
    public String getDescriptionKey() {
        return "example.datasource.polling.description";
    }

    @Override
    public DataSourceVO<?> createDataSourceVO() {
        return new ExamplePollingDataSourceVO();
    }

    /*
     * N/A legacy UI
     */
    @Override
    public String getEditPagePath() {
        return null;
    }

    /*
     * N/A leagcy UI
     */
    @Override
    public Class<?> getDwrClass() {
        return null;
    }
}