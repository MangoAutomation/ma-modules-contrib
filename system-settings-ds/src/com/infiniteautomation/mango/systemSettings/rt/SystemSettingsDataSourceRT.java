/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.systemSettings.rt;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.protocol.BasicHttpContext;
import org.apache.hc.core5.http.protocol.HttpContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infiniteautomation.mango.spring.MangoRuntimeContextConfiguration;
import com.infiniteautomation.mango.systemSettings.vo.SystemSettingsDataSourceVO;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.rt.dataImage.DataPointRT;
import com.serotonin.m2m2.rt.dataImage.PointValueTime;
import com.serotonin.m2m2.rt.dataImage.SetPointSource;
import com.serotonin.m2m2.rt.dataImage.types.DataValue;
import com.serotonin.m2m2.rt.dataSource.PollingDataSource;

/**
 * @author Terry Packer
 *
 */
public class SystemSettingsDataSourceRT extends PollingDataSource<SystemSettingsDataSourceVO> implements HttpClientResponseHandler<Void> {

    protected final Log LOG = LogFactory.getLog(SystemSettingsDataSourceRT.class);

    //Events that can be generated
    public static final int POLL_ABORTED_EVENT = 1;
    public static final int DATA_SOURCE_EXCEPTION = 2;

    private final ObjectMapper mapper;
    private CloseableHttpClient client;
    private HttpGet request;
    private boolean failed;

    public SystemSettingsDataSourceRT(SystemSettingsDataSourceVO vo) {
        super(vo);
        this.mapper = Common.getBean(ObjectMapper.class, MangoRuntimeContextConfiguration.REST_OBJECT_MAPPER_NAME);
    }

    @Override
    public void initialize() {
        super.initialize();
        //TODO Support HTTPS connections via SSL
        this.client = Common.getHttpClient(vo.getTimeoutMs() * 1000, vo.getRetries());
        this.request = new HttpGet(vo.getUrl());
        this.request.setHeader(new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + vo.getToken()));
    }

    /*
     * The poll logic, before a poll all new data points are made 
     *  available to use.
     */
    @Override
    protected void doPoll(long scheduledPollTime) {
        if(dataPoints.size() > 0) {
            //Create client to make rest request
            try {
                HttpContext localContext = new BasicHttpContext();
                localContext.setAttribute("TIMESTAMP", scheduledPollTime);
                client.execute(this.request, localContext, this);

                //Only RTN active events for performance, we won't get here if an exception is thrown
                if(failed) {
                    failed = false;
                    returnToNormal(DATA_SOURCE_EXCEPTION, System.currentTimeMillis());

                    //All good, set points to reliable
                    for(DataPointRT dprt : dataPoints) {
                        dprt.setAttribute(ATTR_UNRELIABLE_KEY, false);
                    }
                }
            }catch(IOException e) {
                failed = true;
                if(LOG.isDebugEnabled()) {
                    LOG.debug("Error making request.", e);
                }
                raiseEvent(DATA_SOURCE_EXCEPTION, scheduledPollTime, true, new TranslatableMessage(
                        "systemSettings.dataSource.exceptionEvent", e.getMessage()));
                //Set all data points to be unreliable
                for(DataPointRT dprt : dataPoints) {
                    dprt.setAttribute(ATTR_UNRELIABLE_KEY, true);
                }
            }
        }
    }

    /*
     * When a point is set externally (via the UI) this method is called 
     *  to allow the data source to perform the set and handle the result/error
     */
    @Override
    public void setPointValueImpl(DataPointRT dprt, PointValueTime valueToSet, SetPointSource source) {
        //TODO use HttpPost to set settings
    }

    @Override
    public Void handleResponse(ClassicHttpResponse httpResponse) throws ParseException, IOException {

        //Make request
        HttpEntity responseEntity = httpResponse.getEntity();
        String responseBody = EntityUtils.toString(responseEntity);

        //Process response
        JsonNode root = mapper.readTree(responseBody);

        //TODO get poll time from HttpContext

        //Set the point values
        for(DataPointRT rt : dataPoints) {
            SystemSettingsPointLocatorRT plrt = rt.getPointLocator();
            try {
                DataValue value = plrt.getValue(root);
                rt.updatePointValue(new PointValueTime(value, Common.timer.currentTimeMillis()));
            }catch(Exception e) {
                //TODO Wire in point read failed event type
            }
        }
        return null;
    }
}
