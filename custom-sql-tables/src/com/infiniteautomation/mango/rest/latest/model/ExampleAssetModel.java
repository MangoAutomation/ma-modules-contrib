/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.rest.latest.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAssetVO;
import com.infiniteautomation.mango.rest.latest.model.permissions.MangoPermissionModel;

public class ExampleAssetModel extends AbstractVoModel<ExampleAssetVO> {

    private MangoPermissionModel readPermission;
    private MangoPermissionModel editPermission;
    private String siteXid;
    private JsonNode data;

    private String siteName;

    public ExampleAssetModel(ExampleAssetVO data) {
        fromVO(data);
    }

    public ExampleAssetModel() {
        super();
    }

    @Override
    protected ExampleAssetVO newVO() {
        return new ExampleAssetVO();
    }

    public MangoPermissionModel getReadPermission() {
        return readPermission;
    }

    public void setReadPermission(MangoPermissionModel readPermission) {
        this.readPermission = readPermission;
    }

    public MangoPermissionModel getEditPermission() {
        return editPermission;
    }

    public void setEditPermission(MangoPermissionModel editPermission) {
        this.editPermission = editPermission;
    }

    public String getSiteXid() {
        return siteXid;
    }

    public void setSiteXid(String siteXid) {
        this.siteXid = siteXid;
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
}
