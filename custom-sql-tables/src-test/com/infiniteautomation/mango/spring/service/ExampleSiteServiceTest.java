/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.spring.service;

import java.util.Collections;

import org.junit.Assert;

import com.fasterxml.jackson.databind.node.TextNode;
import com.infiniteautomation.mango.example.sqlTables.ExampleSiteCreatePermission;
import com.infiniteautomation.mango.example.sqlTables.db.tables.ExampleSites;
import com.infiniteautomation.mango.example.sqlTables.db.tables.records.ExampleSitesRecord;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;
import com.infiniteautomation.mango.permission.MangoPermission;
import com.infiniteautomation.mango.spring.dao.ExampleSiteDao;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.vo.User;
import com.serotonin.m2m2.vo.role.Role;

public class ExampleSiteServiceTest extends AbstractVOServiceWithPermissionsTest<ExampleSiteVO,
        ExampleSitesRecord, ExampleSites, ExampleSiteDao, ExampleSiteService> {


    @Override
    ExampleSiteService getService() {
        return Common.getBean(ExampleSiteService.class);
    }

    @Override
    ExampleSiteDao getDao() {
        return Common.getBean(ExampleSiteDao.class);
    }

    @Override
    void assertVoEqual(ExampleSiteVO expected, ExampleSiteVO actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getXid(), actual.getXid());
        Assert.assertEquals(expected.getReadPermission(), actual.getReadPermission());
        Assert.assertEquals(expected.getEditPermission(), actual.getEditPermission());
    }

    @Override
    ExampleSiteVO newVO(User owner) {
        ExampleSiteVO vo = new ExampleSiteVO();
        vo.setName("Test site");
        vo.setReadPermission(new MangoPermission());
        vo.setEditPermission(new MangoPermission());
        vo.setData(TextNode.valueOf("testing"));
        return vo;
    }

    @Override
    ExampleSiteVO updateVO(ExampleSiteVO vo) {
        vo.setName("Test site1");
        vo.setReadPermission(new MangoPermission());
        vo.setEditPermission(new MangoPermission());
        vo.setData(TextNode.valueOf("testing1"));
        return vo;
    }

    @Override
    String getCreatePermissionType() {
        return ExampleSiteCreatePermission.PERMISSION;
    }

    @Override
    void setReadPermission(MangoPermission permission, ExampleSiteVO vo) {
        vo.setReadPermission(permission);
    }

    @Override
    void addReadRoleToFail(Role role, ExampleSiteVO vo) {
        vo.getReadPermission().getRoles().add(Collections.singleton(role));
    }

    @Override
    String getReadRolesContextKey() {
        return "readPermission";
    }

    @Override
    void setEditPermission(MangoPermission permission, ExampleSiteVO vo) {
        vo.setEditPermission(permission);
    }

    @Override
    void addEditRoleToFail(Role role, ExampleSiteVO vo) {
        vo.getEditPermission().getRoles().add(Collections.singleton(role));
    }

    @Override
    String getEditRolesContextKey() {
        return "editPermission";
    }
}
