/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
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

    getTweets() {

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
