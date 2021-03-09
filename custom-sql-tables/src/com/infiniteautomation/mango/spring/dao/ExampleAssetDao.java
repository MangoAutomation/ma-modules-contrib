/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infiniteautomation.mango.db.query.ConditionSortLimit;
import com.infiniteautomation.mango.example.sqlTables.ExampleAssetAuditEventTypeDefinition;
import com.infiniteautomation.mango.example.sqlTables.db.tables.ExampleAssets;
import com.infiniteautomation.mango.example.sqlTables.db.tables.ExampleSites;
import com.infiniteautomation.mango.example.sqlTables.db.tables.records.ExampleAssetsRecord;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAssetVO;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;
import com.infiniteautomation.mango.permission.MangoPermission;
import com.infiniteautomation.mango.spring.MangoRuntimeContextConfiguration;
import com.infiniteautomation.mango.spring.service.PermissionService;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.db.dao.AbstractVoDao;

@Repository
public class ExampleAssetDao extends AbstractVoDao<ExampleAssetVO, ExampleAssetsRecord, ExampleAssets> {

    @Autowired
    private ExampleAssetDao(
            @Qualifier(MangoRuntimeContextConfiguration.DAO_OBJECT_MAPPER_NAME) ObjectMapper mapper,
            ApplicationEventPublisher publisher,
            PermissionService permissionService) {
        super(ExampleAssetAuditEventTypeDefinition.TYPE_NAME, ExampleAssets.EXAMPLE_ASSETS, mapper, publisher, permissionService);
    }

    @Override
    protected Record toRecord(ExampleAssetVO vo) {
        Record record = table.newRecord();
        record.set(table.xid, vo.getXid());
        record.set(table.name, vo.getName());
        record.set(table.readPermissionId, vo.getReadPermission().getId());
        record.set(table.editPermissionId, vo.getEditPermission().getId());
        record.set(table.siteId, vo.getSiteId());
        record.set(table.data, convertData(vo.getData()));
        return record;
    }

    @Override
    public @NonNull ExampleAssetVO mapRecord(@NonNull Record record) {
        ExampleAssetVO vo = new ExampleAssetVO();
        vo.setId(record.get(table.id));
        vo.setXid(record.get(table.xid));
        vo.setName(record.get(table.name));
        vo.setReadPermission(new MangoPermission(record.get(table.readPermissionId)));
        vo.setEditPermission(new MangoPermission(record.get(table.editPermissionId)));
        vo.setSiteId(record.get(table.siteId));
        vo.setData(extractDataFromObject(record.get(table.data)));
        vo.setSiteName(record.get(ExampleSites.EXAMPLE_SITES.name));
        vo.setSiteXid(record.get(ExampleSites.EXAMPLE_SITES.xid));
        return vo;
    }

    @Override
    protected String getXidPrefix() {
        return ExampleSiteVO.XID_PREFIX;
    }

    @Override
    public void savePreRelationalData(ExampleAssetVO existing, ExampleAssetVO vo) {
        MangoPermission readPermission = permissionService.findOrCreate(vo.getReadPermission());
        vo.setReadPermission(readPermission);

        MangoPermission editPermission = permissionService.findOrCreate(vo.getEditPermission());
        vo.setEditPermission(editPermission);
    }

    @Override
    public void saveRelationalData(ExampleAssetVO existing, ExampleAssetVO vo) {
        if(existing != null) {
            if(!existing.getReadPermission().equals(vo.getReadPermission())) {
                permissionService.deletePermissions(existing.getReadPermission());
            }
            if(!existing.getEditPermission().equals(vo.getEditPermission())) {
                permissionService.deletePermissions(existing.getEditPermission());
            }
        }
    }

    @Override
    public void loadRelationalData(ExampleAssetVO vo) {
        //Populate permissions
        vo.setReadPermission(permissionService.get(vo.getReadPermission().getId()));
        vo.setEditPermission(permissionService.get(vo.getEditPermission().getId()));
    }

    @Override
    public void deletePostRelationalData(ExampleAssetVO vo) {
        //Clean permissions
        permissionService.deletePermissions(vo.getReadPermission(), vo.getEditPermission());
    }

    @Override
    public List<Field<?>> getSelectFields() {
        List<Field<?>> fields = new ArrayList<>(super.getSelectFields());
        fields.add(ExampleSites.EXAMPLE_SITES.xid);
        fields.add(ExampleSites.EXAMPLE_SITES.name);
        return fields;
    }

    @Override
    public <R extends Record> SelectJoinStep<R> joinTables(SelectJoinStep<R> select,
                                                           ConditionSortLimit conditions) {
        return select.join(ExampleSites.EXAMPLE_SITES).on(table.siteId.eq(ExampleSites.EXAMPLE_SITES.id));
    }

    /**
     * Count the assets for a site
     * @param vo
     * @return
     */
    public Integer countAssetsForSite(ExampleSiteVO vo) {
        return this.getCountQuery().from(table).where(table.siteId.eq(vo.getId())).fetchOneInto(Integer.class);
    }

    public List<ExampleAssetVO> getAssetsForSite(int siteId) {
        return joinPermissions(this.getJoinedSelectQuery(), Common.getUser()).where(table.siteId.eq(siteId)).fetch(this::mapRecord);
    }
}
