/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.twitter.rt;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.collect.Lists;
import com.infiniteautomation.mango.twitter.TwitterClientTools;
import com.infiniteautomation.mango.twitter.vo.TwitterPointLocatorVO;
import com.serotonin.m2m2.rt.dataSource.PointLocatorRT;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

/**
 * 
 * Runtime container for an example point
 * 
 * @author Benjamin Perez
 *
 */
public class TwitterPointLocatorRT extends PointLocatorRT<TwitterPointLocatorVO> {

    //messages Queue
    final private BlockingQueue<String> msgQueue;
    private Client client;
    private TwitterDataSourceRT dataSource;

    public TwitterPointLocatorRT(TwitterPointLocatorVO vo) {
        super(vo);
        msgQueue = new LinkedBlockingQueue<String>(1000);

    }
    public void initialize(TwitterDataSourceRT dataSource){
        this.dataSource = dataSource;
        client = TwitterClientTools.getTwitterClient(dataSource.getVo().getConsumerKey(), dataSource.getVo().getConsumerSecret(),
                dataSource.getVo().getToken(), dataSource.getVo().getSecret(),vo.getTweetFilter(), msgQueue);
        client.connect();
    }
    public Client getClient() {
        return client;
    }
    public BlockingQueue<String> getMsgQueue() {
        return msgQueue;
    }

    /*
     * Provides a twitter client.
     * Declares the host to connect to, the endpoint, and authentication (basic auth or oauth)
     */
    @Deprecated
    private Client getTwitterClient() {
        // Host to connect to
        Hosts streamHost = new HttpHosts(Constants.STREAM_HOST);

        //the endpoint
        StatusesFilterEndpoint statusesFilterEndpoint = new StatusesFilterEndpoint();

        //This list should be an input in the data point
        List<String> terms = Lists.newArrayList(vo.getTweetFilter());
        statusesFilterEndpoint.trackTerms(terms);

        //Authentication (basic auth or oauth)
        Authentication oAuth =
                new OAuth1(dataSource.getVo().getConsumerKey(), dataSource.getVo().getConsumerSecret(),
                        dataSource.getVo().getToken(), dataSource.getVo().getSecret());

        ClientBuilder builder =
                new ClientBuilder().name("Twitter stream listener").hosts(streamHost)
                        .authentication(oAuth).endpoint(statusesFilterEndpoint)
                        .processor(new StringDelimitedProcessor(msgQueue));
        Client client = builder.build();
        return client;
    }

}
