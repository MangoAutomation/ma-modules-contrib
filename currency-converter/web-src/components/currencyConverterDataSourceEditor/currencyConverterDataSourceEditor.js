/**
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

import template from './currencyConverterDataSourceEditor.html';

class CurrencyConverterDataSourceEditorController {
    static get $$ngIsClass() { return true; }
    static get $inject() { return []; }
    
    constructor() {
    }
    
    $onChanges(changes) {
    }
}

export default {
    template: template,
    controller: CurrencyConverterDataSourceEditorController,
    bindings: {
        dataSource: '<source'
    }
};
