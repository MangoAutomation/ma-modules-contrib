/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.systemSettings.rt;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.infiniteautomation.mango.systemSettings.vo.SystemSettingsPointLocatorVO;
import com.serotonin.m2m2.rt.dataImage.types.AlphanumericValue;
import com.serotonin.m2m2.rt.dataImage.types.BinaryValue;
import com.serotonin.m2m2.rt.dataImage.types.DataValue;
import com.serotonin.m2m2.rt.dataImage.types.MultistateValue;
import com.serotonin.m2m2.rt.dataSource.PointLocatorRT;

/**
 * 
 * Runtime container for an example point
 * 
 * @author Terry Packer
 *
 */
public class SystemSettingsPointLocatorRT extends PointLocatorRT<SystemSettingsPointLocatorVO> {

    private final JsonPointer jsonPointer;

    public SystemSettingsPointLocatorRT(SystemSettingsPointLocatorVO vo) {
        super(vo);
        this.jsonPointer = JsonPointer.valueOf("/" + vo.getSettingKey());
    }

    public DataValue getValue(JsonNode root) {
        JsonNode node = root.at(jsonPointer);
        switch(vo.getDataType()) {
            case BINARY:
                return new BinaryValue(node.asBoolean());
            case MULTISTATE:
                return new MultistateValue((node.asInt()));
            case ALPHANUMERIC:
            default:
                return new AlphanumericValue(node.asText());
        }

    }
}
