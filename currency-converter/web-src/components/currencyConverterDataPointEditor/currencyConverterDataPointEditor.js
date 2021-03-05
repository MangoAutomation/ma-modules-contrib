/**
 * @copyright 2019 {@link http://infiniteautomation.com|Infinite Automation Systems, Inc.} All rights reserved.
 * @author Pier Puccini
 */

import template from './currencyConverterDataPointEditor.html';

class CurrencyConverterDataPointEditorController {
    static get $$ngIsClass() { return true; }
    static get $inject() { return ['maPoint']; }
    
    constructor(maPoint) {
        
    }
    
}

export default {
    template: template,
    controller: CurrencyConverterDataPointEditorController,
    bindings: {
        dataPoint: '<point'
    },
    require: {
        pointEditor: '^maDataPointEditor'
    }
};    