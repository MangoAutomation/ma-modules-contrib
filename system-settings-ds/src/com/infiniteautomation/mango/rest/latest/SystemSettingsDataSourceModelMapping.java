/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.systemSettings.SystemSettingsDataSourceDefinition;
import com.infiniteautomation.mango.systemSettings.vo.SystemSettingsDataSourceVO;
import com.infiniteautomation.mango.rest.latest.model.SystemSettingsDataSourceModel;
import com.infiniteautomation.mango.rest.latest.model.RestModelJacksonMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

/**
 * @author Terry Packer
 *
 */
@Component
public class SystemSettingsDataSourceModelMapping implements RestModelJacksonMapping<SystemSettingsDataSourceVO, SystemSettingsDataSourceModel> {

    @Override
    public Class<? extends SystemSettingsDataSourceVO> fromClass() {
        return SystemSettingsDataSourceVO.class;
    }

    @Override
    public Class<? extends SystemSettingsDataSourceModel> toClass() {
        return SystemSettingsDataSourceModel.class;
    }

    @Override
    public SystemSettingsDataSourceModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        return new SystemSettingsDataSourceModel((SystemSettingsDataSourceVO)from);
    }

    @Override
    public String getTypeName() {
        return SystemSettingsDataSourceDefinition.TYPE_NAME;
    }

}
