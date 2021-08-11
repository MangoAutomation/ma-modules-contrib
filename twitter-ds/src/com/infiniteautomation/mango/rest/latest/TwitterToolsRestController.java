/*
 * Copyright (C) 2021 RadixIot LLC. All rights reserved.
 */

package com.infiniteautomation.mango.rest.latest;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.infiniteautomation.mango.rest.latest.exception.BadRequestException;
import com.infiniteautomation.mango.spring.service.DataSourceService;
import com.infiniteautomation.mango.twitter.TwitterClientTools;
import com.infiniteautomation.mango.twitter.rt.TwitterDataSourceRT;
import com.infiniteautomation.mango.twitter.vo.TwitterDataSourceVO;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;
import com.twitter.hbc.core.Client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Twitter Tools") @RestController @RequestMapping("/twitter")
public class TwitterToolsRestController {
    private final Logger log = LoggerFactory.getLogger(TwitterDataSourceRT.class);
    private final DataSourceService dataSourceService;
    private final ObjectMapper mapper;

    @Autowired
    public TwitterToolsRestController(DataSourceService dataSourceService,
            @Qualifier(COMMON_OBJECT_MAPPER_NAME) ObjectMapper mapper) {
        this.dataSourceService = dataSourceService;
        this.mapper = mapper;
    }

    @ApiOperation(value = "Get logfile name", notes = "Must have permission to edit the data source")
    @RequestMapping(method = RequestMethod.GET, value = "/tweets/{xid}")
    public List<String> getTweets(
            @ApiParam(value = "XID of Data Source", required = true, allowMultiple = false)
            @PathVariable String xid,
            @ApiParam(name = "tweetCount", value = "Tweet count", required = false, allowMultiple = false)
            @RequestParam(required = false, defaultValue = "1") int tweetCount,
            @ApiParam(value = "Tweet filters", required = true, allowMultiple = false)
            @RequestParam(name = "tweetFilter", required = false) String[] tweetFilter) {

        DataSourceVO vo = dataSourceService.get(xid);
        List<String> returnList = new ArrayList<String>();
        Client client;
        ;
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
            throw new BadRequestException(
                    new TranslatableMessage("twitter.tools.invalidSourceType"));
        }
        if (client != null && !client.isDone()) {
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
