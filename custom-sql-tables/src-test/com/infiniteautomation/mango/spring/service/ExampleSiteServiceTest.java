/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.spring.service;

import org.junit.Assert;
import org.junit.BeforeClass;

import com.fasterxml.jackson.databind.node.TextNode;
import com.infiniteautomation.mango.example.sqlTables.ExampleSiteCreatePermission;
import com.infiniteautomation.mango.example.sqlTables.db.tables.ExampleSites;
import com.infiniteautomation.mango.example.sqlTables.db.tables.records.ExampleSitesRecord;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;
import com.infiniteautomation.mango.permission.MangoPermission;
import com.infiniteautomation.mango.spring.dao.ExampleSiteDao;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.vo.User;

public class ExampleSiteServiceTest extends AbstractVOServiceWithPermissionsTest<ExampleSiteVO,
        ExampleSitesRecord, ExampleSites, ExampleSiteDao, ExampleSiteService> {

    @BeforeClass
    public static void beforeClass() {
        loadModules();
    }

    @Override
    protected ExampleSiteService getService() {
        return Common.getBean(ExampleSiteService.class);
    }

    @Override
    protected ExampleSiteDao getDao() {
        return Common.getBean(ExampleSiteDao.class);
    }

    @Override
    protected void assertVoEqual(ExampleSiteVO expected, ExampleSiteVO actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getXid(), actual.getXid());
        Assert.assertEquals(expected.getReadPermission(), actual.getReadPermission());
        Assert.assertEquals(expected.getEditPermission(), actual.getEditPermission());
    }

    @Override
    protected ExampleSiteVO newVO(User owner) {
        ExampleSiteVO vo = new ExampleSiteVO();
        vo.setName("Test site");
        vo.setReadPermission(new MangoPermission());
        vo.setEditPermission(new MangoPermission());
        vo.setData(TextNode.valueOf("testing"));
        return vo;
    }

    @Override
    protected ExampleSiteVO updateVO(ExampleSiteVO vo) {
        vo.setName("Test site1");
        vo.setReadPermission(new MangoPermission());
        vo.setEditPermission(new MangoPermission());
        vo.setData(TextNode.valueOf("testing1"));
        return vo;
    }

    @Override
    protected String getCreatePermissionType() {
        return ExampleSiteCreatePermission.PERMISSION;
    }

    @Override
    protected void setReadPermission(MangoPermission permission, ExampleSiteVO vo) {
        vo.setReadPermission(permission);
    }

    @Override
    protected String getReadPermissionContextKey() {
        return "readPermission";
    }

    @Override
    protected void setEditPermission(MangoPermission permission, ExampleSiteVO vo) {
        vo.setEditPermission(permission);
    }

    @Override
    protected String getEditPermissionContextKey() {
        return "editPermission";
    }

}
