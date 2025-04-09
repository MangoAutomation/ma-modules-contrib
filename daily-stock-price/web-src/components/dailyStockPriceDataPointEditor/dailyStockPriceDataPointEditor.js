/**
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

import dailyStockPriceDataPointEditor from './dailyStockPriceDataPointEditor.html';

class DailyStockPriceDataPointEditorController {
    static get $$ngIsClass() { return true; }
    static get $inject() { return ['maPoint']; }

    constructor(maPoint) { }
}

export default {
    template: dailyStockPriceDataPointEditor,
    controller: DailyStockPriceDataPointEditorController,
    bindings: {
        dataPoint: '<point'
    },
    require: {
        pointEditor: '^maDataPointEditor'
    }
};
