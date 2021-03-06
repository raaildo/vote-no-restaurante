(function () {
    'use strict';
    angular
        .module('app.core')
        .factory('RankingService', RankingService);

    RankingService.$inject = ['$http','$log', '$q'];
    function RankingService($http, $log, $q) {

        return {
            getUserRanking: getUserRanking,
            getOverallRanking: getOverallRanking
        };

        function getUserRanking(userId) {
            return $http.get('/vote-no-restaurante/ranking/user/'+userId)
                .then(onComplete)
                .catch(onError);
        }

        function getOverallRanking() {
            return $http.get('/vote-no-restaurante/ranking/total')
                .then(onComplete)
                .catch(onError);
        }

        function onComplete(response) {
            return response.data;
        }

        function onError(error) {
            $log.error('XHR Failed for combinations.' + error.data);
            return $q.reject(error.data.description)
        }

    }
})();