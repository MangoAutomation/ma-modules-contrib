/**
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

import angular from 'angular';
import dailyStockPriceDataSourceEditor from './components/dailyStockPriceDataSourceEditor/dailyStockPriceDataSourceEditor';
import dailyStockPriceDataPointEditor from './components/dailyStockPriceDataPointEditor/dailyStockPriceDataPointEditor';
import dsHelpTemplate from './help/dsHelp.html';
import dpHelpTemplate from './help/dpHelp.html';

const dailyStockPriceModule = angular
    .module('maDailyStockPriceDataSourceModule', ['maUiApp'])
    .component('maDailyStockPriceDataSourceEditor', dailyStockPriceDataSourceEditor)
    .component('maDailyStockPriceDataPointEditor', dailyStockPriceDataPointEditor)
    .config([
        'maDataSourceProvider',
        'maPointProvider',
        'maUiMenuProvider',
        (maDataSourceProvider, maPointProvider, maUiMenuProvider) => {
            maDataSourceProvider.registerType({
                type: 'DAILY_STOCK_PRICE',
                description: 'dsp.datasource.description',
                template: `<ma-daily-stock-price-data-source-editor data-source="$ctrl.dataSource"></ma-daily-stock-price-data-source-editor>`,
                polling: true,
                defaultDataSource: {
                    name: '',
                    enabled: false,
                    descriptionKey: 'dsp.datasource.description',
                    pollPeriod: {
                        periods: 24,
                        type: 'HOURS'
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
                        { eventType: 'POLL_ABORTED', level: 'URGENT', duplicateHandling: 'IGNORE', descriptionKey: 'event.ds.pollAborted' },
                        { eventType: 'DATA_SOURCE_EXCEPTION', level: 'URGENT', duplicateHandling: 'IGNORE', descriptionKey: 'event.ds.dataSource'}
                    ],
                    quantize: false,
                    useCron: false,
                    apiKey: '',
                    modelType: 'DAILY_STOCK_PRICE'
                },
                defaultDataPoint: {
                    dataSourceTypeName: 'DAILY_STOCK_PRICE',
                    pointLocator: {
                        dataType: 'NUMERIC',
                        modelType: 'PL.DAILY_STOCK_PRICE',
                        settable: false,
                        stockSymbol: ''
                    }
                },
                bulkEditorColumns: []
            });

            maPointProvider.registerType({
                type: 'DAILY_STOCK_PRICE',
                description: 'dsp.datapoint.description',
                template: `<ma-daily-stock-price-data-point-editor data-point="$ctrl.dataPoint"></ma-daily-stock-price-data-point-editor>`
            });
            maUiMenuProvider.registerMenuItems([
                {
                    name: 'ui.help.dailyStockPriceDataSource',
                    url: '/daily-stock-price-data-source',
                    menuTr: 'dsp.datasource.description',
                    template: dsHelpTemplate
                },
                {
                    name: 'ui.help.dailyStockPriceDataPoint',
                    url: '/daily-stock-price-data-point',
                    menuTr: 'dsp.datapoint.description',
                    template: dpHelpTemplate
                }
            ]);
        }
    ]);

export default dailyStockPriceModule;
