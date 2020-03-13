/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.example;

import com.infiniteautomation.mango.example.vo.ExamplePollingDataSourceVO;
import com.serotonin.m2m2.i18n.ProcessResult;
import com.serotonin.m2m2.module.ModuleRegistry;
import com.serotonin.m2m2.module.PollingDataSourceDefinition;
import com.serotonin.m2m2.module.license.DataSourceTypePointsLimit;
import com.serotonin.m2m2.vo.DataPointVO;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

/**
 * @author Terry Packer
 *
 */
public class ExamplePollingDataSourceDefinition extends PollingDataSourceDefinition<ExamplePollingDataSourceVO> {

    public static final String TYPE_NAME = "EXAMPLE_POLLING";

    /*
     * One of the lifecycle hooks we can use to initialize and configure our module
     */
    @Override
    public void preInitialize() {
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
    public ExamplePollingDataSourceVO createDataSourceVO() {
        return new ExamplePollingDataSourceVO();
    }

    /**
     * Validate the data source's settings, called when saving or updating this type of data source
     * @param response - where to store the validation errors
     * @param ds - the data source to validate
     * @param user - the saving permission holder
     */
    @Override
    public void validate(ProcessResult response, ExamplePollingDataSourceVO ds, PermissionHolder user) {
        DataSourceTypePointsLimit.checkLimit(ExamplePollingDataSourceDefinition.TYPE_NAME, response);
    }

    /**
     * Validate the point locator's settings, this is called when saving or updating a data point
     */
    @Override
    public void validate(ProcessResult response, DataPointVO dpvo, DataSourceVO dsvo, PermissionHolder user) {

        //Ensure the data point should belong to this type of data source
        if (!(dsvo instanceof ExamplePollingDataSourceVO)) {
            response.addContextualMessage("dataSourceId", "dpEdit.validate.invalidDataSourceType");
        }

        //Check the licensing limits, if the response has any problems the data source cannot be saved
        DataSourceTypePointsLimit.checkLimit(ExamplePollingDataSourceDefinition.TYPE_NAME, response);
    }


}