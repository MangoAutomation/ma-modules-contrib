/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.infiniteautomation.mango.emport.ImportContext;
import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAssetVO;
import com.infiniteautomation.mango.spring.dao.ExampleAssetDao;
import com.infiniteautomation.mango.spring.service.ExampleAssetService;
import com.infiniteautomation.mango.util.exception.NotFoundException;
import com.infiniteautomation.mango.util.exception.ValidationException;
import com.serotonin.json.JsonException;
import com.serotonin.json.type.JsonObject;
import com.serotonin.json.type.JsonValue;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.i18n.TranslatableJsonException;
import com.serotonin.m2m2.module.EmportDefinition;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

public class ExampleAssetEmportDefinition extends EmportDefinition {

    public static String ELEMENT_ID = "example-assets";

    @Autowired
    private ExampleAssetService service;
    @Autowired
    private ExampleAssetDao dao;

    @Override
    public String getElementId() {
        return ELEMENT_ID;
    }

    @Override
    public String getDescriptionKey() {
        return "example.header.assets";
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
        ExampleAssetVO vo = null;
        if (StringUtils.isBlank(xid)) {
            xid = service.generateUniqueXid();
        }else {
            try {
                vo = service.get(xid);
            }catch(NotFoundException e) {

            }
        }

        if(vo == null) {
            vo = new ExampleAssetVO();
            vo.setXid(xid);
        }

        try {
            importContext.getReader().readInto(vo, value);
            boolean isnew = vo.getId() == Common.NEW_ID;
            if(isnew) {
                service.insert(vo);
            }else {
                service.update(vo.getId(), vo);
            }
            importContext.addSuccessMessage(isnew, "example.emport.asset.prefix", xid);
        }catch(ValidationException e) {
            importContext.copyValidationMessages(e.getValidationResult(), "example.emport.asset.prefix", xid);
        }
        catch (TranslatableJsonException e) {
            importContext.getResult().addGenericMessage("example.emport.asset.prefix", xid, e.getMsg());
        }
        catch (JsonException e) {
            importContext.getResult().addGenericMessage("example.emport.asset.prefix", xid,
                    importContext.getJsonExceptionMessage(e));
        }
    }
}
