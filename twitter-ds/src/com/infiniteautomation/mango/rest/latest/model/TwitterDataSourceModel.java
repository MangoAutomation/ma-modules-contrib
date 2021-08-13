/*
 * Copyright (C) 2021 RadixIot LLC. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest.model;


import com.infiniteautomation.mango.rest.latest.model.datasource.AbstractPollingDataSourceModel;
import com.infiniteautomation.mango.twitter.TwitterDataSourceDefinition;
import com.infiniteautomation.mango.twitter.vo.TwitterDataSourceVO;

/**
 * @author Benjamin Perez
 *
 */
public class TwitterDataSourceModel extends AbstractPollingDataSourceModel<TwitterDataSourceVO>{

    private String consumerKey;
    private String consumerSecret;
    private String token;
    private String secret;

    public TwitterDataSourceModel() {
        super();
    }
    
    public TwitterDataSourceModel(TwitterDataSourceVO data) {
        fromVO(data);
    }

    @Override
    public String getModelType() {
        return TwitterDataSourceDefinition.TYPE_NAME;
    }

    public String getConsumerKey() { return consumerKey; }

    public void setConsumerKey(String consumerKey) { this.consumerKey = consumerKey; }

    public String getConsumerSecret() { return consumerSecret; }

    public void setConsumerSecret(String consumerSecret) { this.consumerSecret = consumerSecret; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public String getSecret() { return secret; }

    public void setSecret(String secret) { this.secret = secret; }
    
    @Override
    public TwitterDataSourceVO toVO() {
        TwitterDataSourceVO vo = super.toVO();
        vo.setConsumerKey(consumerKey);
        vo.setConsumerSecret(consumerSecret);
        vo.setToken(token);
        vo.setSecret(secret);
        return vo;
    }
    
    @Override
    public void fromVO(TwitterDataSourceVO vo) {
        super.fromVO(vo);
        this.consumerKey = vo.getConsumerKey();
        this.consumerSecret = vo.getConsumerSecret();
        this.token = vo.getToken();
        this.secret = vo.getSecret();

    }


}
