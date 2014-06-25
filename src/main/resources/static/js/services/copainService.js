//copain Service
myApp.factory('copainService', function($http){
    return {
        getCopains: function() {
            return $http.get('/copains').then(
                function(result) {
                    return result.data;
            });
        }
    }
});