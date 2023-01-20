/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.systemSettings.vo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.infiniteautomation.mango.systemSettings.SystemSettingsDataSourceDefinition;
import com.infiniteautomation.mango.systemSettings.rt.SystemSettingsPointLocatorRT;
import com.serotonin.json.JsonException;
import com.serotonin.json.JsonReader;
import com.serotonin.json.ObjectWriter;
import com.serotonin.json.spi.JsonProperty;
import com.serotonin.json.spi.JsonSerializable;
import com.serotonin.json.type.JsonObject;
import com.serotonin.m2m2.DataType;
import com.serotonin.m2m2.i18n.TranslatableJsonException;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.rt.dataSource.PointLocatorRT;
import com.serotonin.m2m2.util.ExportCodes;
import com.serotonin.m2m2.vo.dataSource.AbstractPointLocatorVO;
import com.serotonin.util.SerializationHelper;

/**
 * Class that contains the configuration settings for the point
 *
 * @author Terry Packer
 *
 */
public class SystemSettingsPointLocatorVO extends AbstractPointLocatorVO<SystemSettingsPointLocatorVO> implements JsonSerializable {

    /*
     * Store the event codes in a final static block to share across all of this type of VO
     */
    public static final ExportCodes SETTING_TYPE_CODES = new ExportCodes();
    public static final int INTEGER = 1;
    public static final int STRING = 2;
    public static final int BOOLEAN = 3;

    static {
        SETTING_TYPE_CODES.addElement(INTEGER, "INTEGER","systemSettings.dataPoint.settingType.integer");
        SETTING_TYPE_CODES.addElement(STRING, "STRING","systemSettings.dataPoint.settingType.string");
        SETTING_TYPE_CODES.addElement(BOOLEAN, "BOOLEAN","systemSettings.dataPoint.settingType.boolean");
    }

    @JsonProperty
    private boolean settable;
    @JsonProperty
    private String settingKey;
    private int settingType;

    /*
     * Used internally by Mango to create runtime instances of this
     *  point based on this configuration
     */
    @Override
    public PointLocatorRT<SystemSettingsPointLocatorVO> createRuntime() {
        return new SystemSettingsPointLocatorRT(this);
    }

    /*
     * Used in the Mango UI to describe this point based on its configuration
     */
    @Override
    public TranslatableMessage getConfigurationDescription() {
        return new TranslatableMessage("systemSettings.dataPoint.connectionDescription", settingKey);
    }

    /*
     * Describes what type of data this point will record.
     *   (Can be configurable or hard coded based on implementation)
     */
    @Override
    public DataType getDataType() {
        switch(settingType) {
            case INTEGER:
                return DataType.MULTISTATE;
            case BOOLEAN:
                return DataType.BINARY;
            case STRING:
            default:
                return DataType.ALPHANUMERIC;
        }
    }

    /*
     * Can this point's value be set.
     *  (Can be configurable or hard coded based on implementation)
     */
    @Override
    public boolean isSettable() {
        return settable;
    }

    public void setSettable(boolean settable) {
        this.settable = settable;
    }

    @Override
    public void jsonWrite(ObjectWriter writer) throws IOException, JsonException {
        writer.writeEntry("settingType", SETTING_TYPE_CODES.getCode(settingType));
    }

    @Override
    public void jsonRead(JsonReader reader, JsonObject jsonObject) throws JsonException {
        String text = jsonObject.getString("settingType");
        if (text != null) {
            settingType = SETTING_TYPE_CODES.getId(text);
            if (settingType == -1)
                throw new TranslatableJsonException("emport.error.invalid", "settingType", text,
                        SETTING_TYPE_CODES.getCodeList());
        }
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
        SerializationHelper.writeSafeUTF(out, settingKey);
        out.writeInt(settingType);
    }

    private void readObject(ObjectInputStream in) throws IOException {
        int version = in.readInt();
        settingKey = SerializationHelper.readSafeUTF(in);
        settingType = in.readInt();
    }

    @Override
    public String getDataSourceType() {
        return SystemSettingsDataSourceDefinition.TYPE_NAME;
    }

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public int getSettingType() {
        return settingType;
    }

    public void setSettingType(int settingType) {
        this.settingType = settingType;
    }
}
