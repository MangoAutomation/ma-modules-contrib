/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAccessCardVO;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;
import com.infiniteautomation.mango.spring.dao.ExampleAccessCardDao;
import com.infiniteautomation.mango.util.exception.NotFoundException;
import com.infiniteautomation.mango.util.exception.ValidationException;
import com.serotonin.m2m2.i18n.ProcessResult;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

@Service
public class ExampleAccessCardService extends AbstractVOService<ExampleAccessCardVO, ExampleAccessCardDao> {

    private final ExampleSiteService siteService;

    @Autowired
    public ExampleAccessCardService(ExampleAccessCardDao dao, PermissionService permissionService,
                                    ExampleSiteService siteService) {
        super(dao, permissionService);
        this.siteService = siteService;
    }

    @Override
    public boolean hasEditPermission(PermissionHolder user, ExampleAccessCardVO vo) {
        return permissionService.hasPermission(user, vo.getEditPermission());
    }

    @Override
    public boolean hasReadPermission(PermissionHolder user, ExampleAccessCardVO vo) {
        return permissionService.hasPermission(user, vo.getReadPermission());
    }

    @Override
    public ProcessResult validate(ExampleAccessCardVO vo, PermissionHolder user) {
        ProcessResult result = super.validate(vo, user);
        permissionService.validatePermission(result, "readPermission", user, null, vo.getReadPermission());
        permissionService.validatePermission(result, "editPermission", user, null, vo.getEditPermission());

        return result;
    }

    @Override
    public ProcessResult validate(ExampleAccessCardVO existing, ExampleAccessCardVO vo, PermissionHolder user) {
        ProcessResult result = super.validate(existing, vo, user);

        //Additional checks for existing list
        permissionService.validatePermission(result, "readPermission", user, existing.getReadPermission(), vo.getReadPermission());
        permissionService.validatePermission(result, "editPermission", user, existing.getEditPermission(), vo.getEditPermission());

        return result;
    }


    /**
     * Add a card to a site
     * @param siteXid
     * @param accessCardXid
     * @throws NotFoundException
     * @throws ValidationException
     */
    public void addAccessCardToSite(String siteXid, String accessCardXid) throws NotFoundException, ValidationException {
        ExampleSiteVO site = siteService.get(siteXid);
        ExampleAccessCardVO card = get(accessCardXid);
        addAccessCardToSite(site, card);
    }
    /**
     * Add a card to a site
     * @param vo
     * @param card
     * @throws ValidationException
     */
    public void addAccessCardToSite(ExampleSiteVO vo, ExampleAccessCardVO card) throws ValidationException {
        ProcessResult result = new ProcessResult();
        if(dao.siteMappingExists(vo, card)) {
            result.addGenericMessage("example.accessCard.siteMappingExists", card.getXid(), vo.getXid());
            throw new ValidationException(result);
        }

        dao.insertCardMappingForSite(vo, card);
    }

    public List<ExampleAccessCardVO> getForSite(String siteXid) {
        ExampleSiteVO site = siteService.get(siteXid);
        return dao.getCardsForSite(site.getId());
    }
}
