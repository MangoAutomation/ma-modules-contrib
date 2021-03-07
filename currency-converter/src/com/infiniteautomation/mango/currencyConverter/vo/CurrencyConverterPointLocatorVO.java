/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.currencyConverter.vo;

import com.infiniteautomation.mango.currencyConverter.CurrencyConverterDataSourceDefinition;
import com.infiniteautomation.mango.currencyConverter.rt.CurrencyConverterPointLocatorRT;
import com.serotonin.json.spi.JsonProperty;
import com.serotonin.m2m2.DataTypes;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.rt.dataSource.PointLocatorRT;
import com.serotonin.m2m2.vo.dataSource.AbstractPointLocatorVO;
import com.serotonin.util.SerializationHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class that contains the configuration settings for the point
 *
 * @author Pier Puccini
 *
 */
public class CurrencyConverterPointLocatorVO extends AbstractPointLocatorVO<CurrencyConverterPointLocatorVO> {

    @JsonProperty
    private boolean settable;
    @JsonProperty
    private double initialValue;
    @JsonProperty
    private boolean showCurrencyUnit;
    @JsonProperty
    private String currencyUnit;
    @JsonProperty
    private String fromCurrencyId;
    @JsonProperty
    private String toCurrencyId;


    /*
     * Used internally by Mango to create runtime instances of this
     *  point based on this configuration
     */
    @Override
    public PointLocatorRT<CurrencyConverterPointLocatorVO> createRuntime() {
        return new CurrencyConverterPointLocatorRT(this);
    }

    /*
     * Used in the Mango UI to describe this point based on its configuration
     */
    @Override
    public TranslatableMessage getConfigurationDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * Describes what type of data this point will record.
     *   (Can be configurable or hard coded based on implementation)
     */
    @Override
    public int getDataTypeId() {
        return DataTypes.NUMERIC;
    }

    @Override
    public boolean isSettable() {
        return settable;
    }

    public void setSettable(boolean settable) {
        this.settable = settable;
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
        out.writeBoolean(showCurrencyUnit);
        SerializationHelper.writeSafeUTF(out, currencyUnit);
        SerializationHelper.writeSafeUTF(out, fromCurrencyId);
        SerializationHelper.writeSafeUTF(out, toCurrencyId);
    }

    private void readObject(ObjectInputStream in) throws IOException {
        int version = in.readInt();
        if(version == 1) {
            initialValue = in.readDouble();
            showCurrencyUnit = in.readBoolean();
            currencyUnit  = SerializationHelper.readSafeUTF(in);
            fromCurrencyId = SerializationHelper.readSafeUTF(in);
            toCurrencyId = SerializationHelper.readSafeUTF(in);
        }
    }

    @Override
    public String getDataSourceType() {
        return CurrencyConverterDataSourceDefinition.TYPE_NAME;
    }

}
