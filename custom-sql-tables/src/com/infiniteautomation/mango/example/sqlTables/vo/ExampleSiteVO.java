/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables.vo;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.infiniteautomation.mango.example.sqlTables.ExampleSiteAuditEventTypeDefinition;
import com.infiniteautomation.mango.permission.MangoPermission;
import com.infiniteautomation.mango.spring.dao.ExampleAccessCardDao;
import com.serotonin.json.JsonException;
import com.serotonin.json.ObjectWriter;
import com.serotonin.json.spi.JsonProperty;
import com.serotonin.m2m2.Common;
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

    @Override
    public void jsonWrite(ObjectWriter writer) throws IOException, JsonException {
        super.jsonWrite(writer);
        //Write out any access cards we have
        writer.writeEntry("accessCardXids", Common.getBean(ExampleAccessCardDao.class).getCardXidsForSite(id));
    }

}
