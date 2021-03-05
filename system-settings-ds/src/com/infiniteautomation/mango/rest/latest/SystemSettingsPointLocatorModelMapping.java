/**
 * Copyright (C) 2019  Terry Packer. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest;

import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.systemSettings.vo.SystemSettingsPointLocatorVO;
import com.infiniteautomation.mango.rest.latest.model.RestModelJacksonMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.infiniteautomation.mango.rest.latest.model.SystemSettingsPointLocatorModel;
import com.serotonin.m2m2.vo.permission.PermissionHolder;


/**
 * This class is used to map the VO to and from a REST Model
 * @author Terry Packer
 *
 */
@Component
public class SystemSettingsPointLocatorModelMapping implements RestModelJacksonMapping<SystemSettingsPointLocatorVO, SystemSettingsPointLocatorModel> {

    @Override
    public Class<? extends SystemSettingsPointLocatorVO> fromClass() {
        return SystemSettingsPointLocatorVO.class;
    }

    @Override
    public Class<? extends SystemSettingsPointLocatorModel> toClass() {
        return SystemSettingsPointLocatorModel.class;
    }

    @Override
    public SystemSettingsPointLocatorModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        return new SystemSettingsPointLocatorModel((SystemSettingsPointLocatorVO)from);
    }

    @Override
    public String getTypeName() {
        return SystemSettingsPointLocatorModel.TYPE_NAME;
    }

}
