/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.twitter.rt;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.infiniteautomation.mango.twitter.TwitterClientTools;
import com.infiniteautomation.mango.twitter.vo.TwitterPointLocatorVO;
import com.serotonin.m2m2.rt.dataSource.PointLocatorRT;
import com.twitter.hbc.core.Client;

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

    public TwitterPointLocatorRT(TwitterPointLocatorVO vo) {
        super(vo);
        msgQueue = new LinkedBlockingQueue<String>(1000);

    }
    public void initialize(TwitterDataSourceRT dataSource){
        client = TwitterClientTools.getTwitterClient(dataSource.getVo().getConsumerKey(), dataSource.getVo().getConsumerSecret(),
                dataSource.getVo().getToken(), dataSource.getVo().getSecret(),vo.getTweetFilter(), msgQueue);
        client.connect();
    }

    public void terminate(){
        if(client != null && !client.isDone()){
            client.stop();
        }
    }
    public Client getClient() {
        return client;
    }
    public BlockingQueue<String> getMsgQueue() {
        return msgQueue;
    }

}
