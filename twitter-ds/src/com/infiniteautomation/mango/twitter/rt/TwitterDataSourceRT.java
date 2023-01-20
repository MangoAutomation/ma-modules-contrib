/*
 * Copyright (C) 2021 RadixIot LLC. All rights reserved.
 */
package com.infiniteautomation.mango.twitter.rt;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infiniteautomation.mango.spring.MangoRuntimeContextConfiguration;
import com.infiniteautomation.mango.twitter.TwitterClientTools;
import com.infiniteautomation.mango.twitter.vo.TwitterDataSourceVO;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.rt.dataImage.DataPointRT;
import com.serotonin.m2m2.rt.dataImage.PointValueTime;
import com.serotonin.m2m2.rt.dataImage.SetPointSource;
import com.serotonin.m2m2.rt.dataImage.types.AlphanumericValue;
import com.serotonin.m2m2.rt.dataSource.PollingDataSource;

/**
 * @author Benjamin Perez
 */
public class TwitterDataSourceRT extends PollingDataSource<TwitterDataSourceVO> {

    private final Logger log = LoggerFactory.getLogger(TwitterDataSourceRT.class);

    //Events that can be generated
    public static final int POLL_ABORTED_EVENT = 1;

    public static final int TWITTER_API_FAILURE_EVENT = 2;

    private boolean errorState = true;

    private ObjectMapper mapper;


    public TwitterDataSourceRT(TwitterDataSourceVO vo) {
        super(vo);
    }

    @Override public void initialize() {
        super.initialize();
        mapper = Common.getBean(ObjectMapper.class,
                MangoRuntimeContextConfiguration.COMMON_OBJECT_MAPPER_NAME);
    }

    @Override public void addDataPointImpl(DataPointRT dataPoint) {
        TwitterPointLocatorRT plrt = dataPoint.getPointLocator();
        plrt.initialize(this);
        addDataPointImpl(dataPoint, true);
    }

    /*
     * The poll logic, before a poll all new data points are made
     *  available to use.
     */
    @Override protected void doPoll(long scheduledPollTime) {
        // TODO Auto-generated method stub
        if (dataPoints.size() > 0) {

            for (DataPointRT dataPointRT : dataPoints) {
                TwitterPointLocatorRT twitterPointLocatorRT = dataPointRT.getPointLocator();


                switch (twitterPointLocatorRT.getVo().getDataType()) {
                    case NUMERIC:
                    case BINARY:
                    case MULTISTATE:
                    //case DataType.IMAGE:
                    default:
                        break;
                    case ALPHANUMERIC:
                        if (!twitterPointLocatorRT.getClient().isDone()) {
                            String msg;
                            try {
                                msg = twitterPointLocatorRT.getMsgQueue()
                                        .poll(pollingPeriodMillis - 10, TimeUnit.MILLISECONDS);
                                if (msg != null) {
                                    //JsonNode actualObj = mapper.readTree(msg);
                                    msg = TwitterClientTools.getTweet(msg, mapper);
                                    //msg = actualObj.get("text").asText();
                                    dataPointRT.updatePointValue(
                                            new PointValueTime(new AlphanumericValue(msg),
                                                    scheduledPollTime));
                                    errorState = false;
                                }

                            } catch (JsonMappingException e1) {
                                log.error("While polling", e1);
                                raiseEvent(TWITTER_API_FAILURE_EVENT, scheduledPollTime, true,
                                        new TranslatableMessage("twitter.events.apiFailure",
                                                e1.getMessage()));
                                errorState = true;
                            } catch (InterruptedException | JsonProcessingException e) {
                                log.error("While polling", e);
                                raiseEvent(TWITTER_API_FAILURE_EVENT, scheduledPollTime, true,
                                        new TranslatableMessage("twitter.events.apiFailure",
                                                e.getMessage()));
                                errorState = true;
                            }
                        }
                        break;
                }
            }
        }
        if (errorState) {
            raiseEvent(TWITTER_API_FAILURE_EVENT, scheduledPollTime, true,
                    new TranslatableMessage("twitter.events.apiFailure", "Something happen!!"));
        } else {
            returnToNormal(TWITTER_API_FAILURE_EVENT, scheduledPollTime);
        }
    }

    /*
     * When a point is set externally (via the UI) this method is called
     *  to allow the data source to perform the set and handle the result/error
     */
    @Override public void setPointValueImpl(DataPointRT dprt, PointValueTime valueToSet,
            SetPointSource source) {
        dprt.setPointValue(valueToSet, source);
    }
}
