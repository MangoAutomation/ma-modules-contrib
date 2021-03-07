/**
 * @copyright 2019 {@link http://infiniteautomation.com|Infinite Automation Systems, Inc.} All rights reserved.
 * @author Pier Puccini
 */

import angular from 'angular';
import currencyConverterDataSourceEditor from './components/currencyConverterDataSourceEditor/currencyConverterDataSourceEditor';
import currencyConverterDataPointEditor from './components/currencyConverterDataPointEditor/currencyConverterDataPointEditor';
import dsHelpTemplate from './help/dsHelp.html';
import dpHelpTemplate from './help/dpHelp.html';

const currencyConverterModule = angular
    .module('maExamplePollingDataSourceModule', ['maUiApp'])
    .component('maCurrencyConverterDataSourceEditor', currencyConverterDataSourceEditor)
    .component('maCurrencyConverterDataPointEditor', currencyConverterDataPointEditor)
    .config([
        'maDataSourceProvider',
        'maPointProvider',
        'maUiMenuProvider',
        (maDataSourceProvider, maPointProvider, maUiMenuProvider) => {
            maDataSourceProvider.registerType({
                type: 'CURRENCY_CONVERT',
                description: 'cc.datasource.description',
                template: `<ma-currency-converter-data-source-editor data-source="$ctrl.dataSource"></ma-currency-converter-data-source-editor>`,
                polling: true,
                defaultDataSource: {
                    name: '',
                    enabled: false,
                    descriptionKey: 'cc.datasource.description',
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
                        { eventType: 'POLL_ABORTED', level: 'URGENT', duplicateHandling: 'IGNORE', descriptionKey: 'event.ds.pollAborted' }
                    ],
                    quantize: false,
                    useCron: false,
                    apiKey: '',
                    modelType: 'CURRENCY_CONVERT'
                },
                defaultDataPoint: {
                    dataSourceTypeName: 'CURRENCY_CONVERT',
                    pointLocator: {
                        dataType: 'NUMERIC',
                        modelType: 'PL.CURRENCY_CONVERT',
                        settable: false,
                        initialValue: 1,
                        fromCurrencyId: 'USD',
                        toCurrencyId: ''
                    }
                },
                bulkEditorColumns: []
            });

            maPointProvider.registerType({
                type: 'CURRENCY_CONVERT',
                description: 'cc.datapoint.description',
                template: `<ma-currency-converter-data-point-editor data-point="$ctrl.dataPoint"></ma-currency-converter-data-point-editor>`
            });
            maUiMenuProvider.registerMenuItems([
                {
                    name: 'ui.help.currencyConverterDataSource',
                    url: '/currency-converter-data-source',
                    menuTr: 'cc.datasource.description',
                    template: dsHelpTemplate
                },
                {
                    name: 'ui.help.currencyConverterDataPoint',
                    url: '/currency-converter-data-point',
                    menuTr: 'cc.datapoint.description',
                    template: dpHelpTemplate
                }
            ]);
        }
    ]);

export default currencyConverterModule;
