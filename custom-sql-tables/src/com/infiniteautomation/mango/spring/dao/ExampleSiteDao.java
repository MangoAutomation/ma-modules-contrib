/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.spring.dao;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infiniteautomation.mango.example.sqlTables.ExampleSiteAuditEventTypeDefinition;
import com.infiniteautomation.mango.example.sqlTables.db.tables.ExampleSites;
import com.infiniteautomation.mango.example.sqlTables.db.tables.records.ExampleSitesRecord;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;
import com.infiniteautomation.mango.permission.MangoPermission;
import com.infiniteautomation.mango.spring.MangoRuntimeContextConfiguration;
import com.infiniteautomation.mango.spring.service.PermissionService;
import com.serotonin.m2m2.db.dao.AbstractVoDao;

@Repository
public class ExampleSiteDao extends AbstractVoDao<ExampleSiteVO, ExampleSitesRecord, ExampleSites> {

    private final ExampleAccessCardDao accessCardDao;

    @Autowired
    private ExampleSiteDao(
            @Qualifier(MangoRuntimeContextConfiguration.DAO_OBJECT_MAPPER_NAME) ObjectMapper mapper,
            ApplicationEventPublisher publisher,
            PermissionService permissionService,
            ExampleAccessCardDao accessCardDao) {
        super(ExampleSiteAuditEventTypeDefinition.TYPE_NAME, ExampleSites.EXAMPLE_SITES, mapper, publisher, permissionService);
        this.accessCardDao = accessCardDao;
    }

    @Override
    protected Record toRecord(ExampleSiteVO vo) {
        Record record = table.newRecord();
        record.set(table.xid, vo.getXid());
        record.set(table.name, vo.getName());
        record.set(table.readPermissionId, vo.getReadPermission().getId());
        record.set(table.editPermissionId, vo.getEditPermission().getId());
        record.set(table.data, convertData(vo.getData()));
        return record;
    }

    @Override
    public @NonNull ExampleSiteVO mapRecord(@NonNull Record record) {
        ExampleSiteVO vo = new ExampleSiteVO();
        vo.setId(record.get(table.id));
        vo.setXid(record.get(table.xid));
        vo.setName(record.get(table.name));
        vo.setReadPermission(new MangoPermission(record.get(table.readPermissionId)));
        vo.setEditPermission(new MangoPermission(record.get(table.editPermissionId)));
        vo.setData(extractDataFromObject(record.get(table.data)));
        return vo;
    }

    @Override
    protected String getXidPrefix() {
        return ExampleSiteVO.XID_PREFIX;
    }

    @Override
    public void savePreRelationalData(ExampleSiteVO existing, ExampleSiteVO vo) {
        MangoPermission readPermission = permissionService.findOrCreate(vo.getReadPermission());
        vo.setReadPermission(readPermission);

        MangoPermission editPermission = permissionService.findOrCreate(vo.getEditPermission());
        vo.setEditPermission(editPermission);
    }

    @Override
    public void saveRelationalData(ExampleSiteVO existing, ExampleSiteVO vo) {
        if(existing != null) {
            if (!existing.getReadPermission().equals(vo.getReadPermission())) {
                permissionService.deletePermissions(existing.getReadPermission());
            }
            if (!existing.getEditPermission().equals(vo.getEditPermission())) {
                permissionService.deletePermissions(existing.getEditPermission());
            }
        }
    }

    @Override
    public void loadRelationalData(ExampleSiteVO vo) {
        //Populate permissions
        vo.setReadPermission(permissionService.get(vo.getReadPermission().getId()));
        vo.setEditPermission(permissionService.get(vo.getEditPermission().getId()));
    }

    @Override
    public void deletePostRelationalData(ExampleSiteVO vo) {
        //Clean permissions
        permissionService.deletePermissions(vo.getReadPermission(), vo.getEditPermission());
    }
}
