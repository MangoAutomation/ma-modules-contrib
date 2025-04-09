/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest.model;

import com.infiniteautomation.mango.example.ExamplePollingDataSourceDefinition;
import com.infiniteautomation.mango.example.vo.ExamplePollingDataSourceVO;
import com.infiniteautomation.mango.rest.latest.model.datasource.AbstractPollingDataSourceModel;

/**
 * @author Terry Packer
 *
 */
public class ExamplePollingDataSourceModel extends AbstractPollingDataSourceModel<ExamplePollingDataSourceVO>{

    public ExamplePollingDataSourceModel() {
        super();
    }
    
    public ExamplePollingDataSourceModel(ExamplePollingDataSourceVO data) {
        fromVO(data);
    }

    @Override
    public String getModelType() {
        return ExamplePollingDataSourceDefinition.TYPE_NAME;
    }
    
    @Override
    public ExamplePollingDataSourceVO toVO() {
        ExamplePollingDataSourceVO vo = super.toVO();
        return vo;
    }
    
    @Override
    public void fromVO(ExamplePollingDataSourceVO vo) {
        super.fromVO(vo);
    }
}
