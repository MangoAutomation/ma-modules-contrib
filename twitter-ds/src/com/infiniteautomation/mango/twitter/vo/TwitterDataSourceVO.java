/*
 * Copyright (C) 2021 RadixIot LLC. All rights reserved.
 */
package com.infiniteautomation.mango.twitter.vo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.infiniteautomation.mango.twitter.rt.TwitterDataSourceRT;
import com.serotonin.json.JsonException;
import com.serotonin.json.JsonReader;
import com.serotonin.json.ObjectWriter;
import com.serotonin.json.spi.JsonProperty;
import com.serotonin.json.type.JsonObject;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.util.ExportCodes;
import com.serotonin.m2m2.vo.dataSource.PollingDataSourceVO;
import com.serotonin.m2m2.vo.event.EventTypeVO;
import com.serotonin.util.SerializationHelper;

/**
 * This datasource connects
 *
 * @author Benjamin Perez
 *
 */
public class TwitterDataSourceVO extends PollingDataSourceVO {

    //Test value consumerKey: 8q3j1MsT5OIywxBu6syRuzBrx
    @JsonProperty
    private String consumerKey;

    //Test value consumerSecret: wHzA2tEeKB9kS0mqUMkbB8g1z4WdLDOk9oQM4ElEQoCS8G1cds
    @JsonProperty
    private String consumerSecret;

    //Test value token: 116854384-v2GI6lT0NjgCBxzDoVV1RLzlWt7ny6OLAN4dSr5y
    @JsonProperty
    private String token;

    //Test value secret:gLjf7gBvNOGKy2JnM3cVZ27cBYoSjNItmGZGlXjcUDGY1
    @JsonProperty
    private String secret;

    /*
     * Store the event codes in a final static block to share across all of this type of VO
     */
    private static final ExportCodes EVENT_CODES = new ExportCodes();
    static {
        EVENT_CODES.addElement(TwitterDataSourceRT.POLL_ABORTED_EVENT, POLL_ABORTED);
        EVENT_CODES.addElement(TwitterDataSourceRT.TWITTER_API_FAILURE_EVENT, "TWITTER_API_FAILURE");
    }

    /*
     * Add any event types here
     */
    @Override
    protected void addEventTypes(List<EventTypeVO> ets) {

        super.addEventTypes(ets);
        ets.add(createEventType(TwitterDataSourceRT.TWITTER_API_FAILURE_EVENT,
                new TranslatableMessage("twitter.events.apiFailureDescription")));
    }

    /*
     * All polling data sources can generate a 'Poll Aborted' event
     */
    @Override
    public int getPollAbortedExceptionEventId() {
        return TwitterDataSourceRT.POLL_ABORTED_EVENT;
    }

    /*
     * Used in the RuntimeManager to start an instance of the data source
     *  based on this configuration.
     */
    @Override
    public TwitterDataSourceRT createDataSourceRT() {
        return new TwitterDataSourceRT(this);
    }

    /*
     * Used internally when creating new points
     */
    @Override
    public TwitterPointLocatorVO createPointLocator() {
        return new TwitterPointLocatorVO();
    }

    /*
     * This will show up in the data source list of the UI to describe
     *  the data source based on its settings.
     */
    @Override
    public TranslatableMessage getConnectionDescription() {
        return new TranslatableMessage("twitter.datasource.description");
    }

    /*
     * Return all event codes for this data source,
     * this is used in the UI
     */
    @Override
    public ExportCodes getEventCodes() {
        return EVENT_CODES;
    }

    /*
     * Serialization is used to write the 'data' column of the
     * database table.  It is very important to add a version even
     * if you only serialize that, it ensures that in the future
     * you can easily add/remove settings.
     */
    private static final long serialVersionUID = 1L;
    private static final int version = 1;
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(version);
        // Order is important, meaning that you must read in the order to write
        SerializationHelper.writeSafeUTF(out, consumerKey);
        SerializationHelper.writeSafeUTF(out, consumerSecret);
        SerializationHelper.writeSafeUTF(out, token);
        SerializationHelper.writeSafeUTF(out, secret);

    }

    private void readObject(ObjectInputStream in) throws IOException {
        int version = in.readInt();
        if (version == 1){
            // In case version one is to be modified
            consumerKey = SerializationHelper.readSafeUTF(in);
            consumerSecret = SerializationHelper.readSafeUTF(in);
            token = SerializationHelper.readSafeUTF(in);
            secret = SerializationHelper.readSafeUTF(in);
        }
    }

    @Override
    public void jsonWrite(ObjectWriter writer) throws IOException, JsonException {
        super.jsonWrite(writer);
    }

    @Override
    public void jsonRead(JsonReader reader, JsonObject jsonObject) throws JsonException {
        super.jsonRead(reader, jsonObject);
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
