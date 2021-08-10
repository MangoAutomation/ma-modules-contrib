/**
 * @copyright 2019 {@link http://infiniteautomation.com|Infinite Automation Systems, Inc.} All rights reserved.
 * @author Benjamin Perez
 */

import template from './twitterDataPointEditor.html';


class TwitterDataPointEditorController {
    static get $$ngIsClass() {
        return true;
    }

    static get $inject() { return ['maPoint']; }

    constructor(maPoint) {
        this.dataTypes = maPoint.dataTypes.filter(t => t.key !== 'IMAGE');
    }

    $onInit() {

    }

}

export default {
    template,
    controller: TwitterDataPointEditorController,
    bindings: {
        dataPoint: '<point'
    },
    require: {
        pointEditor: '^maDataPointEditor'
    }
};
