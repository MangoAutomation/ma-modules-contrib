/**
 * Copyright (C) 2019  Terry Packer. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest.model;

import com.infiniteautomation.mango.currencyConverter.CurrencyConverterDataSourceDefinition;
import com.infiniteautomation.mango.currencyConverter.vo.CurrencyConverterDataSourceVO;
import com.infiniteautomation.mango.rest.latest.model.datasource.AbstractPollingDataSourceModel;

/**
 * @author Pier Puccini
 *
 */
public class CurrencyConverterPollingDataSourceModel extends AbstractPollingDataSourceModel<CurrencyConverterDataSourceVO>{

    public CurrencyConverterPollingDataSourceModel() {
        super();
    }
    
    public CurrencyConverterPollingDataSourceModel(CurrencyConverterDataSourceVO data) {
        fromVO(data);
    }

    @Override
    public String getModelType() {
        return CurrencyConverterDataSourceDefinition.TYPE_NAME;
    }
    
    @Override
    public CurrencyConverterDataSourceVO toVO() {
        CurrencyConverterDataSourceVO vo = super.toVO();
        return vo;
    }
    
    @Override
    public void fromVO(CurrencyConverterDataSourceVO vo) {
        super.fromVO(vo);
    }
}
