/**
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

import exampleDataPointEditor from './exampleDataPointEditor.html';

class ExampleDataPointEditorController {
    static get $$ngIsClass() { return true; }
    static get $inject() { return ['maPoint']; }
    
    constructor(maPoint) {
        
    }
    
}

export default {
    template: exampleDataPointEditor,
    controller: ExampleDataPointEditorController,
    bindings: {
        dataPoint: '<point'
    },
    require: {
        pointEditor: '^maDataPointEditor'
    }
};    
