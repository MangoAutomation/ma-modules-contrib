/*
 * Copyright (C) 2021 RadixIot LLC. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.rest.latest.model.RestModelJacksonMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.infiniteautomation.mango.rest.latest.model.TwitterDataSourceModel;
import com.infiniteautomation.mango.twitter.TwitterDataSourceDefinition;
import com.infiniteautomation.mango.twitter.vo.TwitterDataSourceVO;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

/**
 * @author Pier Puccini
 *
 */
@Component
public class TwitterDataSourceModelMapping implements RestModelJacksonMapping<TwitterDataSourceVO, TwitterDataSourceModel> {

    @Override
    public Class<? extends TwitterDataSourceVO> fromClass() {
        return TwitterDataSourceVO.class;
    }

    @Override
    public Class<? extends TwitterDataSourceModel> toClass() {
        return TwitterDataSourceModel.class;
    }

    @Override
    public TwitterDataSourceModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        return new TwitterDataSourceModel((TwitterDataSourceVO)from);
    }

    @Override
    public String getTypeName() {
        return TwitterDataSourceDefinition.TYPE_NAME;
    }

}
