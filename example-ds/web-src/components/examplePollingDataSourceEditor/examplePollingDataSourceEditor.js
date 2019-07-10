/**
 * @copyright 2019 {@link http://infiniteautomation.com|Infinite Automation Systems, Inc.} All rights reserved.
 * @author Terry Packer
 */

import examplePollingDataSourceEditor from './examplePollingDataSourceEditor.html';

class ExamplePollingDataSourceEditorController {
    static get $$ngIsClass() { return true; }
    static get $inject() { return []; }
    
    constructor() {
    }
    
    $onChanges(changes) {
    }
}

export default {
    template: examplePollingDataSourceEditor,
    controller: ExamplePollingDataSourceEditorController,
    bindings: {
        dataSource: '<source'
    }
};