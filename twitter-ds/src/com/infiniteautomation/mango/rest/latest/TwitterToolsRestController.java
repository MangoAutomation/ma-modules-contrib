/*
 * Copyright (C) 2021 RadixIot LLC. All rights reserved.
 */

package com.infiniteautomation.mango.rest.latest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infiniteautomation.mango.spring.service.DataSourceService;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="Twitter Tools")
@RestController
@RequestMapping("/twitter")
public class TwitterToolsRestController {

    private final DataSourceService dataSourceService;

    @Autowired
    public TwitterToolsRestController(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @ApiOperation(
            value = "Get logfile name",
            notes = "Must have permission to edit the data source"
    )
    @RequestMapping(method = RequestMethod.GET, value="/tweets/{xid}")
    public List<String> getTweets(
            @ApiParam(value = "XID of Data Source", required = true, allowMultiple = false)
            @PathVariable String xid,
            @ApiParam(value = "Tweet filters", required = true, allowMultiple = false)
            @RequestParam(required = true)
            String[] tweetFilter,
            @ApiParam(value = "Tweet count", required = false, allowMultiple = false)
            @RequestParam(required = false, defaultValue = "1")
            int tweetCount) {

        DataSourceVO vo = dataSourceService.get(xid);

        //create a Twitter client

        // get the tweets
        //GET /rest/latest/twitter/tweets/DS_XID/#tag1,#tag2
        //http://localhost:8080/rest/latest/twitter/tweets/DS_7c018735-f935-4860-9e0a-1040915ad45d?twitterFilter=#olympics

        // return them
        return Arrays.asList("tweet one","tweet two");
    }
}
