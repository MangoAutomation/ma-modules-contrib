/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.rest.v2.model;

import com.infiniteautomation.mango.example.vo.ExamplePointLocatorVO;
import com.infiniteautomation.mango.rest.v2.model.dataPoint.AbstractPointLocatorModel;

/**
 * @author Terry Packer
 *
 */
public class ExamplePointLocatorModel extends AbstractPointLocatorModel<ExamplePointLocatorVO> {

    public static final String TYPE_NAME = "PL.EXAMPLE_POLLING";

    public ExamplePointLocatorModel() {

    }

    public ExamplePointLocatorModel(ExamplePointLocatorVO data) {
        super(data);
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
    public ExamplePointLocatorVO toVO() {
        ExamplePointLocatorVO vo = new ExamplePointLocatorVO();
        return vo;
    }

    /**
     * This is where one would copy the Model values into the VO,
     * this particual vo has no extra fields that are not handled in
     * the superclass.
     */
    @Override
    public void fromVO(ExamplePointLocatorVO vo) {
        super.fromVO(vo);
    }

}
