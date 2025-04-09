/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest.model;

import com.infiniteautomation.mango.rest.latest.model.datasource.AbstractPollingDataSourceModel;
import com.infiniteautomation.mango.systemSettings.SystemSettingsDataSourceDefinition;
import com.infiniteautomation.mango.systemSettings.vo.SystemSettingsDataSourceVO;

/**
 * @author Terry Packer
 *
 */
public class SystemSettingsDataSourceModel extends AbstractPollingDataSourceModel<SystemSettingsDataSourceVO>{

    private String url;
    private String token;
    private int timeoutMs;
    private int retries;

    public SystemSettingsDataSourceModel() {
        super();
    }
    
    public SystemSettingsDataSourceModel(SystemSettingsDataSourceVO data) {
        fromVO(data);
    }

    @Override
    public String getModelType() {
        return SystemSettingsDataSourceDefinition.TYPE_NAME;
    }
    
    @Override
    public SystemSettingsDataSourceVO toVO() {
        SystemSettingsDataSourceVO vo = super.toVO();
        vo.setUrl(url);
        vo.setToken(token);
        vo.setTimeoutMs(timeoutMs);
        vo.setRetries(retries);
        return vo;
    }
    
    @Override
    public void fromVO(SystemSettingsDataSourceVO vo) {
        super.fromVO(vo);
        url = vo.getUrl();
        token = vo.getToken();
        timeoutMs = vo.getTimeoutMs();
        retries = vo.getRetries();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTimeoutMs() {
        return timeoutMs;
    }

    public void setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
