/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.example.rt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.infiniteautomation.mango.example.vo.ExamplePollingDataSourceVO;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.rt.dataImage.DataPointRT;
import com.serotonin.m2m2.rt.dataImage.PointValueTime;
import com.serotonin.m2m2.rt.dataImage.SetPointSource;
import com.serotonin.m2m2.rt.dataSource.PollingDataSource;

/**
 * @author Terry Packer
 *
 */
public class ExamplePollingDataSourceRT extends PollingDataSource<ExamplePollingDataSourceVO> {

    protected final Log LOG = LogFactory.getLog(ExamplePollingDataSourceRT.class);

    //Events that can be generated
    public static final int POLL_ABORTED_EVENT = 1;
    public static final int DATA_SOURCE_EXCEPTION_EVENT = 2;
    
    public ExamplePollingDataSourceRT(ExamplePollingDataSourceVO vo) {
        super(vo);
    }

    /*
     * The poll logic, before a poll all new data points are made 
     *  available to use.
     */
    @Override
    protected void doPoll(long scheduledPollTime) {
        raiseEvent(DATA_SOURCE_EXCEPTION_EVENT, System.currentTimeMillis(), true, new TranslatableMessage(
                "literal", "Event Raised"));
    }

    /*
     * When a point is set externally (via the UI) this method is called 
     *  to allow the data source to perform the set and handle the result/error
     */
    @Override
    public void setPointValueImpl(DataPointRT dprt, PointValueTime valueToSet, SetPointSource source) {
        
    }
    
}
