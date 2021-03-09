/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables;

import com.serotonin.m2m2.module.AuditEventTypeDefinition;

public class ExampleSiteAuditEventTypeDefinition extends AuditEventTypeDefinition {

    public static final String TYPE_NAME = "EXAMPLE_SITE";
    public static final String TYPE_KEY = "event.audit.exampleSite";

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    @Override
    public String getDescriptionKey() {
        return TYPE_KEY;
    }
}
