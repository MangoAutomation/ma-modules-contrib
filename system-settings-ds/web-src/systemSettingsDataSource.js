/**
 * @copyright 2021 {@link http://radixiot.com|Radix IoT Inc.} All rights reserved.
 * @author Terry Packer
 */

import angular from 'angular';
import systemSettingsDataSourceEditor from './components/systemSettingsDataSourceEditor/systemSettingsDataSourceEditor';
import systemSettingsDataPointEditor from './components/systemSettingsDataPointEditor/systemSettingsDataPointEditor';
import dsHelpTemplate from './help/dsHelp.html';
import dpHelpTemplate from './help/dpHelp.html';

const systemSettingsDataSourceModule = angular.module('maSystemSettingsDataSourceModule', ['maUiApp'])
.component('maSystemSettingsDataSourceEditor', systemSettingsDataSourceEditor)
.component('maSystemSettingsDataPointEditor', systemSettingsDataPointEditor)
.config(['maDataSourceProvider', 'maPointProvider', 'maUiMenuProvider', function(maDataSourceProvider, maPointProvider, maUiMenuProvider) {
    maDataSourceProvider.registerType({
        type: 'EXAMPLE_SYSTEM_SETTINGS',
        description: 'systemSettings.datasource.description',
        template: `<ma-system-settings-data-source-editor data-source="$ctrl.dataSource"></ma-system-settings-data-source-editor>`,
        polling: true,
        defaultDataSource: {
            name: '',
            enabled: false,
            descriptionKey: 'systemSettings.datasource.description',
            pollPeriod: {
                periods: 1,
                type: 'MINUTES'
            },
            editPermission: ['superadmin'],
            purgeSettings: {
                override: false,
                frequency: {
                    periods: 1,
                    type: 'YEARS'
                }
            },
            eventAlarmLevels: [
                {eventType: 'POLL_ABORTED', level: 'URGENT', duplicateHandling: 'IGNORE', descriptionKey: 'event.ds.pollAborted'}
            ],
            quantize: false,
            useCron: false,
            url: 'http://localhost:8080/rest/latest/system-settings',
            token: '',
            timeoutMs: 1000,
            retries: 2,
            modelType: 'EXAMPLE_SYSTEM_SETTINGS'
        },
        defaultDataPoint: {
            dataSourceTypeName: 'EXAMPLE_SYSTEM_SETTINGS',
            pointLocator: {
                settable: false,
                settingKey: '',
                settingType: 'INTEGER',
                modelType: 'PL.EXAMPLE_SYSTEM_SETTINGS'
            }
        },
        bulkEditorColumns: [
            
        ]
    });

    maPointProvider.registerType({
        type: 'EXAMPLE_SYSTEM_SETTINGS',
        description: 'systemSettings.datapoint.description',
        template: `<ma-system-settings-data-point-editor data-point="$ctrl.dataPoint"></ma-system-settings-data-point-editor>`
    });
    maUiMenuProvider.registerMenuItems([
        {
            name: 'ui.help.systemSettingsDataSource',
            url: '/system-settings-data-source',
            menuTr: 'systemSettings.datasource.description',
            template: dsHelpTemplate
        },
        {
            name: 'ui.help.systemSettingsDataPoint',
            url: '/system-settings-data-point',
            menuTr: 'systemSettings.datapoint.description',
            template: dpHelpTemplate
        }
    ]);
}]);

export default systemSettingsDataSourceModule;
