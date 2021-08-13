/*
 * Copyright (C) 2021 RadixIot LLC. All rights reserved.
 */
package com.infiniteautomation.mango.twitter;


import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.infiniteautomation.mango.twitter.vo.TwitterDataSourceVO;
import com.infiniteautomation.mango.twitter.vo.TwitterPointLocatorVO;
import com.serotonin.m2m2.i18n.ProcessResult;
import com.serotonin.m2m2.module.PollingDataSourceDefinition;
import com.serotonin.m2m2.vo.DataPointVO;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

/**
 * @author Benjamin Perez
 *
 */
public class TwitterDataSourceDefinition extends PollingDataSourceDefinition<TwitterDataSourceVO> {

    public static final String TYPE_NAME = "TWITTER_DS";

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
        return "twitter.datasource.description";
    }

    @Override
    public TwitterDataSourceVO createDataSourceVO() {
        return new TwitterDataSourceVO();
    }

    /**
     * Validate the data source's settings, called when saving or updating this type of data source
     * @param response - where to store the validation errors
     * @param ds - the data source to validate
     * @param user - the saving permission holder
     */
    @Override
    public void validate(ProcessResult response, TwitterDataSourceVO ds, PermissionHolder user) {

        if (StringUtils.isEmpty(ds.getConsumerKey())) {
            response.addContextualMessage("consumerKey", "validate.required");
        }
        if(StringUtils.isEmpty(ds.getConsumerSecret())) {
            response.addContextualMessage("consumerSecret", "validate.required");
        }
        if(StringUtils.isEmpty(ds.getToken())) {
            response.addContextualMessage("token", "validate.required");
        }
        if(StringUtils.isEmpty(ds.getSecret())) {
            response.addContextualMessage("secret", "validate.required");
        }
    }

    /**
     * Validate the point locator's settings, this is called when saving or updating a data point
     */
    @Override
    public void validate(ProcessResult response, DataPointVO dpvo, DataSourceVO dsvo, PermissionHolder user) {
        //Ensure the data point should belong to this type of data source
        if (!(dsvo instanceof TwitterDataSourceVO)) {
            response.addContextualMessage("dataSourceId", "dpEdit.validate.invalidDataSourceType");
        }
        TwitterPointLocatorVO pl = dpvo.getPointLocator();
        validateTweetFilter(response, pl.getTweetFilter());

    }

    public static void validateTweetFilter(ProcessResult response, List<String> tweetFilter) {
        if(tweetFilter == null) {
            response.addContextualMessage("tweetFilter", "validate.required");
        }else {
            int index = 0;
            for (String filter: tweetFilter) {
                String prefix = "tweetFilter[" + index + "]";
                if (StringUtils.isEmpty(filter)) {
                    response.addContextualMessage(prefix, "validate.required");
                }
                index++;
            }
        }
    }


}
