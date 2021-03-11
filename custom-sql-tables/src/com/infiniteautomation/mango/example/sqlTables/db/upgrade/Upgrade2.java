/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables.db.upgrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.serotonin.m2m2.db.DatabaseType;
import com.serotonin.m2m2.db.upgrade.DBUpgrade;

/**
 * Add exampleSensorsTable
 */
public class Upgrade2 extends DBUpgrade {


    @Override
    public void upgrade() throws Exception {
        // Run the script.
        Map<String, String[]> scripts = new HashMap<String, String[]>();

        scripts.put(DatabaseType.MYSQL.name(), getScript(DatabaseType.MYSQL));
        scripts.put(DatabaseType.H2.name(), getScript(DatabaseType.H2));
        runScript(scripts);
    }

    @Override
    protected String getNewSchemaVersion() {
        return "3";
    }

    private String[] getScript(DatabaseType dbType) {
        List<String> lines = new ArrayList<>();
        switch(dbType) {
            case MYSQL:
                Scanner mySqlScanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("com/infiniteautomation/mango/example/sqlTables/db/upgrade/upgradeMySQL2.sql"));
                while(mySqlScanner.hasNextLine()) {
                    lines.add(mySqlScanner.nextLine());
                }
                break;
            case H2:
                Scanner h2Scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("com/infiniteautomation/mango/example/sqlTables/db/upgrade/upgradeH22.sql"));
                while(h2Scanner.hasNextLine()) {
                    lines.add(h2Scanner.nextLine());
                }
                break;
        }
        return lines.toArray(new String[lines.size()]);
    }
}
