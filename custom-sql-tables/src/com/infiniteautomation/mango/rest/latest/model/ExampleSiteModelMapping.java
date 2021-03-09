/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.rest.latest.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAssetVO;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;
import com.infiniteautomation.mango.permission.MangoPermission;
import com.infiniteautomation.mango.rest.latest.model.permissions.MangoPermissionModel;
import com.infiniteautomation.mango.spring.service.ExampleAssetService;
import com.infiniteautomation.mango.util.exception.ValidationException;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

@Component
public class ExampleSiteModelMapping implements RestModelMapping<ExampleSiteVO, ExampleSiteModel> {

    private final ExampleAssetService exampleAssetService;
    @Autowired
    public ExampleSiteModelMapping(ExampleAssetService exampleAssetService) {
        this.exampleAssetService = exampleAssetService;
    }

    @Override
    public Class<? extends ExampleSiteVO> fromClass() {
        return ExampleSiteVO.class;
    }

    @Override
    public Class<? extends ExampleSiteModel> toClass() {
        return ExampleSiteModel.class;
    }

    @Override
    public ExampleSiteModel map(Object from, PermissionHolder user, RestModelMapper mapper) {
        ExampleSiteVO vo = (ExampleSiteVO)from;
        ExampleSiteModel model = new ExampleSiteModel(vo);
        model.setReadPermission(new MangoPermissionModel(vo.getReadPermission()));
        model.setEditPermission(new MangoPermissionModel(vo.getEditPermission()));
        model.setData(vo.getData());

        List<ExampleAssetModel> assets = new ArrayList<>();
        model.setAssets(assets);

        List<ExampleAssetVO> assetVOS = exampleAssetService.getAssetsForSite(vo.getId());
        for(ExampleAssetVO assetVO : assetVOS) {
            assets.add(mapper.map(assetVO, ExampleAssetModel.class, user));
        }
        return model;
    }

    @Override
    public ExampleSiteVO unmap(Object from, PermissionHolder user, RestModelMapper mapper) throws ValidationException {
        ExampleSiteModel model = (ExampleSiteModel)from;
        ExampleSiteVO vo = model.toVO();
        vo.setReadPermission(model.getReadPermission() != null ? model.getReadPermission().getPermission() : new MangoPermission());
        vo.setEditPermission(model.getEditPermission() != null ? model.getEditPermission().getPermission() : new MangoPermission());
        vo.setData(model.getData());
        return vo;
    }
}
