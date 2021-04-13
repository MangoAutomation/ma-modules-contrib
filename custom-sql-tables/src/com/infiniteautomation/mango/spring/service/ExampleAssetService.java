/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAssetVO;
import com.infiniteautomation.mango.spring.dao.ExampleAssetDao;
import com.serotonin.m2m2.i18n.ProcessResult;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

@Service
public class ExampleAssetService extends AbstractVOService<ExampleAssetVO, ExampleAssetDao> {

    @Autowired
    public ExampleAssetService(ExampleAssetDao exampleSiteDao, PermissionService permissionService) {
        super(exampleSiteDao, permissionService);
    }

    @Override
    public boolean hasEditPermission(PermissionHolder user, ExampleAssetVO vo) {
        return permissionService.hasPermission(user, vo.getEditPermission());
    }

    @Override
    public boolean hasReadPermission(PermissionHolder user, ExampleAssetVO vo) {
        return permissionService.hasPermission(user, vo.getReadPermission());
    }

    @Override
    public ProcessResult validate(ExampleAssetVO vo, PermissionHolder user) {
        ProcessResult result = super.validate(vo, user);
        permissionService.validatePermission(result, "readPermission", user, vo.getReadPermission());
        permissionService.validatePermission(result, "editPermission", user, vo.getEditPermission());

        return result;
    }

    @Override
    public ProcessResult validate(ExampleAssetVO existing, ExampleAssetVO vo, PermissionHolder user) {
        ProcessResult result = super.validate(existing, vo, user);

        //Additional checks for existing list
        permissionService.validatePermission(result, "readPermission", user, vo.getReadPermission());
        permissionService.validatePermission(result, "editPermission", user, vo.getEditPermission());

        return result;
    }


    public List<ExampleAssetVO> getAssetsForSite(int siteId){
        return dao.getAssetsForSite(siteId);
    }
}
