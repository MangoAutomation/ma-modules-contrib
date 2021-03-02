/**
 * Copyright (C) 2019  Terry Packer. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.example.vo.ExamplePointLocatorVO;
import com.infiniteautomation.mango.rest.latest.model.RestModelJacksonMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.infiniteautomation.mango.rest.latest.model.ExamplePointLocatorModel;
import com.serotonin.m2m2.vo.permission.PermissionHolder;


/**
 * This class is used to map the VO to and from a REST Model
 * @author Terry Packer
 *
 */
@Component
public class ExamplePointLocatorModelMapping implements RestModelJacksonMapping<ExamplePointLocatorVO, ExamplePointLocatorModel> {

    @Override
    public Class<? extends ExamplePointLocatorVO> fromClass() {
        return ExamplePointLocatorVO.class;
    }

    @Override
    public Class<? extends ExamplePointLocatorModel> toClass() {
        return ExamplePointLocatorModel.class;
    }

    @Override
    public ExamplePointLocatorModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        return new ExamplePointLocatorModel((ExamplePointLocatorVO)from);
    }

    @Override
    public String getTypeName() {
        return ExamplePointLocatorModel.TYPE_NAME;
    }

}
