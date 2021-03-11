/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.infiniteautomation.mango.emport.ImportContext;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAccessCardVO;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;
import com.infiniteautomation.mango.spring.dao.ExampleSiteDao;
import com.infiniteautomation.mango.spring.service.ExampleAccessCardService;
import com.infiniteautomation.mango.spring.service.ExampleSiteService;
import com.infiniteautomation.mango.util.exception.NotFoundException;
import com.infiniteautomation.mango.util.exception.ValidationException;
import com.serotonin.json.JsonException;
import com.serotonin.json.type.JsonArray;
import com.serotonin.json.type.JsonObject;
import com.serotonin.json.type.JsonValue;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.i18n.TranslatableJsonException;
import com.serotonin.m2m2.module.EmportDefinition;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

public class ExampleSiteEmportDefinition extends EmportDefinition {

    public static String ELEMENT_ID = "example-sites";

    @Autowired
    private ExampleSiteService service;
    @Autowired
    private ExampleSiteDao dao;
    @Autowired
    private ExampleAccessCardService accessCardService;

    @Override
    public String getElementId() {
        return ELEMENT_ID;
    }

    @Override
    public String getDescriptionKey() {
        return "example.header.sites";
    }

    @Override
    public Object getExportData() {
        return dao.getAll();
    }

    @Override
    public int getOrder() {
        return 2; //Lower values is higher precedence
    }

    @Override
    public void doImport(JsonValue json, ImportContext importContext, PermissionHolder holder)
            throws JsonException {

        JsonObject value = json.toJsonObject();
        String xid = value.getString("xid");
        ExampleSiteVO vo = null;
        if (StringUtils.isBlank(xid)) {
            xid = service.generateUniqueXid();
        }else {
            try {
                vo = service.get(xid);
            }catch(NotFoundException e) {

            }
        }

        if(vo == null) {
            vo = new ExampleSiteVO();
            vo.setXid(xid);
        }

        try {
            List<ExampleAccessCardVO> cards = new ArrayList<>();
            JsonArray accessCardXids = value.getJsonArray("accessCardXids");
            if(accessCardXids != null) {
                for(JsonValue cardXid : value.getJsonArray("accessCardXids")) {
                    try {
                        cards.add(accessCardService.get(cardXid.toString()));
                    }catch(NotFoundException e) {
                        throw new TranslatableJsonException("emport.example.site.missingCard", cardXid.toString());
                    }
                }
            }

            importContext.getReader().readInto(vo, value);
            boolean isnew = vo.getId() == Common.NEW_ID;
            if(isnew) {
                service.insert(vo);
            }else {
                service.update(vo.getId(), vo);
            }

            //Insert any mappings we have
            for(ExampleAccessCardVO card : cards) {
                accessCardService.addAccessCardToSite(vo, card);
            }

            importContext.addSuccessMessage(isnew, "example.emport.site.prefix", xid);
        }catch(ValidationException e) {
            importContext.copyValidationMessages(e.getValidationResult(), "example.emport.site.prefix", xid);
        }
        catch (TranslatableJsonException e) {
            importContext.getResult().addGenericMessage("example.emport.site.prefix", xid, e.getMsg());
        }
        catch (JsonException e) {
            importContext.getResult().addGenericMessage("example.emport.site.prefix", xid,
                    importContext.getJsonExceptionMessage(e));
        }
    }
}
