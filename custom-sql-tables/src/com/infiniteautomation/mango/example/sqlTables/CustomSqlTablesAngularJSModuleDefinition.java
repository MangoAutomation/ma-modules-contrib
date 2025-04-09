/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.example.sqlTables;

import com.serotonin.m2m2.module.AngularJSModuleDefinition;

/**
 * Define the angular module
 */
public class CustomSqlTablesAngularJSModuleDefinition extends AngularJSModuleDefinition {
    @Override
    public String getJavaScriptFilename() {return "/angular/customSqlTables.js";}
}
