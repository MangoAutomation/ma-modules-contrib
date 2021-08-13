/*
 * Copyright (C) 2021 RadixIot LLC. All rights reserved.
 */

package com.infiniteautomation.mango.rest.latest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infiniteautomation.mango.spring.service.TwitterToolsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Benjamin Perez
 */
@Api(value = "Twitter Tools") @RestController @RequestMapping("/twitter")
public class TwitterToolsRestController {
    private final Logger log = LoggerFactory.getLogger(TwitterToolsRestController.class);
    private final TwitterToolsService service;


    @Autowired
    public TwitterToolsRestController(TwitterToolsService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get tweets", notes = "Must have permission to edit the data source")
    @RequestMapping(method = RequestMethod.GET, value = "/tweets/{xid}")
    public List<String> getTweets(
            @ApiParam(value = "XID of Data Source", required = true, allowMultiple = false)
            @PathVariable String xid,
            @ApiParam(name = "tweetCount", value = "Tweet count", required = false, allowMultiple = false)
            @RequestParam(required = false, defaultValue = "1") int tweetCount,
            @ApiParam(value = "Tweet filters", required = true, allowMultiple = false)
            @RequestParam(name = "tweetFilter", required = false) List<String> tweetFilter) {
        return service.getTweets(xid, tweetCount, tweetFilter);

    }
}
