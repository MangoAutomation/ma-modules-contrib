/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.systemSettings;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.infiniteautomation.mango.systemSettings.vo.SystemSettingsDataSourceVO;
import com.infiniteautomation.mango.systemSettings.vo.SystemSettingsPointLocatorVO;
import com.serotonin.m2m2.db.dao.SystemSettingsDao;
import com.serotonin.m2m2.i18n.ProcessResult;
import com.serotonin.m2m2.module.PollingDataSourceDefinition;
import com.serotonin.m2m2.vo.DataPointVO;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;

/**
 * @author Terry Packer
 *
 */
public class SystemSettingsDataSourceDefinition extends PollingDataSourceDefinition<SystemSettingsDataSourceVO> {

    public static final String TYPE_NAME = "EXAMPLE_SYSTEM_SETTINGS";

    @Autowired
    private SystemSettingsDao systemSettingsDao;

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
        return "systemSettings.datasource.description";
    }

    @Override
    public SystemSettingsDataSourceVO createDataSourceVO() {
        return new SystemSettingsDataSourceVO();
    }

    /**
     * Validate the data source's settings, called when saving or updating this type of data source
     * @param response - where to store the validation errors
     * @param ds - the data source to validate
     */
    @Override
    public void validate(ProcessResult response, SystemSettingsDataSourceVO ds) {

        //TODO Validate URL points to something real?

        if(StringUtils.isEmpty(ds.getUrl())) {
            response.addContextualMessage("url", "validate.required");
        }

        if(StringUtils.isEmpty(ds.getToken())) {
            response.addContextualMessage("token", "validate.required");
        }

        if(ds.getTimeoutMs() < 0) {
            response.addContextualMessage("timeoutMs", "validate.greaterThan", 0);
        }

        if(ds.getRetries() < 0) {
            response.addContextualMessage("retries", "validate.greaterThan", 0);
        }

    }

    /**
     * Validate the point locator's settings, this is called when saving or updating a data point
     */
    @Override
    public void validate(ProcessResult response, DataPointVO dpvo, DataSourceVO dsvo) {
        //Ensure the data point should belong to this type of data source
        if (!(dsvo instanceof SystemSettingsDataSourceVO)) {
            response.addContextualMessage("dataSourceId", "dpEdit.validate.invalidDataSourceType");
        }

        SystemSettingsPointLocatorVO pl = dpvo.getPointLocator();

        //validate that the setting exists, it may actually be set to null but that is very unlikely
        if(systemSettingsDao.getValue(pl.getSettingKey()) == null) {
            response.addContextualMessage("settingKey", "dpEdit.validate.invalidDataSourceType");
        }

        //validate that the type exists
        if (!SystemSettingsPointLocatorVO.SETTING_TYPE_CODES.isValidId(pl.getSettingType())) {
            response.addContextualMessage("settingType", "validate.invalidValue");
        }
    }


}
