/**
 * Copyright (C) 2019  Pier Puccini. All rights reserved.
 */
package com.infiniteautomation.mango.dailystockprice;

import com.serotonin.m2m2.module.AngularJSModuleDefinition;

/**
 * Define the angular module used on the data source/point configuration views
 */
public class DailyStockPriceAngularJSModuleDefinition extends AngularJSModuleDefinition {
    @Override
    public String getJavaScriptFilename() {return "/angular/dailyStockPrice.js";}
}
