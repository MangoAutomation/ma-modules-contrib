/*
 * Copyright (C) 2021 RadixIot LLC. All rights reserved.
 */

package com.infiniteautomation.mango.twitter;

import static com.infiniteautomation.mango.spring.MangoRuntimeContextConfiguration.COMMON_OBJECT_MAPPER_NAME;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class TwitterClientTools {

    public static Client getTwitterClient(String consumerKey, String consumerSecret, String token,
            String secret, List<String> terms, BlockingQueue<String> msgQueue) {
        // Host to connect to
        Hosts streamHost = new HttpHosts(Constants.STREAM_HOST);

        //the endpoint
        StatusesFilterEndpoint statusesFilterEndpoint = new StatusesFilterEndpoint();

        //This list should be an input in the data point
        statusesFilterEndpoint.trackTerms(terms);

        //Authentication (basic auth or oauth)
        Authentication oAuth = new OAuth1(consumerKey, consumerSecret, token, secret);
        ClientBuilder builder =
                new ClientBuilder().name("Twitter stream listener").hosts(streamHost)
                        .authentication(oAuth).endpoint(statusesFilterEndpoint)
                        .processor(new StringDelimitedProcessor(msgQueue));

        return builder.build();
    }

    public static String getTweet(String msg,
            @Qualifier(COMMON_OBJECT_MAPPER_NAME) ObjectMapper mapper)
            throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        JsonNode root;
        root = mapper.readTree(msg);
        sb.append('@');
        sb.append(root.path("user").path("screen_name").asText());
        sb.append(':');
        sb.append(' ');
        if (root.get("truncated").asBoolean()) {
            sb.append(root.path("extended_tweet").path("full_text").asText());
        } else {
            sb.append(root.get("text").asText());
        }
        return sb.toString();
    }
}
