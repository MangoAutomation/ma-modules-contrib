/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables;

import com.serotonin.m2m2.module.AuditEventTypeDefinition;

public class ExampleAccessCardAuditEventTypeDefinition extends AuditEventTypeDefinition {

    public static final String TYPE_NAME = "EXAMPLE_ACCESS_CARD";
    public static final String TYPE_KEY = "event.audit.exampleAccessCard";

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    @Override
    public String getDescriptionKey() {
        return TYPE_KEY;
    }
}
