/**
 * Copyright (C) 2019  Terry Packer. All rights reserved.
 */
package com.infiniteautomation.mango.rest.v2;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.example.ExamplePollingDataSourceDefinition;
import com.infiniteautomation.mango.example.vo.ExamplePollingDataSourceVO;
import com.infiniteautomation.mango.rest.v2.model.ExamplePollingDataSourceModel;
import com.infiniteautomation.mango.rest.v2.model.RestModelJacksonMapping;
import com.infiniteautomation.mango.rest.v2.model.RestModelMapper;
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
