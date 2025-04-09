/**
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

import angular from 'angular';
import examplePollingDataSourceEditor from './components/examplePollingDataSourceEditor/examplePollingDataSourceEditor';
import exampleDataPointEditor from './components/exampleDataPointEditor/exampleDataPointEditor';
import dsHelpTemplate from './help/dsHelp.html';
import dpHelpTemplate from './help/dpHelp.html';

const examplePollingDataSourceModule = angular.module('maExamplePollingDataSourceModule', ['maUiApp'])
.component('maExamplePollingDataSourceEditor', examplePollingDataSourceEditor)
.component('maExampleDataPointEditor', exampleDataPointEditor)
.config(['maDataSourceProvider', 'maPointProvider', 'maUiMenuProvider', function(maDataSourceProvider, maPointProvider, maUiMenuProvider) {
    maDataSourceProvider.registerType({
        type: 'EXAMPLE_POLLING',
        description: 'example.datasource.polling.description',
        template: `<ma-example-polling-data-source-editor data-source="$ctrl.dataSource"></ma-example-polling-data-source-editor>`,
        polling: true,
        defaultDataSource: {
            name: '',
            enabled: false,
            descriptionKey: 'example.datasource.polling.description',
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
                {eventType: 'POLL_ABORTED', level: 'URGENT', duplicateHandling: 'IGNORE', descriptionKey: 'event.ds.pollAborted'},
                {eventType: 'DATA_SOURCE_EXCEPTION', level: 'URGENT', duplicateHandling: 'IGNORE', descriptionKey: 'event.ds.dataSource'}
            ],
            quantize: false,
            useCron: false,
            modelType: 'EXAMPLE_POLLING'
        },
        defaultDataPoint: {
            dataSourceTypeName: 'EXAMPLE_POLLING',
            pointLocator: {
                dataType: 'NUMERIC',
                modelType: 'PL.EXAMPLE_POLLING'
            }
        },
        bulkEditorColumns: [
            
        ]
    });

    maPointProvider.registerType({
        type: 'EXAMPLE_POLLING',
        description: 'example.datapoint.description',
        template: `<ma-example-data-point-editor data-point="$ctrl.dataPoint"></ma-example-data-point-editor>`
    });
    maUiMenuProvider.registerMenuItems([
        {
            name: 'ui.helps.help.examplePollingDataSource',
            url: '/example-polling-data-source',
            menuTr: 'example.datasource.polling.description',
            template: dsHelpTemplate
        },
        {
            name: 'ui.helps.help.exampleDataPoint',
            url: '/example-data-point',
            menuTr: 'example.datapoint.description',
            template: dpHelpTemplate
        }
    ]);
}]);

export default examplePollingDataSourceModule;
