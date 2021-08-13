/**
 * @copyright 2019 {@link http://infiniteautomation.com|Infinite Automation Systems, Inc.} All rights reserved.
 * @author Benjamin Perez
 */

import template from './twitterDataSourceEditor.html';

class TwitterDataSourceEditorController {
    static get $$ngIsClass() {
        return true;
    }

    static get $inject() {
        return ['maTwitterFactory'];
    }

    constructor(maTwitterFactory) {
        this.maTwitterFactory = maTwitterFactory;

        this.toolsTweetFilter = [];

        this.query = {
            limit: 25,
            page: 1,
            total: 0,
            promise: null
        };
    }

    $onChanges(changes) {}

    getTweets() {
        const service = new this.maTwitterFactory();
        this.query.promise = service.getTweets(this.dataSource.xid, this.toolsTweetFilter, this.numTweets).then((response) => {
            console.log(response);
            this.items = response;
            this.query.total = response.length;
        });
    }
}

export default {
    template,
    controller: TwitterDataSourceEditorController,
    bindings: {
        dataSource: '<source'
    },
    require: {
        dataSourceEditor: '^maDataSourceEditor'
    }
};
