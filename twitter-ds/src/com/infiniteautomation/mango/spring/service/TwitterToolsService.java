/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.spring.service;

import static com.infiniteautomation.mango.spring.MangoRuntimeContextConfiguration.COMMON_OBJECT_MAPPER_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.infiniteautomation.mango.twitter.TwitterClientTools;
import com.infiniteautomation.mango.twitter.TwitterDataSourceDefinition;
import com.infiniteautomation.mango.twitter.vo.TwitterDataSourceVO;
import com.infiniteautomation.mango.util.exception.NotFoundException;
import com.infiniteautomation.mango.util.exception.TranslatableIllegalStateException;
import com.infiniteautomation.mango.util.exception.ValidationException;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.i18n.ProcessResult;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;
import com.serotonin.m2m2.vo.permission.PermissionException;
import com.twitter.hbc.core.Client;

/**
 * @author Benjamin Perez
 */
@Service public class TwitterToolsService {
    private final Logger log = LoggerFactory.getLogger(TwitterToolsService.class);
    private final DataSourceService dataSourceService;
    private final PermissionService permissionService;
    private final ObjectMapper mapper;

    @Autowired public TwitterToolsService(DataSourceService dataSourceService,
            PermissionService permissionService,
            @Qualifier(COMMON_OBJECT_MAPPER_NAME) ObjectMapper mapper) {
        this.permissionService = permissionService;
        this.dataSourceService = dataSourceService;
        this.mapper = mapper;
    }

    /**
     * Creates and connects a Twitter client, then wait for the amount of tweets based on the
     * given filters, getting real worldwide tweets. Retrieving a list with the Tweets
     *
     * @param xid         of the Data Source
     * @param tweetCount  Number of tweets to retrieve
     * @param tweetFilter List of words or hashtags to filter the tweets by.
     * @return list with the Tweets
     * @throws PermissionException
     * @throws NotFoundException
     */
    public List<String> getTweets(String xid, int tweetCount, List<String> tweetFilter)
            throws PermissionException, NotFoundException, ValidationException {
        //check read
        DataSourceVO vo = dataSourceService.get(xid);

        //check edit
        permissionService.ensurePermission(Common.getUser(), vo.getEditPermission());

        //validate inputs
        ProcessResult result = new ProcessResult();
        if (tweetCount <= 0) {
            result.addContextualMessage("tweetCount", "validate.greaterThanZero");
        }
        TwitterDataSourceDefinition.validateTweetFilter(result, tweetFilter);
        if (!result.isValid()) {
            throw new ValidationException(result);
        }

        List<String> returnList = new ArrayList<String>();
        Client client;

        if (vo instanceof TwitterDataSourceVO) {
            TwitterDataSourceVO dataSource = (TwitterDataSourceVO) vo;
            BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(1000);
            List<String> terms = Lists.newArrayList(tweetFilter);

            client = TwitterClientTools.getTwitterClient(dataSource.getConsumerKey(),
                    dataSource.getConsumerSecret(), dataSource.getToken(), dataSource.getSecret(),
                    terms, msgQueue);

            client.connect();
            while (returnList.size() < tweetCount) {
                try {
                    String msg = msgQueue.poll(15, TimeUnit.SECONDS);
                    if (msg != null) {
                        returnList.add(TwitterClientTools.getTweet(msg, mapper));
                    }
                } catch (Exception e) {
                    log.error("While consuming messages: " + e.getCause());
                    break;
                }
            }
        } else {
            throw new TranslatableIllegalStateException(
                    new TranslatableMessage("twitter.tools.invalidSourceType"));
        }
        if (!client.isDone()) {
            try {
                client.stop();
                log.info("client stopped");
            } catch (Exception e) {
                log.warn("While calling client.stop() " + e.getCause());

            }

        }

        return returnList;
    }
}
