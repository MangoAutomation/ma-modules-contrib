/**
 * @copyright 2021 {@link https://radixiot.com|Radix IoT, Inc.} All rights reserved.
 * @author Terry Packer
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
                menuText: 'Stallion map view',
                menuHidden: true,
                menuIcon: 'location_on',
                weight: 800
            }]);
    }
]);

export default customSqlTablesModule;
