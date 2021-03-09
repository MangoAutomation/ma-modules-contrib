/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.rest.latest.model;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;
import com.infiniteautomation.mango.rest.latest.model.permissions.MangoPermissionModel;

public class ExampleSiteModel extends AbstractVoModel<ExampleSiteVO> {

    private MangoPermissionModel readPermission;
    private MangoPermissionModel editPermission;
    private JsonNode data;
    private List<ExampleAssetModel> assets;

    public ExampleSiteModel(ExampleSiteVO data) {
        fromVO(data);
    }

    public ExampleSiteModel() {
        super();
    }

    @Override
    protected ExampleSiteVO newVO() {
        return new ExampleSiteVO();
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

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public List<ExampleAssetModel> getAssets() {
        return assets;
    }

    public void setAssets(List<ExampleAssetModel> assets) {
        this.assets = assets;
    }
}
