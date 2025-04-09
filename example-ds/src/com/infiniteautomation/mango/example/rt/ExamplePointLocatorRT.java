/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.example.rt;

import com.infiniteautomation.mango.example.vo.ExamplePointLocatorVO;
import com.serotonin.m2m2.rt.dataSource.PointLocatorRT;

/**
 * 
 * Runtime container for an example point
 * 
 * @author Terry Packer
 *
 */
public class ExamplePointLocatorRT extends PointLocatorRT<ExamplePointLocatorVO> {

    public ExamplePointLocatorRT(ExamplePointLocatorVO vo) {
        super(vo);
    }

}
