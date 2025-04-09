/*
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

twitterFactory.$inject = ['$q', '$injector', '$http', 'maRestResource'];
function twitterFactory($q, $injector, $http, RestResource) {
    class Twitter extends RestResource {
        static get defaultProperties() {
            return {};
        }

        static get baseUrl() {
            return '/rest/latest/twitter';
        }

        static get webSocketUrl() {
            return '/rest/latest/websocket/twitter';
        }

        static get xidPrefix() {
            return 'TW_';
        }

        getTweets(xid, tweetFilter, tweetCount) {
            const { baseUrl } = this.constructor;
            return $http({
                url: `${baseUrl}/tweets/${xid}`,
                method: 'GET',
                params: { tweetCount, tweetFilter }
            }).then((response) => response.data);
        }
    }

    return Twitter;
}

export default twitterFactory;
