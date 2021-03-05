/**
 * Copyright (C) 2019  Terry Packer. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.currencyConverter.CurrencyConverterDataSourceDefinition;
import com.infiniteautomation.mango.currencyConverter.vo.CurrencyConverterDataSourceVO;
import com.infiniteautomation.mango.rest.latest.model.CurrencyConverterPollingDataSourceModel;
import com.infiniteautomation.mango.rest.latest.model.RestModelJacksonMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

/**
 * @author Pier Puccini
 *
 */
@Component
public class CurrencyConverterPollingDataSourceModelMapping implements RestModelJacksonMapping<CurrencyConverterDataSourceVO, CurrencyConverterPollingDataSourceModel> {

    @Override
    public Class<? extends CurrencyConverterDataSourceVO> fromClass() {
        return CurrencyConverterDataSourceVO.class;
    }

    @Override
    public Class<? extends CurrencyConverterPollingDataSourceModel> toClass() {
        return CurrencyConverterPollingDataSourceModel.class;
    }

    @Override
    public CurrencyConverterPollingDataSourceModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        return new CurrencyConverterPollingDataSourceModel((CurrencyConverterDataSourceVO)from);
    }

    @Override
    public String getTypeName() {
        return CurrencyConverterDataSourceDefinition.TYPE_NAME;
    }

}
