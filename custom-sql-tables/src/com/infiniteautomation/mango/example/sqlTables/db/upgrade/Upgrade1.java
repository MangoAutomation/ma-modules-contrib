/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.example.sqlTables.db.upgrade;

import java.util.HashMap;
import java.util.Map;

import com.serotonin.m2m2.db.DatabaseType;
import com.serotonin.m2m2.db.upgrade.DBUpgrade;

/**
 * Add exampleSensorsTable
 */
public class Upgrade1 extends DBUpgrade {


    @Override
    public void upgrade() throws Exception {
        // Run the script.
        Map<String, String[]> scripts = new HashMap<String, String[]>();
        scripts.put(DatabaseType.MYSQL.name(), mysqlScript);
        scripts.put(DatabaseType.H2.name(), h2Script);
        runScript(scripts);
    }

    @Override
    protected String getNewSchemaVersion() {
        return "2";
    }

    private final String[] mysqlScript = new String[]{
            "CREATE TABLE exampleAccessCards(",
            "id INT NOT NULL AUTO_INCREMENT,",
            "xid VARCHAR(100) NOT NULL,",
            "name VARCHAR(255) NOT NULL,",
            "readPermissionId       INT          NOT NULL,",
            "editPermissionId       INT          NOT NULL,",
            "userId                 INT          NOT NULL,",
            "data                   CLOB,",
            "PRIMARY KEY (id)",
            ")engine=InnoDB;",
            "ALTER TABLE exampleAccessCards ADD CONSTRAINT exampleAccessCardsUnq1 UNIQUE (xid);",
            "ALTER TABLE exampleAccessCards ADD CONSTRAINT exampleAccessCardsFk1 FOREIGN KEY (readPermissionId) REFERENCES permissions (id) ON DELETE RESTRICT;",
            "ALTER TABLE exampleAccessCards ADD CONSTRAINT exampleAccessCardsFk2 FOREIGN KEY (editPermissionId) REFERENCES permissions (id) ON DELETE RESTRICT;",
            "ALTER TABLE exampleAccessCards ADD CONSTRAINT exampleAccessCardsFk3 FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE;"
    };

    private final String[] h2Script = new String[]{
            "CREATE TABLE exampleAccessCards(",
            "id INT NOT NULL AUTO_INCREMENT,",
            "xid VARCHAR(100) NOT NULL,",
            "name VARCHAR(255) NOT NULL,",
            "readPermissionId       INT          NOT NULL,",
            "editPermissionId       INT          NOT NULL,",
            "userId                 INT  NOT NULL,",
            "data                   CLOB,",
            "PRIMARY KEY (id)",
            ");",
            "ALTER TABLE exampleAccessCards ADD CONSTRAINT exampleAccessCardsUnq1 UNIQUE (xid);",
            "ALTER TABLE exampleAccessCards ADD CONSTRAINT exampleAccessCardsFk1 FOREIGN KEY (readPermissionId) REFERENCES permissions (id) ON DELETE RESTRICT;",
            "ALTER TABLE exampleAccessCards ADD CONSTRAINT exampleAccessCardsFk2 FOREIGN KEY (editPermissionId) REFERENCES permissions (id) ON DELETE RESTRICT;",
            "ALTER TABLE exampleAccessCards ADD CONSTRAINT exampleAccessCardsFk3 FOREIGN KEY (userId) REFERENCES users (id);"
    };
}
