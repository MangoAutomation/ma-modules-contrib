/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest.model;

import com.infiniteautomation.mango.currencyConverter.vo.CurrencyConverterPointLocatorVO;
import com.infiniteautomation.mango.rest.latest.model.dataPoint.AbstractPointLocatorModel;

/**
 * @author Pier Puccini
 *
 */
public class CurrencyConverterPointLocatorModel extends AbstractPointLocatorModel<CurrencyConverterPointLocatorVO> {

    public static final String TYPE_NAME = "PL.EXAMPLE_POLLING";

    public CurrencyConverterPointLocatorModel() {

    }

    public CurrencyConverterPointLocatorModel(CurrencyConverterPointLocatorVO data) {
        super(data);
    }

    @Override
    public String getModelType() {
        return TYPE_NAME;
    }

    /**
     * This is where one would copy the VO values into the fields,
     * this particual vo has no extra fields that are not handled in
     * the superclass.
     */
    @Override
    public CurrencyConverterPointLocatorVO toVO() {
        CurrencyConverterPointLocatorVO vo = new CurrencyConverterPointLocatorVO();
        return vo;
    }

    /**
     * This is where one would copy the Model values into the VO,
     * this particual vo has no extra fields that are not handled in
     * the superclass.
     */
    @Override
    public void fromVO(CurrencyConverterPointLocatorVO vo) {
        super.fromVO(vo);
    }

}
