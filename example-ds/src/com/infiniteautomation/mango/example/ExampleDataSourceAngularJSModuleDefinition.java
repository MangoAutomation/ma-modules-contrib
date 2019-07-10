/**
 * Copyright (C) 2019  Terry Packer. All rights reserved.
 */
package com.infiniteautomation.mango.example;

import com.serotonin.m2m2.module.AngularJSModuleDefinition;

/**
 * Define the angular module used on the data source/point configuration views
 * 
 * @author Terry Packer
 *
 */
public class ExampleDataSourceAngularJSModuleDefinition extends AngularJSModuleDefinition {
    @Override
    public String getJavaScriptFilename() {
        return "/angular/exampleDataSource.js";
    }
}
