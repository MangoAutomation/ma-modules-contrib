/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.spring.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.infiniteautomation.mango.db.tables.Users;
import com.infiniteautomation.mango.example.sqlTables.ExampleAssetAuditEventTypeDefinition;
import com.infiniteautomation.mango.example.sqlTables.db.tables.ExampleAccessCardSites;
import com.infiniteautomation.mango.example.sqlTables.db.tables.ExampleAccessCards;
import com.infiniteautomation.mango.example.sqlTables.db.tables.records.ExampleAccessCardsRecord;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAccessCardVO;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;
import com.infiniteautomation.mango.permission.MangoPermission;
import com.infiniteautomation.mango.spring.MangoRuntimeContextConfiguration;
import com.infiniteautomation.mango.spring.service.PermissionService;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.db.dao.AbstractVoDao;

@Repository
public class ExampleAccessCardDao extends AbstractVoDao<ExampleAccessCardVO, ExampleAccessCardsRecord, ExampleAccessCards> {

    @Autowired
    private ExampleAccessCardDao(
            @Qualifier(MangoRuntimeContextConfiguration.DAO_OBJECT_MAPPER_NAME) ObjectMapper mapper,
            ApplicationEventPublisher publisher,
            PermissionService permissionService) {
        super(ExampleAssetAuditEventTypeDefinition.TYPE_NAME, ExampleAccessCards.EXAMPLE_ACCESS_CARDS, mapper, publisher, permissionService);
    }

    @Override
    protected Record toRecord(ExampleAccessCardVO vo) {
        Record record = table.newRecord();
        record.set(table.xid, vo.getXid());
        record.set(table.name, vo.getName());
        record.set(table.readPermissionId, vo.getReadPermission().getId());
        record.set(table.editPermissionId, vo.getEditPermission().getId());
        record.set(table.userId, vo.getUserId());
        record.set(table.data, convertData(vo.getData()));
        return record;
    }

    @Override
    public @NonNull ExampleAccessCardVO mapRecord(@NonNull Record record) {
        ExampleAccessCardVO vo = new ExampleAccessCardVO();
        vo.setId(record.get(table.id));
        vo.setXid(record.get(table.xid));
        vo.setName(record.get(table.name));
        vo.setReadPermission(new MangoPermission(record.get(table.readPermissionId)));
        vo.setEditPermission(new MangoPermission(record.get(table.editPermissionId)));
        vo.setUserId(record.get(table.userId));
        vo.setData(extractDataFromObject(record.get(table.data)));
        vo.setUsername(record.get(Users.USERS.name));
        return vo;
    }

    @Override
    protected String getXidPrefix() {
        return ExampleAccessCardVO.XID_PREFIX;
    }

    @Override
    public void savePreRelationalData(ExampleAccessCardVO existing, ExampleAccessCardVO vo) {
        MangoPermission readPermission = permissionService.findOrCreate(vo.getReadPermission());
        vo.setReadPermission(readPermission);

        MangoPermission editPermission = permissionService.findOrCreate(vo.getEditPermission());
        vo.setEditPermission(editPermission);
    }

    @Override
    public void saveRelationalData(ExampleAccessCardVO existing, ExampleAccessCardVO vo) {
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
    public void loadRelationalData(ExampleAccessCardVO vo) {
        //Populate permissions
        vo.setReadPermission(permissionService.get(vo.getReadPermission().getId()));
        vo.setEditPermission(permissionService.get(vo.getEditPermission().getId()));
    }

    @Override
    public void deletePostRelationalData(ExampleAccessCardVO vo) {
        //Clean permissions
        permissionService.deletePermissions(vo.getReadPermission(), vo.getEditPermission());
    }

    @Override
    public List<Field<?>> getSelectFields() {
        List<Field<?>> fields = new ArrayList<>(super.getSelectFields());
        fields.add(Users.USERS.username);
        return fields;
    }

    @Override
    public <R extends Record> SelectJoinStep<R> joinTables(SelectJoinStep<R> select,
                                                           ConditionSortLimit conditions) {
        return select.join(Users.USERS).on(table.userId.eq(Users.USERS.id));
    }

    /**
     * Get all cards for a site
     * @param id
     * @return
     */
    public List<ExampleAccessCardVO> getCardsForSite(int id) {
        SelectJoinStep<Record> query = getSelectQuery(getSelectFields());
        query = joinTables(query, null);
        query = joinPermissions(query, Common.getUser());
        return query.join(ExampleAccessCardSites.EXAMPLE_ACCESS_CARD_SITES)
                .on(ExampleAccessCards.EXAMPLE_ACCESS_CARDS.id.eq(ExampleAccessCardSites.EXAMPLE_ACCESS_CARD_SITES.accessCardId))
                .where(ExampleAccessCardSites.EXAMPLE_ACCESS_CARD_SITES.siteId.eq(id))
                .stream().map(this::mapRecordLoadRelationalData)
                .collect(Collectors.toList());
    }

    public void insertCardMappingForSite(ExampleSiteVO site, ExampleAccessCardVO card) {
        this.create.insertInto(ExampleAccessCardSites.EXAMPLE_ACCESS_CARD_SITES)
                .set(ExampleAccessCardSites.EXAMPLE_ACCESS_CARD_SITES.accessCardId, card.getId())
                .set(ExampleAccessCardSites.EXAMPLE_ACCESS_CARD_SITES.siteId, site.getId())
                .execute();
    }

    /**
     * Shortcut to just get card xids
     * @param siteId
     * @return
     */
    public List<String> getCardXidsForSite(int siteId) {
        return this.create.select(table.xid).from(table)
                .join(ExampleAccessCardSites.EXAMPLE_ACCESS_CARD_SITES)
                .on(ExampleAccessCards.EXAMPLE_ACCESS_CARDS.id.eq(ExampleAccessCardSites.EXAMPLE_ACCESS_CARD_SITES.accessCardId))
                .where(ExampleAccessCardSites.EXAMPLE_ACCESS_CARD_SITES.siteId.eq(siteId))
                .fetch().getValues(table.xid);
    }

    /**
     * Ensure no mapping exists
     * @param site
     * @param card
     * @return
     */
    public boolean siteMappingExists(ExampleSiteVO site, ExampleAccessCardVO card) {
        return this.create.selectCount().from(ExampleAccessCardSites.EXAMPLE_ACCESS_CARD_SITES)
                .where(ExampleAccessCardSites.EXAMPLE_ACCESS_CARD_SITES.siteId.eq(site.getId()), ExampleAccessCardSites.EXAMPLE_ACCESS_CARD_SITES.accessCardId.eq(card.getId()))
                .fetchSingle().value1() > 0;
    }
}
