/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables;

import java.util.HashMap;
import java.util.Map;

import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAccessCardVO;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;
import com.infiniteautomation.mango.permission.MangoPermission;
import com.infiniteautomation.mango.spring.service.RoleService;
import com.infiniteautomation.mango.util.ConfigurationExportData;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.db.dao.RoleDao;
import com.serotonin.m2m2.db.dao.UserDao;
import com.serotonin.m2m2.vo.User;
import com.serotonin.m2m2.vo.role.RoleVO;

public class GenerateDevelopmentAccessCards extends GenerateDevelopmentConfiguration {

    @Override
    protected Map<String, Object> generateImpl() {
        createData();

        Map<String, Object> data = new HashMap<>();
        data.put(ConfigurationExportData.USERS, Common.getBean(UserDao.class).getAll());
        data.put(ConfigurationExportData.ROLES, Common.getBean(RoleDao.class).getAll());
        data.put(ExampleSiteEmportDefinition.ELEMENT_ID, siteService.buildQuery().query());
        data.put(ExampleAccessCardEmportDefinition.ELEMENT_ID, accessCardService.buildQuery().query());
        return data;
    }

    public void createData() {
        //First create some roles
        RoleVO read = new RoleVO(Common.NEW_ID, "ACCESS_CARD_READ", "Read Access Cards");
        RoleVO edit = new RoleVO(Common.NEW_ID, "ACCESS_CARD_EDIT", "Edit Access Cards");

        Common.getBean(RoleService.class).insert(read);
        Common.getBean(RoleService.class).insert(edit);

        //Create some users
        User accessCardReader = createUser("Access Card Reader",
                "accessCardReader",
                "password",
                "accessCardReader@example.com",
                read.getRole()
                );
        User accessCardEditor = createUser("Access Card Editor",
                "accessCardEditor",
                "password",
                "accessCardEditor@example.com",
                edit.getRole()
        );

        //Assigned to the read user and they can "see" this card
        ExampleAccessCardVO card1 = new ExampleAccessCardVO();
        card1.setName("Card 1");
        card1.setUserId(accessCardReader.getId());
        card1.setReadPermission(MangoPermission.requireAllRoles(read.getRole()));
        card1.setEditPermission(MangoPermission.requireAllRoles(edit.getRole()));
        accessCardService.insert(card1);

        //Assigned to the read user but they cannot "see" this card.
        ExampleAccessCardVO card2 = new ExampleAccessCardVO();
        card2.setName("Card 2");
        card2.setUserId(accessCardReader.getId());
        card2.setEditPermission(MangoPermission.requireAllRoles(edit.getRole()));
        accessCardService.insert(card2);

        ExampleSiteVO site1 = new ExampleSiteVO();
        site1.setName("Example site 1");
        siteService.insert(site1);
        accessCardService.addAccessCardToSite(site1, card1);
        accessCardService.addAccessCardToSite(site1, card2);

        ExampleSiteVO site2 = new ExampleSiteVO();
        site2.setName("Example site 2");
        siteService.insert(site2);
        accessCardService.addAccessCardToSite(site2, card1);
    }

    @Override
    protected String getFilename() {
        return "dev-cards.json";
    }
}
