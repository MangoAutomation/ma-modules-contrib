/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.systemSettings.vo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.infiniteautomation.mango.systemSettings.rt.SystemSettingsDataSourceRT;
import com.serotonin.json.spi.JsonProperty;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.util.ExportCodes;
import com.serotonin.m2m2.vo.dataSource.PollingDataSourceVO;
import com.serotonin.m2m2.vo.event.EventTypeVO;
import com.serotonin.util.SerializationHelper;

/**
 * Example VO for polling system settings values.
 *
 * @author Terry Packer
 *
 */
public class SystemSettingsDataSourceVO extends PollingDataSourceVO {

    /*
     * Store the event codes in a final static block to share across all of this type of VO
     */
    private static final ExportCodes EVENT_CODES = new ExportCodes();
    static {
        EVENT_CODES.addElement(SystemSettingsDataSourceRT.POLL_ABORTED_EVENT, POLL_ABORTED);
        EVENT_CODES.addElement(SystemSettingsDataSourceRT.DATA_SOURCE_EXCEPTION, "DATA_SOURCE_EXCEPTION");
    }

    @JsonProperty
    private String url;
    @JsonProperty
    private String token;
    @JsonProperty
    private int timeoutMs;
    @JsonProperty
    private int retries;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTimeoutMs() {
        return timeoutMs;
    }

    public void setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /*
     * Add any event types here
     */
    @Override
    protected void addEventTypes(List<EventTypeVO> ets) {
        super.addEventTypes(ets);
    }

    /*
     * All polling data sources can generate a 'Poll Aborted' event
     */
    @Override
    public int getPollAbortedExceptionEventId() {
        return SystemSettingsDataSourceRT.POLL_ABORTED_EVENT;
    }

    /*
     * Used in the RuntimeManager to start an instance of the data source
     *  based on this configuration.
     */
    @Override
    public SystemSettingsDataSourceRT createDataSourceRT() {
        return new SystemSettingsDataSourceRT(this);
    }

    /*
     * Used internally when creating new points
     */
    @Override
    public SystemSettingsPointLocatorVO createPointLocator() {
        return new SystemSettingsPointLocatorVO();
    }

    /*
     * This will show up in the data source list of the UI to describe
     *  the data source based on its settings.
     */
    @Override
    public TranslatableMessage getConnectionDescription() {
        return new TranslatableMessage("systemSettings.datasource.connectionDescription", url);
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
    private static final int version = 1;

    /*
     * Write the settings to a stream that goes to the database
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(version);
        SerializationHelper.writeSafeUTF(out, url);
        SerializationHelper.writeSafeUTF(out, token);
        out.writeInt(timeoutMs);
        out.writeInt(retries);
    }

    /*
     * Read the settings from a stream out of the database
     */
    private void readObject(ObjectInputStream in) throws IOException {
        int version = in.readInt();
        if(version == 1) {
            url = SerializationHelper.readSafeUTF(in);
            token = SerializationHelper.readSafeUTF(in);
            timeoutMs = in.readInt();
            retries = in.readInt();
        }
    }

}
