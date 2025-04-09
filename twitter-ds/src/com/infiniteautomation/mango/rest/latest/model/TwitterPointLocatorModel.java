/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest.model;

import java.util.List;

import com.infiniteautomation.mango.rest.latest.model.dataPoint.AbstractPointLocatorModel;
import com.infiniteautomation.mango.twitter.vo.TwitterPointLocatorVO;

/**
 * @author Benjamin Perez
 *
 */
public class TwitterPointLocatorModel extends AbstractPointLocatorModel<TwitterPointLocatorVO> {

    public static final String TYPE_NAME = "PL.TWITTER";
    private List<String> tweetFilter;

    public TwitterPointLocatorModel() {

    }

    public TwitterPointLocatorModel(TwitterPointLocatorVO data) {
        super(data);
    }

    public List<String> getTweetFilter() {
        return tweetFilter;
    }

    public void setTweetFilter(List<String> tweetFilter) {
        this.tweetFilter = tweetFilter;
    }
    @Override
    public String getModelType() {
        return TYPE_NAME;
    }

    /**
     * This is where one would copy the VO values into the fields,
     * this particual vo has no extra fields that are not handled in
     * the superclass.
     */
    @Override
    public TwitterPointLocatorVO toVO() {
        TwitterPointLocatorVO vo = new TwitterPointLocatorVO();
        vo.setSettable(settable);
        vo.setTweetFilter(tweetFilter);
        return vo;
    }

    /**
     * This is where one would copy the Model values into the VO,
     * this particual vo has no extra fields that are not handled in
     * the superclass.
     */
    @Override
    public void fromVO(TwitterPointLocatorVO vo) {
        super.fromVO(vo);
        this.tweetFilter = vo.getTweetFilter();

    }

}
