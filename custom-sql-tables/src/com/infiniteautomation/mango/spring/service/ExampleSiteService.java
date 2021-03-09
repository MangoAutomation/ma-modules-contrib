/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infiniteautomation.mango.example.sqlTables.ExampleSiteCreatePermission;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;
import com.infiniteautomation.mango.spring.dao.ExampleSiteDao;
import com.serotonin.m2m2.i18n.ProcessResult;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

@Service
public class ExampleSiteService extends AbstractVOService<ExampleSiteVO, ExampleSiteDao> {

    private final ExampleSiteCreatePermission createPermission;

    @Autowired
    public ExampleSiteService(ExampleSiteDao exampleSiteDao, PermissionService permissionService,
                              ExampleSiteCreatePermission createPermission) {
        super(exampleSiteDao, permissionService);
        this.createPermission = createPermission;
    }

    @Override
    public ExampleSiteCreatePermission getCreatePermission() {
        return createPermission;
    }

    @Override
    public boolean hasEditPermission(PermissionHolder user, ExampleSiteVO vo) {
        return permissionService.hasPermission(user, vo.getEditPermission());
    }

    @Override
    public boolean hasReadPermission(PermissionHolder user, ExampleSiteVO vo) {
        return permissionService.hasPermission(user, vo.getReadPermission());
    }

    @Override
    public ProcessResult validate(ExampleSiteVO vo, PermissionHolder user) {
        ProcessResult result = super.validate(vo, user);
        permissionService.validatePermission(result, "readPermission", user, null, vo.getReadPermission());
        permissionService.validatePermission(result, "editPermission", user, null, vo.getEditPermission());

        return result;
    }

    @Override
    public ProcessResult validate(ExampleSiteVO existing, ExampleSiteVO vo, PermissionHolder user) {
        ProcessResult result = super.validate(existing, vo, user);

        //Additional checks for existing list
        permissionService.validatePermission(result, "readPermission", user, existing.getReadPermission(), vo.getReadPermission());
        permissionService.validatePermission(result, "editPermission", user, existing.getEditPermission(), vo.getEditPermission());

        return result;
    }


}
