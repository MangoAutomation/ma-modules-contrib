/*
    Copyright (C) 2014 Infinite Automation Systems Inc. All rights reserved.
    @author Matthew Lohbihler
 */
package com.infiniteautomation.m2m2.zigbee;

import com.infiniteautomation.m2m2.zigbee.dwr.ZigBeeEditDwr;
import com.serotonin.ShouldNeverHappenException;
import com.serotonin.m2m2.module.DataSourceDefinition;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;
import com.serotonin.m2m2.web.mvc.rest.v1.model.AbstractDataSourceModel;

public class ZigBeeDataSourceDefinition extends DataSourceDefinition {
	
	public static final String TYPE_NAME = "ZIGBEE";
    
	@Override
    public String getDataSourceTypeName() {
        return TYPE_NAME;
    }

    @Override
    public String getDescriptionKey() {
        return "zigbee.dataSource";
    }

    @Override
    public DataSourceVO<?> createDataSourceVO() {
        throw new ShouldNeverHappenException("un-implemented");
    }

    @Override
    public String getEditPagePath() {
        return "web/editZigBee.jspf";
    }

    @Override
    public Class<?> getDwrClass() {
        return ZigBeeEditDwr.class;
    }

	/* (non-Javadoc)
	 * @see com.serotonin.m2m2.module.DataSourceDefinition#getModelClass()
	 */
	@Override
	public Class<? extends AbstractDataSourceModel<?>> getModelClass() {
		 throw new ShouldNeverHappenException("un-implemented");
	}
}
