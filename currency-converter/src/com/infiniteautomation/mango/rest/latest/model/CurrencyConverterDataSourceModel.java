/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest.model;

import com.infiniteautomation.mango.currencyConverter.CurrencyConverterDataSourceDefinition;
import com.infiniteautomation.mango.currencyConverter.vo.CurrencyConverterDataSourceVO;
import com.infiniteautomation.mango.rest.latest.model.datasource.AbstractPollingDataSourceModel;

/**
 * @author Pier Puccini
 *
 */
public class CurrencyConverterDataSourceModel extends AbstractPollingDataSourceModel<CurrencyConverterDataSourceVO>{

    private String apiKey;

    public CurrencyConverterDataSourceModel() {
        super();
    }
    
    public CurrencyConverterDataSourceModel(CurrencyConverterDataSourceVO data) {
        fromVO(data);
    }

    @Override
    public String getModelType() {
        return CurrencyConverterDataSourceDefinition.TYPE_NAME;
    }
    
    @Override
    public CurrencyConverterDataSourceVO toVO() {
        CurrencyConverterDataSourceVO vo = super.toVO();
        vo.setApiKey(apiKey);
        return vo;
    }
    
    @Override
    public void fromVO(CurrencyConverterDataSourceVO vo) {
        super.fromVO(vo);
        this.apiKey = vo.getApiKey();
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
