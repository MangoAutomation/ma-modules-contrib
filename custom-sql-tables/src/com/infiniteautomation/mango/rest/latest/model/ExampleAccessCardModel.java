/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.rest.latest.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAccessCardVO;
import com.infiniteautomation.mango.rest.latest.model.permissions.MangoPermissionModel;

public class ExampleAccessCardModel extends AbstractVoModel<ExampleAccessCardVO> {

    private MangoPermissionModel readPermission;
    private MangoPermissionModel editPermission;
    private String username;
    private JsonNode data;

    public ExampleAccessCardModel(ExampleAccessCardVO data) {
        fromVO(data);
    }

    public ExampleAccessCardModel() {
        super();
    }

    @Override
    protected ExampleAccessCardVO newVO() {
        return new ExampleAccessCardVO();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
