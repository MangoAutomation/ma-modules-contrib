/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest.model;

import com.infiniteautomation.mango.dailystockprice.vo.DailyStockPricePointLocatorVO;
import com.infiniteautomation.mango.rest.latest.model.dataPoint.AbstractPointLocatorModel;

public class DailyStockPricePointLocatorModel extends AbstractPointLocatorModel<DailyStockPricePointLocatorVO> {

    public static final String TYPE_NAME = "PL.DAILY_STOCK_PRICE";
    private String stockSymbol;


    public DailyStockPricePointLocatorModel() {}

    public DailyStockPricePointLocatorModel(DailyStockPricePointLocatorVO data) {
        super(data);
    }

    @Override
    public String getModelType() {
        return TYPE_NAME;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    /**
     * This is where one would copy the VO values into the fields,
     * this particual vo has no extra fields that are not handled in
     * the superclass.
     */
    @Override
    public DailyStockPricePointLocatorVO toVO() {
        DailyStockPricePointLocatorVO vo = new DailyStockPricePointLocatorVO();
        vo.setSettable(settable);
        vo.setStockSymbol(stockSymbol);
        return vo;
    }

    /**
     * This is where one would copy the Model values into the VO,
     * this particual vo has no extra fields that are not handled in
     * the superclass.
     */
    @Override
    public void fromVO(DailyStockPricePointLocatorVO vo) {
        super.fromVO(vo);
        settable = vo.isSettable();
        stockSymbol = vo.getStockSymbol();
    }

}
