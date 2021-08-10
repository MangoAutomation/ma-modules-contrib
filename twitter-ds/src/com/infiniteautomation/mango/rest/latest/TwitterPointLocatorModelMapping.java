/*
 * Copyright (C) 2021 RadixIot LLC. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.rest.latest.model.RestModelJacksonMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.infiniteautomation.mango.rest.latest.model.TwitterPointLocatorModel;
import com.infiniteautomation.mango.twitter.vo.TwitterPointLocatorVO;
import com.serotonin.m2m2.vo.permission.PermissionHolder;


/**
 * This class is used to map the VO to and from a REST Model
 * @author Benjmain Perez
 *
 */
@Component
public class TwitterPointLocatorModelMapping implements RestModelJacksonMapping<TwitterPointLocatorVO, TwitterPointLocatorModel> {

    @Override
    public Class<? extends TwitterPointLocatorVO> fromClass() {
        return TwitterPointLocatorVO.class;
    }

    @Override
    public Class<? extends TwitterPointLocatorModel> toClass() {
        return TwitterPointLocatorModel.class;
    }

    @Override
    public TwitterPointLocatorModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        return new TwitterPointLocatorModel((TwitterPointLocatorVO)from);
    }

    @Override
    public String getTypeName() {
        return TwitterPointLocatorModel.TYPE_NAME;
    }

}
