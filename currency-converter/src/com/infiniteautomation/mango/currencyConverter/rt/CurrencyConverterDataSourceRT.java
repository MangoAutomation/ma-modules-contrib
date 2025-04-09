/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.currencyConverter.rt;

import com.infiniteautomation.mango.currencyConverter.vo.CurrencyConverterDataSourceVO;
import com.posadskiy.currencyconverter.CurrencyConverter;
import com.posadskiy.currencyconverter.config.ConfigBuilder;
import com.serotonin.m2m2.rt.dataImage.DataPointRT;
import com.serotonin.m2m2.rt.dataImage.PointValueTime;
import com.serotonin.m2m2.rt.dataImage.SetPointSource;
import com.serotonin.m2m2.rt.dataSource.PollingDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Terry Packer
 *
 */
public class CurrencyConverterDataSourceRT extends PollingDataSource<CurrencyConverterDataSourceVO> {

    protected final Log LOG = LogFactory.getLog(CurrencyConverterDataSourceRT.class);

    //Events that can be generated
    public static final int POLL_ABORTED_EVENT = 1;
    
    public CurrencyConverterDataSourceRT(CurrencyConverterDataSourceVO vo) {
        super(vo);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    /*
     * The poll logic, before a poll all new data points are made 
     *  available to use.
     */
    @Override
    protected void doPoll(long scheduledPollTime) {
        // TODO Auto-generated method stub
        if (dataPoints.size() > 0) {
            String CURRENCY_CONVERTER_API_API_KEY = vo.getApiKey();
            CurrencyConverter converter = new CurrencyConverter(
                    new ConfigBuilder()
                            .currencyConverterApiApiKey(CURRENCY_CONVERTER_API_API_KEY)
                            .build()
            );

            for (DataPointRT dprt : dataPoints) {
                CurrencyConverterPointLocatorRT plrt = dprt.getPointLocator();

                dprt.updatePointValue(new PointValueTime(plrt.convertRate(converter), scheduledPollTime));
            }
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
    
}
