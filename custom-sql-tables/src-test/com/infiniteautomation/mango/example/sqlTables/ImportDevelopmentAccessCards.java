/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.infiniteautomation.mango.emport.ImportTask;
import com.infiniteautomation.mango.spring.service.EmportService;
import com.infiniteautomation.mango.spring.service.ExampleAccessCardService;
import com.infiniteautomation.mango.spring.service.ExampleAssetService;
import com.infiniteautomation.mango.spring.service.ExampleSiteService;
import com.serotonin.json.JsonException;
import com.serotonin.json.type.JsonTypeReader;
import com.serotonin.json.type.JsonValue;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.MangoTestBase;
import com.serotonin.m2m2.i18n.ProcessMessage;

/**
 * Test to import the generated Sero JSON from the Generate test
 */
public class ImportDevelopmentAccessCards extends MangoTestBase {

    protected ExampleSiteService siteService;
    protected ExampleAssetService assetService;
    protected ExampleAccessCardService accessCardService;

    @BeforeClass
    public static void setup() {
        System.setProperty("mango.paths.home", "maven-target/");
        loadModules();
    }

    @Before
    @Override
    public void before() {
        super.before();
        this.siteService = Common.getBean(ExampleSiteService.class);
        this.assetService = Common.getBean(ExampleAssetService.class);
        this.accessCardService = Common.getBean(ExampleAccessCardService.class);
    }

    @Test
    public void importDevelopmentConfig() {
        try {
            InputStream jsonTemplate = this.getClass().getResourceAsStream("/configs/" + getFilename());
            InputStreamReader isReader = new InputStreamReader(jsonTemplate);
            JsonTypeReader typeReader = new JsonTypeReader(isReader);

            JsonValue root = typeReader.read();
            ImportTask task = Common.getBean(EmportService.class).getImportTask(root.toJsonObject(), null, false, Common.getTranslations());
            task.run(1);

            //Ensure we had no problems, we know if we import the superadmin user and the default and anonymous role
            // we will have had only 3 errors
            int errorCount = 0;
            StringBuilder errorMessages = new StringBuilder();
            for(ProcessMessage m : task.getResponse().getMessages()) {
                if(m.getLevel() == ProcessMessage.Level.error) {
                    errorCount++;
                    if(m.getContextKey() == null) {
                        errorMessages.append(m.getGenericMessage().translate(Common.getTranslations()));
                    }else {
                        errorMessages.append(m.getContextualMessage().translate(Common.getTranslations()));
                    }
                    errorMessages.append("\n");
                }
            }

            if(errorCount > 3) {
                fail(errorMessages.toString());
            }

        } catch (JsonException | IOException e) {
            fail(e.getMessage());
        }
    }

    protected String getFilename() {
        return "dev-cards.json";
    }

}
