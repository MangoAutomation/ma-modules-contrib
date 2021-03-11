/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.rest.latest.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAccessCardVO;
import com.infiniteautomation.mango.permission.MangoPermission;
import com.infiniteautomation.mango.rest.latest.model.permissions.MangoPermissionModel;
import com.infiniteautomation.mango.util.exception.ValidationException;
import com.serotonin.m2m2.db.dao.UserDao;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

@Component
public class ExampleAccessCardModelMapping implements RestModelMapping<ExampleAccessCardVO, ExampleAccessCardModel> {

    private final UserDao dao;

    @Autowired
    public ExampleAccessCardModelMapping(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public Class<? extends ExampleAccessCardVO> fromClass() {
        return ExampleAccessCardVO.class;
    }

    @Override
    public Class<? extends ExampleAccessCardModel> toClass() {
        return ExampleAccessCardModel.class;
    }

    @Override
    public ExampleAccessCardModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        ExampleAccessCardVO vo = (ExampleAccessCardVO)from;
        ExampleAccessCardModel model = new ExampleAccessCardModel(vo);
        model.setReadPermission(new MangoPermissionModel(vo.getReadPermission()));
        model.setEditPermission(new MangoPermissionModel(vo.getEditPermission()));
        model.setData(vo.getData());
        model.setUsername(vo.getUsername());
        return model;
    }

    @Override
    public ExampleAccessCardVO unmap(Object from, PermissionHolder user, RestModelMapper mapper) throws ValidationException {
        ExampleAccessCardModel model = (ExampleAccessCardModel)from;
        ExampleAccessCardVO vo = model.toVO();
        vo.setReadPermission(model.getReadPermission() != null ? model.getReadPermission().getPermission() : new MangoPermission());
        vo.setEditPermission(model.getEditPermission() != null ? model.getEditPermission().getPermission() : new MangoPermission());
        vo.setUserId(dao.getIdByXid(model.getUsername()));
        vo.setData(model.getData());
        return vo;
    }
}
