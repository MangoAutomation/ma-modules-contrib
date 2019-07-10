/**
 * @copyright 2019 {@link http://infiniteautomation.com|Infinite Automation Systems, Inc.} All rights reserved.
 * @author Terry Packer
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