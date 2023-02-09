/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.dailystockprice.rt;

import static com.infiniteautomation.mango.dailystockprice.vo.DailyStockPriceDataSourceVO.DATA_SOURCE_EXCEPTION_EVENT;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infiniteautomation.mango.dailystockprice.vo.DailyStockPriceDataSourceVO;
import com.infiniteautomation.mango.dailystockprice.vo.DailyStockPricePointLocatorVO;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.rt.dataImage.DataPointRT;
import com.serotonin.m2m2.rt.dataImage.PointValueTime;
import com.serotonin.m2m2.rt.dataImage.SetPointSource;
import com.serotonin.m2m2.rt.dataImage.types.DataValue;
import com.serotonin.m2m2.rt.dataImage.types.NumericValue;
import com.serotonin.m2m2.rt.dataSource.PollingDataSource;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

public class DailyStockPriceDataSourceRT extends PollingDataSource<DailyStockPriceDataSourceVO> {

    protected final Log logger = LogFactory.getLog(DailyStockPriceDataSourceRT.class);
    private PolledValues polledValues;


    public DailyStockPriceDataSourceRT(DailyStockPriceDataSourceVO vo) {
        super(vo);
    }


    @Override
    public void initialize() {
        logger.debug("Initializing " + this.getClass());
        super.initialize();
        polledValues = new PolledValues();
        polledValues.init(vo);
    }


    @Override
    public void forcePointRead(DataPointRT dpRT) {
        readPoint(dpRT, System.currentTimeMillis());
    }


    /*
     * The poll logic, before a poll all new data points are made 
     *  available to use.
     */
    @Override
    protected void doPoll(long scheduledPollTime) {
        polledValues.newPoll();

        for (DataPointRT dpRT : dataPoints) {
            readPoint(dpRT, scheduledPollTime);
        }
    }


    private void readPoint(DataPointRT dpRT, long pollTime) {
        DailyStockPricePointLocatorRT plRT = dpRT.getPointLocator();

        try {
            dpRT.updatePointValue(new PointValueTime(plRT.getValue(polledValues), pollTime));

        } catch (Exception e) {
            raiseEvent(DATA_SOURCE_EXCEPTION_EVENT, System.currentTimeMillis(), true,
                    new TranslatableMessage("literal", "Event Raised"));
        }
    }


    /*
     * When a point is set externally (via the UI) this method is called 
     *  to allow the data source to perform the set and handle the result/error
     */
    @Override
    public void setPointValueImpl(DataPointRT dprt, PointValueTime valueToSet, SetPointSource source) {
        dprt.setPointValue(valueToSet, source);
    }


    public static class PolledValues {
        private static final String SERVICE_URL_TEMPLATE = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s";
        private OkHttpClient client;
        private String apiKey;
        private Map<String, DataValue> cachedValues;
        private ObjectMapper objectMapper;


        public void init(DailyStockPriceDataSourceVO vo) {
            client = new OkHttpClient();
            apiKey = vo.getApiKey();
            cachedValues = new HashMap<>();
            objectMapper = new ObjectMapper();
        }

        public void newPoll() {
            cachedValues.clear();
        }

        public DataValue getValue(DailyStockPricePointLocatorVO vo) throws IOException {
            DataValue dataValue = cachedValues.get(vo.getStockSymbol());

            if (dataValue == null) {
                String serviceURL = String.format(SERVICE_URL_TEMPLATE, vo.getStockSymbol(), this.apiKey);
                Request request = new Request.Builder().url(serviceURL).get().build();
                Response response = client.newCall(request).execute();

                try (
                    ResponseBody rb = response.body()
                ) {
                    JsonNode jsonNode = objectMapper.readTree(rb.string());
                    double price = jsonNode.get("Global Quote").get("05. price").asDouble();
                    dataValue = new NumericValue(price);
                }
            }

            return dataValue;
        }
    }
}
