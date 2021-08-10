/**
 * @copyright 2019 {@link http://infiniteautomation.com|Infinite Automation Systems, Inc.} All rights reserved.
 * @author Benjamin Perez
 */

import template from './twitterDataSourceEditor.html';

class TwitterDataSourceEditorController {
    static get $$ngIsClass() { return true; }
    static get $inject() { return []; }
    
    constructor() {
    }
    
    $onChanges(changes) {
    }
}

export default {
    template: template,
    controller: TwitterDataSourceEditorController,
    bindings: {
        dataSource: '<source'
    }
};
