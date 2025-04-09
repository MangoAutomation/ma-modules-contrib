/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

import angular from 'angular';
import twitterDataSourceEditor from './components/twitterDataSourceEditor/twitterDataSourceEditor';
import twitterDataPointEditor from './components/twitterDataPointEditor/twitterDataPointEditor';
import dsHelpTemplate from './help/dsHelp.html';
import dpHelpTemplate from './help/dpHelp.html';

// Services
import twitterFactory from './services/twitter';

const twitterModule = angular
    .module('maExamplePollingDataSourceModule', ['maUiApp'])
    .component('maTwitterDataSourceEditor', twitterDataSourceEditor)
    .component('maTwitterDataPointEditor', twitterDataPointEditor)
    .factory('maTwitterFactory', twitterFactory)
    .config([
        'maDataSourceProvider',
        'maPointProvider',
        'maUiMenuProvider',
        (maDataSourceProvider, maPointProvider, maUiMenuProvider) => {
            maDataSourceProvider.registerType({
                type: 'TWITTER_DS',
                description: 'twitter.datasource.description',
                template: `<ma-twitter-data-source-editor data-source="$ctrl.dataSource"></ma-twitter-data-source-editor>`,
                polling: true,
                defaultDataSource: {
                    name: '',
                    enabled: false,
                    descriptionKey: 'twitter.datasource.description',
                    pollPeriod: {
                        periods: 5,
                        type: 'SECONDS'
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
                        {
                            eventType: 'TWITTER_API_FAILURE',
                            level: 'URGENT',
                            duplicateHandling: 'IGNORE',
                            descriptionKey: 'twitter.events.apiFailureDescription'
                        }
                    ],
                    quantize: false,
                    useCron: false,
                    modelType: 'TWITTER_DS',
                    consumerKey: '',
                    consumerSecret: '',
                    token: '',
                    secret: ''
                },
                defaultDataPoint: {
                    dataSourceTypeName: 'TWITTER_DS',
                    pointLocator: {
                        dataType: 'ALPHANUMERIC',
                        modelType: 'PL.TWITTER',
                        tweetFilter: [],
                        settable: false
                    }
                },
                bulkEditorColumns: []
            });

            maPointProvider.registerType({
                type: 'TWITTER_DS',
                description: 'twitter.datapoint.description',
                template: `<ma-twitter-data-point-editor data-point="$ctrl.dataPoint"></ma-twitter-data-point-editor>`
            });
            maUiMenuProvider.registerMenuItems([
                {
                    name: 'ui.help.twitterDataSource',
                    url: '/twitter-data-source',
                    menuTr: 'twitter.datasource.description',
                    template: dsHelpTemplate
                },
                {
                    name: 'ui.help.twitterDataPoint',
                    url: '/twitter-data-point',
                    menuTr: 'twitter.datapoint.description',
                    template: dpHelpTemplate
                }
            ]);
        }
    ]);

export default twitterModule;
