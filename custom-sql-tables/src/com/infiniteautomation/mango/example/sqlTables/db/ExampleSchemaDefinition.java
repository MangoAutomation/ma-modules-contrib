/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables.db;

import java.util.List;

import org.jooq.Table;

import com.infiniteautomation.mango.example.sqlTables.db.tables.ExampleSites;
import com.serotonin.m2m2.module.DatabaseSchemaDefinition;

public class ExampleSchemaDefinition extends DatabaseSchemaDefinition {

    @Override
    public String getNewInstallationCheckTableName() {
        return ExampleSites.EXAMPLE_SITES.getName();
    }

    @Override
    public List<Table<?>> getTablesForConversion() {
        return DefaultSchema.DEFAULT_SCHEMA.getTables();
    }

    @Override
    public String getUpgradePackage() {
        return "com.infiniteautomation.mango.example.db.upgrade";
    }

    @Override
    public int getDatabaseSchemaVersion() {
        return 1;
    }
}
