/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.example.rt;

import com.infiniteautomation.mango.example.vo.ExamplePointLocatorVO;
import com.serotonin.m2m2.rt.dataImage.PointValueTime;
import com.serotonin.m2m2.rt.dataImage.types.DataValue;
import com.serotonin.m2m2.rt.dataImage.types.NumericValue;
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

    /**
     * Transform the value according to our settings
     * @param currentValue
     * @return
     */
    public DataValue transform(PointValueTime currentValue) {
        if(currentValue == null) {
            return new NumericValue(0);
        }else if(vo.isIncrement()) {
            return new NumericValue(currentValue.getDoubleValue() + 1);
        }else {
            return new NumericValue(currentValue.getDoubleValue() - 1);
        }
    }

}
