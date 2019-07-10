/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.example.vo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.infiniteautomation.mango.example.ExamplePollingDataSourceDefinition;
import com.infiniteautomation.mango.example.rt.ExamplePollingDataSourceRT;
import com.serotonin.m2m2.i18n.ProcessResult;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.module.license.DataSourceTypePointsLimit;
import com.serotonin.m2m2.rt.dataSource.DataSourceRT;
import com.serotonin.m2m2.util.ExportCodes;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;
import com.serotonin.m2m2.vo.dataSource.PointLocatorVO;
import com.serotonin.m2m2.vo.dataSource.PollingDataSourceVO;
import com.serotonin.m2m2.vo.event.EventTypeVO;

/**
 * Example VO for polling data sources.  This data source 
 * does nothing more than poll and log a message.
 * 
 * @author Terry Packer
 *
 */
public class ExamplePollingDataSourceVO extends PollingDataSourceVO<ExamplePollingDataSourceVO> {
    
    /*
     * Store the event codes in a final static block to share across all of this type of VO
     */
    private static final ExportCodes EVENT_CODES = new ExportCodes();
    static {
        EVENT_CODES.addElement(ExamplePollingDataSourceRT.POLL_ABORTED_EVENT, POLL_ABORTED);
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
        return ExamplePollingDataSourceRT.POLL_ABORTED_EVENT;
    }

    /*
     * Used in the RuntimeManager to start an instance of the data source
     *  based on this configuration.
     */
    @Override
    public DataSourceRT<? extends DataSourceVO<?>> createDataSourceRT() {
        return new ExamplePollingDataSourceRT(this);
    }

    /*
     * Used internally when creating new points
     */
    @Override
    public PointLocatorVO<?> createPointLocator() {
        return new ExamplePointLocatorVO();
    }

    /*
     * This will show up in the data source list of the UI to describe 
     *  the data source based on its settings.
     */
    @Override
    public TranslatableMessage getConnectionDescription() {
        return new TranslatableMessage("example.datasource.polling.connectionDescription", xid);
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
     * Validate all settings, ensure you let the superclass
     * validate its settings via the super.validate call.
     */
    @Override
    public void validate(ProcessResult response) {
        super.validate(response);
        //Check the licensing limits, if the response has any problems the data source cannot be saved
        DataSourceTypePointsLimit.checkLimit(ExamplePollingDataSourceDefinition.TYPE_NAME, response);
    }
    
    /*
     * Serialization is used to write the 'data' portion of the
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
    }

    /*
     * Read the settings from a stream out of the database
     */
    private void readObject(ObjectInputStream in) throws IOException {
        in.readInt();
    }

}
