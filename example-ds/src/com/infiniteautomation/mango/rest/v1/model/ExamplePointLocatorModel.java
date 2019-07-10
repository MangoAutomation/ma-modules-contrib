/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.rest.v1.model;

import com.infiniteautomation.mango.example.vo.ExamplePointLocatorVO;
import com.serotonin.m2m2.web.mvc.rest.v1.model.dataPoint.PointLocatorModel;

/**
 * @author Terry Packer
 *
 */
public class ExamplePointLocatorModel extends PointLocatorModel<ExamplePointLocatorVO> {

    public static final String TYPE_NAME = "PL.EXAMPLE_POLLING";
    
    public ExamplePointLocatorModel() {
        super(new ExamplePointLocatorVO());
    }
    
    public ExamplePointLocatorModel(ExamplePointLocatorVO data) {
        super(data);
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

}
