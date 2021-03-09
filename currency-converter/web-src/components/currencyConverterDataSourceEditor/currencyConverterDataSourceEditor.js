/**
 * @copyright 2019 {@link http://infiniteautomation.com|Infinite Automation Systems, Inc.} All rights reserved.
 * @author Pier Puccini
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