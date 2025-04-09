/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Benjamin Perez
 */
@Tag(name = "Twitter Tools")
@RestController
@RequestMapping("/twitter")
public class TwitterToolsRestController {
    private final Logger log = LoggerFactory.getLogger(TwitterToolsRestController.class);
    private final TwitterToolsService service;


    @Autowired
    public TwitterToolsRestController(TwitterToolsService service) {
        this.service = service;
    }

    @Operation(summary = "Get tweets", description = "Must have permission to edit the data source")
    @RequestMapping(method = RequestMethod.GET, value = "/tweets/{xid}")
    public List<String> getTweets(
            @Parameter(description = "XID of Data Source", required = true)
            @PathVariable String xid,
            @Parameter(name = "tweetCount", description = "Tweet count", required = false)
            @RequestParam(required = false, defaultValue = "1") int tweetCount,
            @Parameter(description = "Tweet filters", required = true)
            @RequestParam(name = "tweetFilter", required = false) List<String> tweetFilter) {
        return service.getTweets(xid, tweetCount, tweetFilter);

    }
}
