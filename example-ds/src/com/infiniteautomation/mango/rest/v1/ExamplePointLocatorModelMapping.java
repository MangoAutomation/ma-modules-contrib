/**
 * Copyright (C) 2019  Terry Packer. All rights reserved.
 */
package com.infiniteautomation.mango.rest.v1;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.example.vo.ExamplePointLocatorVO;
import com.infiniteautomation.mango.rest.v1.model.ExamplePointLocatorModel;
import com.serotonin.m2m2.vo.User;
import com.serotonin.m2m2.web.mvc.rest.v1.model.RestModelJacksonMapping;
import com.serotonin.m2m2.web.mvc.rest.v1.model.RestModelMapper;

/**
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
