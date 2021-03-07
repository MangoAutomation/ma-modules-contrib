/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.currencyConverter.rt;

import com.infiniteautomation.mango.currencyConverter.vo.CurrencyConverterPointLocatorVO;
import com.posadskiy.currencyconverter.CurrencyConverter;
import com.serotonin.m2m2.rt.dataImage.types.DataValue;
import com.serotonin.m2m2.rt.dataImage.types.NumericValue;
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

    /**
     * Returns a converted currency based of currency symbols
     * @param converter
     * @return
     */
    public DataValue convertRate(CurrencyConverter converter) {
        String fromSymbol = vo.getFromCurrencyId();
        String toSymbol = vo.getToCurrencyId();
        Double initValue = vo.getInitialValue();
        Double rate = converter.rate(fromSymbol, toSymbol);

        if (initValue != 1) {
            rate = rate*initValue;
        }

        return new NumericValue(rate);
    }

}
