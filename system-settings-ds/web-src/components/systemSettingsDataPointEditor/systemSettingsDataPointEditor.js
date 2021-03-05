/**
 * @copyright 2019 {@link http://infiniteautomation.com|Infinite Automation Systems, Inc.} All rights reserved.
 * @author Terry Packer
 */

import systemSettingsDataPointEditor from './systemSettingsDataPointEditor.html';

class SystemSettingsDataPointEditorController {
    static get $$ngIsClass() { return true; }
    static get $inject() { return ['maPoint']; }
    
    constructor(maPoint) {
        
    }

    settingTypeChanged() {
        switch(this.dataPoint.pointLocator.settingType) {
            case 'INTEGER':
                this.dataPoint.dataType = 'MULTISTATE';
            break;
            case 'BOOLEAN':
                this.dataPoint.dataType = 'BINARY';
            break;
            case 'STRING':
                this.dataPoint.dataType = 'ALPHANUMERIC';
            break;
        }
    }
}

export default {
    template: systemSettingsDataPointEditor,
    controller: SystemSettingsDataPointEditorController,
    bindings: {
        dataPoint: '<point'
    },
    require: {
        pointEditor: '^maDataPointEditor'
    }
};    
