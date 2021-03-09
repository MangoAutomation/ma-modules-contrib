/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables.vo;

import com.fasterxml.jackson.databind.JsonNode;
import com.infiniteautomation.mango.example.sqlTables.ExampleSiteAuditEventTypeDefinition;
import com.infiniteautomation.mango.permission.MangoPermission;
import com.serotonin.json.spi.JsonProperty;
import com.serotonin.m2m2.vo.AbstractVO;

public class ExampleSiteVO extends AbstractVO {

    public static final String XID_PREFIX = "ES_";

    @JsonProperty
    private MangoPermission editPermission = new MangoPermission();
    @JsonProperty
    private MangoPermission readPermission = new MangoPermission();
    @JsonProperty
    private JsonNode data;

    public MangoPermission getEditPermission() {
        return editPermission;
    }

    public void setEditPermission(MangoPermission editPermission) {
        this.editPermission = editPermission;
    }

    public MangoPermission getReadPermission() {
        return readPermission;
    }

    public void setReadPermission(MangoPermission readPermission) {
        this.readPermission = readPermission;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    @Override
    public String getTypeKey() {
        return ExampleSiteAuditEventTypeDefinition.TYPE_KEY;
    }
}
