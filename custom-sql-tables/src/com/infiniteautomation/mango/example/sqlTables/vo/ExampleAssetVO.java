/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables.vo;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.infiniteautomation.mango.example.sqlTables.ExampleAssetAuditEventTypeDefinition;
import com.infiniteautomation.mango.permission.MangoPermission;
import com.infiniteautomation.mango.spring.dao.ExampleSiteDao;
import com.serotonin.json.JsonException;
import com.serotonin.json.JsonReader;
import com.serotonin.json.ObjectWriter;
import com.serotonin.json.spi.JsonProperty;
import com.serotonin.json.type.JsonObject;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.i18n.TranslatableJsonException;
import com.serotonin.m2m2.vo.AbstractVO;

public class ExampleAssetVO extends AbstractVO {

    public static final String XID_PREFIX = "ES_";

    @JsonProperty
    private MangoPermission editPermission = new MangoPermission();
    @JsonProperty
    private MangoPermission readPermission = new MangoPermission();
    private int siteId;
    @JsonProperty
    private JsonNode data;

    //
    //
    // Convenience data from site
    //
    private String siteName;
    private String siteXid;

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

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteXid() {
        return siteXid;
    }

    public void setSiteXid(String siteXid) {
        this.siteXid = siteXid;
    }

    @Override
    public String getTypeKey() {
        return ExampleAssetAuditEventTypeDefinition.TYPE_KEY;
    }

    @Override
    public void jsonRead(JsonReader reader, JsonObject jsonObject) throws JsonException {
        super.jsonRead(reader, jsonObject);
        String siteXid = jsonObject.getString("siteXid");
        if(StringUtils.isNotEmpty(siteXid)) {
            Integer siteId = Common.getBean(ExampleSiteDao.class).getIdByXid(siteXid);
            if(siteId == null) {
                throw new TranslatableJsonException("example.emport.site.invalidReference", siteXid);
            }else {
                this.siteId = siteId;
            }
        }
    }

    @Override
    public void jsonWrite(ObjectWriter writer) throws IOException, JsonException {
        super.jsonWrite(writer);
        writer.writeEntry("siteXid", Common.getBean(ExampleSiteDao.class).getXidById(siteId));
    }
}
