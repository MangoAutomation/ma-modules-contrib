/**
 * Copyright (C) 2019  Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango.example.vo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.infiniteautomation.mango.example.ExamplePollingDataSourceDefinition;
import com.infiniteautomation.mango.example.rt.ExamplePointLocatorRT;
import com.serotonin.m2m2.DataTypes;
import com.serotonin.m2m2.i18n.TranslatableMessage;
import com.serotonin.m2m2.rt.dataSource.PointLocatorRT;
import com.serotonin.m2m2.vo.dataSource.AbstractPointLocatorVO;

/**
 * Class that contains the configuration settings for the point
 *
 * @author Terry Packer
 *
 */
public class ExamplePointLocatorVO extends AbstractPointLocatorVO<ExamplePointLocatorVO> {


    /*
     * Used internally by Mango to create runtime instances of this
     *  point based on this configuration
     */
    @Override
    public PointLocatorRT<ExamplePointLocatorVO> createRuntime() {
        return new ExamplePointLocatorRT(this);
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

    /*
     * Can this point's value be set.
     *  (Can be configurable or hard coded based on implementation)
     */
    @Override
    public boolean isSettable() {
        return false;
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
    }

    private void readObject(ObjectInputStream in) throws IOException {
        in.readInt();
    }

    @Override
    public String getDataSourceType() {
        return ExamplePollingDataSourceDefinition.TYPE_NAME;
    }

}
