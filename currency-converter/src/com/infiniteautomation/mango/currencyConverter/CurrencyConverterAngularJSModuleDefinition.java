/**
 * Copyright (C) 2019  Pier Puccini. All rights reserved.
 */
package com.infiniteautomation.mango.currencyConverter;

import com.serotonin.m2m2.module.AngularJSModuleDefinition;

/**
 * Define the angular module used on the data source/point configuration views
 * 
 * @author Pier Puccini
 *
 */
public class CurrencyConverterAngularJSModuleDefinition extends AngularJSModuleDefinition {
    @Override
    public String getJavaScriptFilename() {
        return "/angular/currencyConverter.js";
    }
}
