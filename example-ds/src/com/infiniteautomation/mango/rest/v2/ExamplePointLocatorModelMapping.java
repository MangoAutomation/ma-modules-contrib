/**
 * Copyright (C) 2019  Terry Packer. All rights reserved.
 */
package com.infiniteautomation.mango.rest.v2;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.example.vo.ExamplePointLocatorVO;
import com.infiniteautomation.mango.rest.v1.model.ExamplePointLocatorModel;
import com.infiniteautomation.mango.rest.v2.model.RestModelJacksonMapping;
import com.infiniteautomation.mango.rest.v2.model.RestModelMapper;
import com.serotonin.m2m2.vo.User;


/**
 * This class is used for the v2 Model Mapper but replicates the 
 * functionality of the v1 mapper exactly
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
    public ExamplePointLocatorModel map(Object from, User user, RestModelMapper mapper) {
        return new ExamplePointLocatorModel((ExamplePointLocatorVO)from);
    }

    @Override
    public String getTypeName() {
        return ExamplePointLocatorModel.TYPE_NAME;
    }

}
