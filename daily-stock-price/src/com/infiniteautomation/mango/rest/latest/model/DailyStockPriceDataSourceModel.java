/**
 * Copyright (C) 2019  Terry Packer. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest.model;

import com.infiniteautomation.mango.dailystockprice.DailyStockPriceDataSourceDefinition;
import com.infiniteautomation.mango.dailystockprice.vo.DailyStockPriceDataSourceVO;
import com.infiniteautomation.mango.rest.latest.model.datasource.AbstractPollingDataSourceModel;

public class DailyStockPriceDataSourceModel extends AbstractPollingDataSourceModel<DailyStockPriceDataSourceVO>{

    private String apiKey;

    public DailyStockPriceDataSourceModel() {
        super();
    }
    
    public DailyStockPriceDataSourceModel(DailyStockPriceDataSourceVO data) {
        fromVO(data);
    }

    @Override
    public String getModelType() {
        return DailyStockPriceDataSourceDefinition.TYPE_NAME;
    }
    
    @Override
    public DailyStockPriceDataSourceVO toVO() {
        DailyStockPriceDataSourceVO vo = super.toVO();
        vo.setApiKey(apiKey);
        return vo;
    }
    
    @Override
    public void fromVO(DailyStockPriceDataSourceVO vo) {
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
