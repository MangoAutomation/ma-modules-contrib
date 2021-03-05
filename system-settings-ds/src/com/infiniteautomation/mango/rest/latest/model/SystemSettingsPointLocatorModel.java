/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.rest.latest.model;

import com.infiniteautomation.mango.systemSettings.vo.SystemSettingsPointLocatorVO;
import com.infiniteautomation.mango.rest.latest.model.dataPoint.AbstractPointLocatorModel;

/**
 * @author Terry Packer
 *
 */
public class SystemSettingsPointLocatorModel extends AbstractPointLocatorModel<SystemSettingsPointLocatorVO> {

    public static final String TYPE_NAME = "PL.EXAMPLE_SYSTEM_SETTINGS";

    private String settingKey;
    private String settingType;

    public SystemSettingsPointLocatorModel() {

    }

    public SystemSettingsPointLocatorModel(SystemSettingsPointLocatorVO data) {
        super(data);
    }

    @Override
    public String getModelType() {
        return TYPE_NAME;
    }

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingType() {
        return settingType;
    }

    public void setSettingType(String settingType) {
        this.settingType = settingType;
    }

    /**
     * This is where one would copy the VO values into the fields,
     * this particual vo has no extra fields that are not handled in
     * the superclass.
     */
    @Override
    public SystemSettingsPointLocatorVO toVO() {
        SystemSettingsPointLocatorVO vo = new SystemSettingsPointLocatorVO();
        vo.setSettable(settable);
        vo.setSettingKey(settingKey);
        vo.setSettingType(SystemSettingsPointLocatorVO.SETTING_TYPE_CODES.getId(settingType));
        return vo;
    }

    /**
     * This is where one would copy the Model values into the VO,
     * this particular vo has no extra fields that are not handled in
     * the superclass.
     */
    @Override
    public void fromVO(SystemSettingsPointLocatorVO vo) {
        super.fromVO(vo);
        settingKey = vo.getSettingKey();
        settingType = SystemSettingsPointLocatorVO.SETTING_TYPE_CODES.getCode(vo.getSettingType());
    }

}
