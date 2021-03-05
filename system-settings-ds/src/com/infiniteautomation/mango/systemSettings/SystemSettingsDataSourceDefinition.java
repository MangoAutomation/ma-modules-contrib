/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.systemSettings;

import com.infiniteautomation.mango.systemSettings.vo.SystemSettingsDataSourceVO;
import com.serotonin.m2m2.i18n.ProcessResult;
import com.serotonin.m2m2.module.PollingDataSourceDefinition;
import com.serotonin.m2m2.vo.DataPointVO;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

/**
 * @author Terry Packer
 *
 */
public class SystemSettingsDataSourceDefinition extends PollingDataSourceDefinition<SystemSettingsDataSourceVO> {

    public static final String TYPE_NAME = "EXAMPLE_SYSTEM_SETTINGS";

    /*
     * One of the lifecycle hooks we can use to initialize and configure our module
     */
    @Override
    public void preInitialize() {

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
    public SystemSettingsDataSourceVO createDataSourceVO() {
        return new SystemSettingsDataSourceVO();
    }

    /**
     * Validate the data source's settings, called when saving or updating this type of data source
     * @param response - where to store the validation errors
     * @param ds - the data source to validate
     * @param user - the saving permission holder
     */
    @Override
    public void validate(ProcessResult response, SystemSettingsDataSourceVO ds, PermissionHolder user) {

    }

    /**
     * Validate the point locator's settings, this is called when saving or updating a data point
     */
    @Override
    public void validate(ProcessResult response, DataPointVO dpvo, DataSourceVO dsvo, PermissionHolder user) {
        //Ensure the data point should belong to this type of data source
        if (!(dsvo instanceof SystemSettingsDataSourceVO)) {
            response.addContextualMessage("dataSourceId", "dpEdit.validate.invalidDataSourceType");
        }
    }


}
