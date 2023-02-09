/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.dailystockprice.rt;

import com.infiniteautomation.mango.dailystockprice.rt.DailyStockPriceDataSourceRT.PolledValues;
import com.infiniteautomation.mango.dailystockprice.vo.DailyStockPricePointLocatorVO;
import com.serotonin.m2m2.rt.dataImage.types.DataValue;
import com.serotonin.m2m2.rt.dataSource.PointLocatorRT;

/**
 * Runtime container for an example point
 */
public class DailyStockPricePointLocatorRT extends PointLocatorRT<DailyStockPricePointLocatorVO> {

    public DailyStockPricePointLocatorRT(DailyStockPricePointLocatorVO vo) {
        super(vo);
    }


    public DataValue getValue(PolledValues polledValues) throws Exception {
        return polledValues.getValue(vo);
    }
}
