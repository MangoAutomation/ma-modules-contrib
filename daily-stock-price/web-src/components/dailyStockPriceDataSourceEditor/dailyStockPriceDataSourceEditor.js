/**
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
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
