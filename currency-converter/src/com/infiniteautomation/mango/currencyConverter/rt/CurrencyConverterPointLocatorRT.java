/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.currencyConverter.rt;

import com.infiniteautomation.mango.currencyConverter.vo.CurrencyConverterPointLocatorVO;
import com.serotonin.m2m2.rt.dataSource.PointLocatorRT;

/**
 * 
 * Runtime container for an example point
 * 
 * @author Pier Puccini
 *
 */
public class CurrencyConverterPointLocatorRT extends PointLocatorRT<CurrencyConverterPointLocatorVO> {

    public CurrencyConverterPointLocatorRT(CurrencyConverterPointLocatorVO vo) {
        super(vo);
    }

}
