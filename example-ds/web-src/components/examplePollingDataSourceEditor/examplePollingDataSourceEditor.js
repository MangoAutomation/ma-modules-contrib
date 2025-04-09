/**
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
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
