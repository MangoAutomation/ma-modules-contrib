/**
 * Copyright (C) 2019  Terry Packer. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.dailystockprice.DailyStockPriceDataSourceDefinition;
import com.infiniteautomation.mango.dailystockprice.vo.DailyStockPriceDataSourceVO;
import com.infiniteautomation.mango.rest.latest.model.DailyStockPriceDataSourceModel;
import com.infiniteautomation.mango.rest.latest.model.RestModelJacksonMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

@Component
public class DailyStockPriceDataSourceModelMapping implements RestModelJacksonMapping<DailyStockPriceDataSourceVO, DailyStockPriceDataSourceModel> {

    @Override
    public Class<? extends DailyStockPriceDataSourceVO> fromClass() {
        return DailyStockPriceDataSourceVO.class;
    }

    @Override
    public Class<? extends DailyStockPriceDataSourceModel> toClass() {
        return DailyStockPriceDataSourceModel.class;
    }

    @Override
    public DailyStockPriceDataSourceModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        return new DailyStockPriceDataSourceModel((DailyStockPriceDataSourceVO)from);
    }

    @Override
    public String getTypeName() {
        return DailyStockPriceDataSourceDefinition.TYPE_NAME;
    }

}
