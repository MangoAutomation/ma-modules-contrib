/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.currencyConverter;

import com.infiniteautomation.mango.currencyConverter.vo.CurrencyConverterDataSourceVO;
import com.serotonin.m2m2.i18n.ProcessResult;
import com.serotonin.m2m2.module.PollingDataSourceDefinition;
import com.serotonin.m2m2.vo.DataPointVO;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;

/**
 * @author Pier Puccini
 *
 */
public class CurrencyConverterDataSourceDefinition extends PollingDataSourceDefinition<CurrencyConverterDataSourceVO> {

    public static final String TYPE_NAME = "CURRENCY_CONVERT";

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
        return "cc.datasource.description";
    }

    @Override
    public CurrencyConverterDataSourceVO createDataSourceVO() {
        return new CurrencyConverterDataSourceVO();
    }

    /**
     * Validate the data source's settings, called when saving or updating this type of data source
     * @param response - where to store the validation errors
     * @param ds - the data source to validate
     */
    @Override
    public void validate(ProcessResult response, CurrencyConverterDataSourceVO ds) {

    }

    /**
     * Validate the point locator's settings, this is called when saving or updating a data point
     */
    @Override
    public void validate(ProcessResult response, DataPointVO dpvo, DataSourceVO dsvo) {
        //Ensure the data point should belong to this type of data source
        if (!(dsvo instanceof CurrencyConverterDataSourceVO)) {
            response.addContextualMessage("dataSourceId", "dpEdit.validate.invalidDataSourceType");
        }
    }


}
