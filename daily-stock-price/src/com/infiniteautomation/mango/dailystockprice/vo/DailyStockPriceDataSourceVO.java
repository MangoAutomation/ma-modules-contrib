/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.dailystockprice.vo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.infiniteautomation.mango.dailystockprice.rt.DailyStockPriceDataSourceRT;
import com.serotonin.json.spi.JsonProperty;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.util.ExportCodes;
import com.serotonin.m2m2.vo.dataSource.PollingDataSourceVO;
import com.serotonin.m2m2.vo.event.EventTypeVO;
import com.serotonin.util.SerializationHelper;

/**
 * This datasouce uses this free API
 * <a href="https://www.alphavantage.co/documentation/#latestprice">
 *     https://www.alphavantage.co/documentation/#latestprice
 * </a> to get the daily prices of stocks.
 */
public class DailyStockPriceDataSourceVO extends PollingDataSourceVO {

    //Events that can be generated
    public static final int POLL_ABORTED_EVENT = 1;
    public static final int DATA_SOURCE_EXCEPTION_EVENT = 2;
    /*
     * Store the event codes in a final static block to share across all of this type of VO
     */
    private static final ExportCodes EVENT_CODES = new ExportCodes();
    static {
        EVENT_CODES.addElement(POLL_ABORTED_EVENT, POLL_ABORTED);
        EVENT_CODES.addElement(DATA_SOURCE_EXCEPTION_EVENT, "DATA_SOURCE_EXCEPTION");
    }
    private static final int VERSION = 1;

    @JsonProperty
    private String apiKey = "";

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(VERSION);
        // Order is important, meaning that you must read in the order to write
        SerializationHelper.writeSafeUTF(out, apiKey);
    }

    private void readObject(ObjectInputStream in) throws IOException {
        int version = in.readInt();
        if (version == 1){
            // In case version one is to be modified
            apiKey = SerializationHelper.readSafeUTF(in);
        }
    }

    /*
     * Used in the RuntimeManager to start an instance of the data source
     *  based on this configuration.
     */
    @Override
    public DailyStockPriceDataSourceRT createDataSourceRT() {
        return new DailyStockPriceDataSourceRT(this);
    }

    /*
     * Used internally when creating new points
     */
    @Override
    public DailyStockPricePointLocatorVO createPointLocator() {
        return new DailyStockPricePointLocatorVO();
    }

    /*
     * This will show up in the data source list of the UI to describe
     *  the data source based on its settings.
     */
    @Override
    public TranslatableMessage getConnectionDescription() {
        return new TranslatableMessage("dsp.datasource.polling.connectionDescription", xid);
    }

    /*
     * Add any event types here
     */
    @Override
    protected void addEventTypes(List<EventTypeVO> ets) {
        super.addEventTypes(ets);
        ets.add(createEventType(DATA_SOURCE_EXCEPTION_EVENT, new TranslatableMessage("event.ds.dataSource")));
    }

    /*
     * All polling data sources can generate a 'Poll Aborted' event
     */
    @Override
    public int getPollAbortedExceptionEventId() {
        return POLL_ABORTED_EVENT;
    }

    /*
     * Return all event codes for this data source,
     * this is used in the UI
     */
    @Override
    public ExportCodes getEventCodes() {
        return EVENT_CODES;
    }

    /*
     * Serialization is used to write the 'data' column of the
     * database table.  It is very important to add a version even
     * if you only serialize that, it ensures that in the future
     * you can easily add/remove settings.
     */
    private static final long serialVersionUID = 1L;
}
