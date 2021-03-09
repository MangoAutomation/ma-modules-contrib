/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.rest.latest.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAssetVO;
import com.infiniteautomation.mango.permission.MangoPermission;
import com.infiniteautomation.mango.rest.latest.model.permissions.MangoPermissionModel;
import com.infiniteautomation.mango.spring.dao.ExampleSiteDao;
import com.infiniteautomation.mango.util.exception.ValidationException;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

@Component
public class ExampleAssetModelMapping implements RestModelMapping<ExampleAssetVO, ExampleAssetModel> {

    private final ExampleSiteDao dao;

    @Autowired
    public ExampleAssetModelMapping(ExampleSiteDao dao) {
        this.dao = dao;
    }

    @Override
    public Class<? extends ExampleAssetVO> fromClass() {
        return ExampleAssetVO.class;
    }

    @Override
    public Class<? extends ExampleAssetModel> toClass() {
        return ExampleAssetModel.class;
    }

    @Override
    public ExampleAssetModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        ExampleAssetVO vo = (ExampleAssetVO)from;
        ExampleAssetModel model = new ExampleAssetModel(vo);
        model.setReadPermission(new MangoPermissionModel(vo.getReadPermission()));
        model.setEditPermission(new MangoPermissionModel(vo.getEditPermission()));

        model.setData(vo.getData());

        model.setSiteXid(vo.getSiteXid());
        model.setSiteName(vo.getSiteName());
        return model;
    }

    @Override
    public ExampleAssetVO unmap(Object from, PermissionHolder user, RestModelMapper mapper) throws ValidationException {
        ExampleAssetModel model = (ExampleAssetModel)from;
        ExampleAssetVO vo = model.toVO();
        vo.setReadPermission(model.getReadPermission() != null ? model.getReadPermission().getPermission() : new MangoPermission());
        vo.setEditPermission(model.getEditPermission() != null ? model.getEditPermission().getPermission() : new MangoPermission());
        vo.setSiteId(dao.getIdByXid(model.getSiteXid()));
        vo.setData(model.getData());
        return vo;
    }
}
