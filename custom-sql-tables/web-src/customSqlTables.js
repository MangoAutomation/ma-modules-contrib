/**
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

import angular from 'angular';

const customSqlTablesModule = angular.module('customSqlTablesModule', [
    'maUiApp'
]);
customSqlTablesModule.config([
    'maUiMenuProvider',
    (maUiMenuProvider) => {
        maUiMenuProvider.registerMenuItems([
            {
                name: 'ui.customSqlTables',
                url: '/custom-sql-tables',
                menuText: 'Custom SQL tables',
                template: '<ma-sql-console></ma-sql-console>',
                menuHidden: false,
                menuIcon: 'table_view',
                permission: ['superadmin']
            }]);
    }
]);

export default customSqlTablesModule;
