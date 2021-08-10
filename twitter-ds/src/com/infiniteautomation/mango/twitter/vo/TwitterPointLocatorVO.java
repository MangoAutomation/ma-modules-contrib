/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.twitter.vo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.infiniteautomation.mango.twitter.TwitterDataSourceDefinition;
import com.infiniteautomation.mango.twitter.rt.TwitterPointLocatorRT;
import com.serotonin.json.JsonException;
import com.serotonin.json.JsonReader;
import com.serotonin.json.ObjectWriter;
import com.serotonin.json.spi.JsonProperty;
import com.serotonin.json.spi.JsonSerializable;
import com.serotonin.json.type.JsonObject;
import com.serotonin.m2m2.DataTypes;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.rt.dataSource.PointLocatorRT;
import com.serotonin.m2m2.vo.dataSource.AbstractPointLocatorVO;
import com.serotonin.util.SerializationHelper;


/**
 * Class that contains the configuration settings for the point
 *
 * @author Benjamin Perez
 *
 */
public class TwitterPointLocatorVO extends AbstractPointLocatorVO<TwitterPointLocatorVO>  implements
        JsonSerializable {

    @JsonProperty
    private boolean settable;

    //Tweet filter
    @JsonProperty
    private List<String>  tweetFilter;

    /*
     * Used internally by Mango to create runtime instances of this
     *  point based on this configuration
     */
    @Override
    public PointLocatorRT<TwitterPointLocatorVO> createRuntime() {
        return new TwitterPointLocatorRT(this);

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
    public int getDataTypeId() { return DataTypes.ALPHANUMERIC; }

    @Override
    public boolean isSettable() {
        return settable;
    }

    public void setSettable(boolean settable) {
        this.settable = settable;
    }

    public List<String> getTweetFilter() { return tweetFilter; }

    public void setTweetFilter(List<String> tweetFilter) { this.tweetFilter = tweetFilter; }

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
        out.writeBoolean(settable);
        SerializationHelper.writeObject(out, tweetFilter);
    }

    private void readObject(ObjectInputStream in) throws IOException {
        int version = in.readInt();
        if(version == 1) {
            settable = in.readBoolean();
            tweetFilter = SerializationHelper.readObject(in);
        }
    }

    @Override
    public String getDataSourceType() {
        return TwitterDataSourceDefinition.TYPE_NAME;
    }

    @Override
    public void jsonWrite(ObjectWriter writer) throws IOException, JsonException {
        writeDataType(writer);
    }

    @Override
    public void jsonRead(JsonReader reader, JsonObject jsonObject)
            throws JsonException {
    }
}
