/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.twitter.rt;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infiniteautomation.mango.twitter.vo.TwitterDataSourceVO;
import com.serotonin.m2m2.DataTypes;
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

    protected final Log LOG = LogFactory.getLog(TwitterDataSourceRT.class);

    //Events that can be generated
    public static final int POLL_ABORTED_EVENT = 1;

    public static final int TWITTER_API_FAILURE_EVENT = 2;

    private boolean errorState = true;


    public TwitterDataSourceRT(TwitterDataSourceVO vo) {
        super(vo);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void addDataPointImpl(DataPointRT dataPoint) {
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

            for (DataPointRT dprt : dataPoints) {
                TwitterPointLocatorRT plrt = dprt.getPointLocator();


                switch (plrt.getVo().getDataTypeId()) {
                    case DataTypes.NUMERIC:
                    case DataTypes.BINARY:
                    case DataTypes.MULTISTATE:
                    case DataTypes.IMAGE:
                    default:
                        break;
                    case DataTypes.ALPHANUMERIC:
                        if (!plrt.getClient().isDone() ) {
                            String msg = null;
                            try {
                                msg = plrt.getMsgQueue().poll(pollingPeriodMillis - 10,
                                        TimeUnit.MILLISECONDS);
                                if(msg!=null) {
                                    ObjectMapper mapper = new ObjectMapper();
                                    JsonNode actualObj = mapper.readTree(msg);
                                    msg = actualObj.get("text").asText();
                                    dprt.updatePointValue(new PointValueTime(new AlphanumericValue(msg),
                                            scheduledPollTime));
                                    errorState = false;
                                }else {
                                    //is this really a failure?
                                    raiseEvent(TWITTER_API_FAILURE_EVENT, scheduledPollTime, true,
                                            new TranslatableMessage("twitter.events.apiFailure",
                                                    "Null message"));
                                    errorState = true;
                                }

                            } catch (InterruptedException e) {
                                LOG.error("While polling", e);
                                raiseEvent(TWITTER_API_FAILURE_EVENT, scheduledPollTime, true,
                                        new TranslatableMessage("twitter.events.apiFailure",
                                                e.getMessage()));
                                errorState = true;
                            } catch (JsonMappingException e1) {
                                LOG.error("While polling", e1);
                                raiseEvent(TWITTER_API_FAILURE_EVENT, scheduledPollTime, true,
                                        new TranslatableMessage("twitter.events.apiFailure",
                                                e1.getMessage()));
                                errorState = true;
                            } catch (JsonProcessingException e2) {
                                LOG.error("While polling", e2);
                                raiseEvent(TWITTER_API_FAILURE_EVENT, scheduledPollTime, true,
                                        new TranslatableMessage("twitter.events.apiFailure",
                                                e2.getMessage()));
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
