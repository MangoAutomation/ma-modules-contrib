/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.example.ExamplePollingDataSourceDefinition;
import com.infiniteautomation.mango.example.vo.ExamplePollingDataSourceVO;
import com.infiniteautomation.mango.rest.latest.model.ExamplePollingDataSourceModel;
import com.infiniteautomation.mango.rest.latest.model.RestModelJacksonMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

/**
 * @author Terry Packer
 *
 */
@Component
public class ExamplePollingDataSourceModelMapping implements RestModelJacksonMapping<ExamplePollingDataSourceVO, ExamplePollingDataSourceModel> {

    @Override
    public Class<? extends ExamplePollingDataSourceVO> fromClass() {
        return ExamplePollingDataSourceVO.class;
    }

    @Override
    public Class<? extends ExamplePollingDataSourceModel> toClass() {
        return ExamplePollingDataSourceModel.class;
    }

    @Override
    public ExamplePollingDataSourceModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        return new ExamplePollingDataSourceModel((ExamplePollingDataSourceVO)from);
    }

    @Override
    public String getTypeName() {
        return ExamplePollingDataSourceDefinition.TYPE_NAME;
    }

}
