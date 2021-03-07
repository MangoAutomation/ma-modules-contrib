/**
 * Copyright (C) 2019  Terry Packer. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.currencyConverter.CurrencyConverterDataSourceDefinition;
import com.infiniteautomation.mango.currencyConverter.vo.CurrencyConverterDataSourceVO;
import com.infiniteautomation.mango.rest.latest.model.CurrencyConverterDataSourceModel;
import com.infiniteautomation.mango.rest.latest.model.RestModelJacksonMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

/**
 * @author Pier Puccini
 *
 */
@Component
public class CurrencyConverterDataSourceModelMapping implements RestModelJacksonMapping<CurrencyConverterDataSourceVO, CurrencyConverterDataSourceModel> {

    @Override
    public Class<? extends CurrencyConverterDataSourceVO> fromClass() {
        return CurrencyConverterDataSourceVO.class;
    }

    @Override
    public Class<? extends CurrencyConverterDataSourceModel> toClass() {
        return CurrencyConverterDataSourceModel.class;
    }

    @Override
    public CurrencyConverterDataSourceModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        return new CurrencyConverterDataSourceModel((CurrencyConverterDataSourceVO)from);
    }

    @Override
    public String getTypeName() {
        return CurrencyConverterDataSourceDefinition.TYPE_NAME;
    }

}
