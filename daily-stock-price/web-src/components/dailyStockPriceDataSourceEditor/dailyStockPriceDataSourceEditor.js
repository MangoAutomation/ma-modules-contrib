/**
 * @copyright 2019 {@link http://infiniteautomation.com|Infinite Automation Systems, Inc.} All rights reserved.
 */

import dailyStockPriceDataSourceEditor from './dailyStockPriceDataSourceEditor.html';

class DailyStockPriceDataSourceEditorController {
    static get $$ngIsClass() { return true; }
    static get $inject() { return []; }
    
    constructor() {
    }
    
    $onChanges(changes) {
    }
}

export default {
    template: dailyStockPriceDataSourceEditor,
    controller: DailyStockPriceDataSourceEditorController,
    bindings: {
        dataSource: '<source'
    }
};
