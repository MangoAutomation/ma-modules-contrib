/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables.vo;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.infiniteautomation.mango.example.sqlTables.ExampleAccessCardAuditEventTypeDefinition;
import com.infiniteautomation.mango.permission.MangoPermission;
import com.serotonin.json.JsonException;
import com.serotonin.json.JsonReader;
import com.serotonin.json.ObjectWriter;
import com.serotonin.json.spi.JsonProperty;
import com.serotonin.json.type.JsonObject;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.db.dao.UserDao;
import com.serotonin.m2m2.i18n.TranslatableJsonException;
import com.serotonin.m2m2.vo.AbstractVO;

public class ExampleAccessCardVO extends AbstractVO {

    public static final String XID_PREFIX = "EAC_";

    @JsonProperty
    private MangoPermission editPermission = new MangoPermission();
    @JsonProperty
    private MangoPermission readPermission = new MangoPermission();
    private int userId;
    @JsonProperty
    private JsonNode data;

    //
    //
    // Convenience data from user
    //
    private String username;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getTypeKey() {
        return ExampleAccessCardAuditEventTypeDefinition.TYPE_KEY;
    }

    @Override
    public void jsonRead(JsonReader reader, JsonObject jsonObject) throws JsonException {
        super.jsonRead(reader, jsonObject);
        String username = jsonObject.getString("username");
        if(StringUtils.isNotEmpty(username)) {
            Integer userId = Common.getBean(UserDao.class).getIdByXid(username);
            if(userId == null) {
                throw new TranslatableJsonException("example.emport.user.invalidReference", username, xid);
            }else {
                this.userId = userId;
            }
        }
    }

    @Override
    public void jsonWrite(ObjectWriter writer) throws IOException, JsonException {
        super.jsonWrite(writer);
        writer.writeEntry("username", Common.getBean(UserDao.class).getXidById(userId));
    }
}
