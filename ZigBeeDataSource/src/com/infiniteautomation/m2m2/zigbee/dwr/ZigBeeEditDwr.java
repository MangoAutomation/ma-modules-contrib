/*
    Copyright (C) 2014 Infinite Automation Systems Inc. All rights reserved.
    @author Matthew Lohbihler
 */
package com.infiniteautomation.m2m2.zigbee.dwr;

import com.infiniteautomation.m2m2.zigbee.vo.ZigBeePointLocatorVO;
import com.serotonin.ShouldNeverHappenException;
import com.serotonin.m2m2.i18n.ProcessResult;
import com.serotonin.m2m2.vo.dataSource.BasicDataSourceVO;
import com.serotonin.m2m2.web.dwr.DataSourceEditDwr;
import com.serotonin.m2m2.web.dwr.util.DwrPermission;

public class ZigBeeEditDwr extends DataSourceEditDwr {
    
	@DwrPermission(user = true)
    public ProcessResult saveZigBeeDataSource(BasicDataSourceVO basic, int updatePeriods, int updatePeriodType, boolean quantize, String cronPattern) {
		throw new ShouldNeverHappenException("un-implemented");
    }

    @DwrPermission(user = true)
    public ProcessResult saveZigBeePointLocator(int id, String xid, String name, ZigBeePointLocatorVO locator) {
        return validatePoint(id, xid, name, locator, null);
    }
    
}
