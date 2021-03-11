/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables;

import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.infiniteautomation.mango.spring.service.EmportService;
import com.infiniteautomation.mango.spring.service.ExampleAccessCardService;
import com.infiniteautomation.mango.spring.service.ExampleAssetService;
import com.infiniteautomation.mango.spring.service.ExampleSiteService;
import com.infiniteautomation.mango.util.exception.ValidationException;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.MangoTestBase;
import com.serotonin.m2m2.i18n.ProcessMessage;

public abstract class GenerateDevelopmentConfiguration extends MangoTestBase {

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
    public void generate() {
        try {
            Map<String, Object> exportData = generateImpl();
            File outputDir = new File("resources-test/configs/");
            if(!outputDir.exists()) {
                outputDir.mkdirs();
            }
            String fileName = getFilename();
            File outputFile = new File(outputDir, fileName);
            System.out.println("Writing dev configuration to file " + outputFile.getAbsolutePath());

            try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                Common.getBean(EmportService.class).export(exportData, writer, 4);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
        }catch(ValidationException e) {
            e.printStackTrace();
            String failureMessage = "";
            for(ProcessMessage m : e.getValidationResult().getMessages()) {
                if(m.getContextKey() != null) {
                    String messagePart = m.getContextKey() + " -> " + m.getContextualMessage().translate(Common.getTranslations()) + "\n";
                    failureMessage += messagePart;
                }else {
                    failureMessage += m.getGenericMessage().translate(Common.getTranslations()) + "\n";
                }
            }
            fail(failureMessage);
        }catch(Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    /**
     * Generate the Export Data
     */
    protected abstract Map<String, Object> generateImpl();

    /**
     * The filename/location of the dev config to export
     * @return
     */
    protected abstract String getFilename();
}
