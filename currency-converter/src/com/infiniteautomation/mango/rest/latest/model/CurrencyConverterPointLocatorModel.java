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

    public static final String TYPE_NAME = "PL.CURRENCY_CONVERT";

    private double initialValue;
    private boolean showCurrencyUnit;
    private String currencyUnit;
    private String fromCurrencyId;
    private String toCurrencyId;

    public CurrencyConverterPointLocatorModel() {

    }

    public CurrencyConverterPointLocatorModel(CurrencyConverterPointLocatorVO data) {
        super(data);
    }

    @Override
    public String getModelType() {
        return TYPE_NAME;
    }

    public double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(double initialValue) {
        this.initialValue = initialValue;
    }

    public boolean isShowCurrencyUnit() {
        return showCurrencyUnit;
    }

    public void setShowCurrencyUnit(boolean showCurrencyUnit) {
        this.showCurrencyUnit = showCurrencyUnit;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public String getFromCurrencyId() {
        return fromCurrencyId;
    }

    public void setFromCurrencyId(String fromCurrencyId) {
        this.fromCurrencyId = fromCurrencyId;
    }

    public String getToCurrencyId() {
        return toCurrencyId;
    }

    public void setToCurrencyId(String toCurrencyId) {
        this.toCurrencyId = toCurrencyId;
    }

    /**
     * This is where one would copy the VO values into the fields,
     * this particual vo has no extra fields that are not handled in
     * the superclass.
     */
    @Override
    public CurrencyConverterPointLocatorVO toVO() {
        CurrencyConverterPointLocatorVO vo = new CurrencyConverterPointLocatorVO();
        vo.setSettable(settable);
        vo.setInitialValue(initialValue);
        vo.setShowCurrencyUnit(showCurrencyUnit);
        vo.setCurrencyUnit(currencyUnit);
        vo.setFromCurrencyId(fromCurrencyId);
        vo.setToCurrencyId(toCurrencyId);
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
        initialValue = vo.getInitialValue();
        showCurrencyUnit = vo.isShowCurrencyUnit();
        currencyUnit = vo.getCurrencyUnit();
        fromCurrencyId = vo.getFromCurrencyId();
        toCurrencyId = vo.getToCurrencyId();
    }

}
