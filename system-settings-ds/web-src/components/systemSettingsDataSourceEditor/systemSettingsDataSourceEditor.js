/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

import systemSettingsDataSourceEditor from './systemSettingsDataSourceEditor.html';

class SystemSettingsDataSourceEditorController {
    static get $$ngIsClass() { return true; }
    static get $inject() { return []; }
    
    constructor() {
    }
    
    $onChanges(changes) {
    }
}

export default {
    template: systemSettingsDataSourceEditor,
    controller: SystemSettingsDataSourceEditorController,
    bindings: {
        dataSource: '<source'
    }
};
