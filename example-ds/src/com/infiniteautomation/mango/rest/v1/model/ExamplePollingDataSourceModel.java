/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.rest.v1.model;

import com.infiniteautomation.mango.example.ExamplePollingDataSourceDefinition;
import com.infiniteautomation.mango.example.vo.ExamplePollingDataSourceVO;
import com.serotonin.m2m2.web.mvc.rest.v1.model.dataSource.AbstractPollingDataSourceModel;

/**
 * @author Terry Packer
 *
 */
public class ExamplePollingDataSourceModel extends AbstractPollingDataSourceModel<ExamplePollingDataSourceVO> {

    public ExamplePollingDataSourceModel() { }
    
    public ExamplePollingDataSourceModel(ExamplePollingDataSourceVO vo) {
        super(vo);
    }
    
    @Override
    public String getModelType() {
        return ExamplePollingDataSourceDefinition.TYPE_NAME;
    }

}
