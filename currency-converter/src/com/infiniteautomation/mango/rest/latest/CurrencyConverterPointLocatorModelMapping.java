/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.currencyConverter.vo.CurrencyConverterPointLocatorVO;
import com.infiniteautomation.mango.rest.latest.model.RestModelJacksonMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.infiniteautomation.mango.rest.latest.model.CurrencyConverterPointLocatorModel;
import com.serotonin.m2m2.vo.permission.PermissionHolder;


/**
 * This class is used to map the VO to and from a REST Model
 * @author Pier Puccini
 *
 */
@Component
public class CurrencyConverterPointLocatorModelMapping implements RestModelJacksonMapping<CurrencyConverterPointLocatorVO, CurrencyConverterPointLocatorModel> {

    @Override
    public Class<? extends CurrencyConverterPointLocatorVO> fromClass() {
        return CurrencyConverterPointLocatorVO.class;
    }

    @Override
    public Class<? extends CurrencyConverterPointLocatorModel> toClass() {
        return CurrencyConverterPointLocatorModel.class;
    }

    @Override
    public CurrencyConverterPointLocatorModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        return new CurrencyConverterPointLocatorModel((CurrencyConverterPointLocatorVO)from);
    }

    @Override
    public String getTypeName() {
        return CurrencyConverterPointLocatorModel.TYPE_NAME;
    }

}
