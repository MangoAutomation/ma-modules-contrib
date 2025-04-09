/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.dailystockprice;

import java.io.IOException;

import com.infiniteautomation.mango.dailystockprice.rt.DailyStockPriceDataSourceRT.PolledValues;
import com.infiniteautomation.mango.dailystockprice.vo.DailyStockPriceDataSourceVO;
import com.infiniteautomation.mango.dailystockprice.vo.DailyStockPricePointLocatorVO;
import com.serotonin.m2m2.i18n.ProcessResult;
import com.serotonin.m2m2.module.PollingDataSourceDefinition;
import com.serotonin.m2m2.rt.dataImage.types.DataValue;
import com.serotonin.m2m2.vo.DataPointVO;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;

public class DailyStockPriceDataSourceDefinition extends PollingDataSourceDefinition<DailyStockPriceDataSourceVO> {

    public static final String TYPE_NAME = "DAILY_STOCK_PRICE";

    /*
     * One of the lifecycle hooks we can use to initialize and configure our module
     */
    @Override
    public void preInitialize() { }

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
        return "dsp.datasource.description";
    }


    @Override
    public DailyStockPriceDataSourceVO createDataSourceVO() {
        return new DailyStockPriceDataSourceVO();
    }


    /**
     * Validate the data source's settings, called when saving or updating this type of data source
     * @param response - where to store the validation errors
     * @param dsvo - the data source to validate
     */
    @Override
    public void validate(ProcessResult response, DailyStockPriceDataSourceVO dsvo) {

        try {
            validateDS(null, dsvo);

        } catch (Throwable e) {
            response.addContextualMessage("dataSourceId", "dpEdit.validate.invalidDataSourceType");
        }
    }


    /**
     * Validate the point locator's settings, this is called when saving or updating a data point
     */
    @Override
    public void validate(ProcessResult response, DataPointVO dpvo, DataSourceVO dsvo) {

        if (!(dsvo instanceof DailyStockPriceDataSourceVO)) {
            response.addContextualMessage("dataSourceId", "dpEdit.validate.invalidDataSourceType");

        } else {

            try {
                validateDS(dpvo, dsvo);

            } catch (Throwable e) {
                response.addContextualMessage("dataSourceId", "dpEdit.validate.invalidDataSourceType");
            }
        }
    }


    private DataValue validateDS(DataPointVO dpvo, DataSourceVO dsvo) throws IOException {
        DailyStockPriceDataSourceVO ds = (DailyStockPriceDataSourceVO) dsvo;
        DailyStockPricePointLocatorVO pl;

        if (dpvo != null) {
            pl = dpvo.getPointLocator();

        } else {
            pl = new DailyStockPricePointLocatorVO();
            pl.setStockSymbol("AAPL");
        }

        PolledValues pv = new PolledValues();
        pv.init(ds);
        pv.newPoll();

        return pv.getValue(pl);
    }
}
