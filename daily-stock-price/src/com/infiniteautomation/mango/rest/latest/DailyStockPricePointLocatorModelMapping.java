/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.dailystockprice.vo.DailyStockPricePointLocatorVO;
import com.infiniteautomation.mango.rest.latest.model.DailyStockPricePointLocatorModel;
import com.infiniteautomation.mango.rest.latest.model.RestModelJacksonMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

@Component
public class DailyStockPricePointLocatorModelMapping implements RestModelJacksonMapping<DailyStockPricePointLocatorVO, DailyStockPricePointLocatorModel> {

    @Override
    public Class<? extends DailyStockPricePointLocatorVO> fromClass() {
        return DailyStockPricePointLocatorVO.class;
    }

    @Override
    public Class<? extends DailyStockPricePointLocatorModel> toClass() {
        return DailyStockPricePointLocatorModel.class;
    }

    @Override
    public DailyStockPricePointLocatorModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        return new DailyStockPricePointLocatorModel((DailyStockPricePointLocatorVO)from);
    }

    @Override
    public String getTypeName() {
        return DailyStockPricePointLocatorModel.TYPE_NAME;
    }

}
