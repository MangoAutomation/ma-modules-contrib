/*
 * Copyright (C) 2021 RadixIot LLC. All rights reserved.
 */
package com.infiniteautomation.mango.twitter;

import com.serotonin.m2m2.module.AngularJSModuleDefinition;

/**
 * Define the angular module used on the data source/point configuration views
 * 
 * @author Benjamin Perez
 *
 */
public class TwitterAngularJSModuleDefinition extends AngularJSModuleDefinition {
    @Override
    public String getJavaScriptFilename() {
        return "/angular/twitterDataSource.js";
    }
}
