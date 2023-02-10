/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.dailystockprice.vo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.infiniteautomation.mango.dailystockprice.DailyStockPriceDataSourceDefinition;
import com.infiniteautomation.mango.dailystockprice.rt.DailyStockPricePointLocatorRT;
import com.serotonin.json.spi.JsonProperty;
import com.serotonin.m2m2.DataType;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.rt.dataSource.PointLocatorRT;
import com.serotonin.m2m2.vo.dataSource.AbstractPointLocatorVO;
import com.serotonin.util.SerializationHelper;

/**
 * Class that contains the configuration settings for the point
 */
public class DailyStockPricePointLocatorVO extends AbstractPointLocatorVO<DailyStockPricePointLocatorVO> {

    @JsonProperty
    private boolean settable = false;
    @JsonProperty
    private String stockSymbol = "";


    /*
     * Used internally by Mango to create runtime instances of this
     *  point based on this configuration
     */
    @Override
    public PointLocatorRT<DailyStockPricePointLocatorVO> createRuntime() {
        return new DailyStockPricePointLocatorRT(this);
    }

    /*
     * Used in the Mango UI to describe this point based on its configuration
     */
    @Override
    public TranslatableMessage getConfigurationDescription() {
        return null;
    }

    /*
     * Describes what type of data this point will record.
     *   (Can be configurable or hard coded based on implementation)
     */
    @Override
    public DataType getDataType() {
        return DataType.NUMERIC;
    }

    @Override
    public boolean isSettable() {return settable;}

    public void setSettable(boolean settable) {this.settable = settable;}

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    /*
     * Serialization is used to write the 'data' portion of the
     * database table.  It is very important to add a version even
     * if you only serialize that, it ensures that in the future
     * you can easily add/remove settings.
     */
    private static final long serialVersionUID = 1L;
    private static final int version = 1;
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(version);
        SerializationHelper.writeSafeUTF(out, stockSymbol);
    }

    private void readObject(ObjectInputStream in) throws IOException {
        int version = in.readInt();
        if(version == 1) {
            stockSymbol = SerializationHelper.readSafeUTF(in);
        }
    }

    @Override
    public String getDataSourceType() {
        return DailyStockPriceDataSourceDefinition.TYPE_NAME;
    }

}
