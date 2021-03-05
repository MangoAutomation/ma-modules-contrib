/**
 * @copyright 2021 {@link http://radixiot.com|Radix IoT Inc.} All rights reserved.
 * @author Terry Packer
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
