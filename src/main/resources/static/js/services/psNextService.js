// psNext service
myApp.factory('psNextService', function($http) {

    var psNextService={};

    psNextService.getPsNext = function() {
        return $http.get('/psNext').then(function(result) {
            csrf = result.headers("X-CSRF-TOKEN");
            return result.data;
        });
    };

    psNextService.openPsNext = function() {
        return $http.get('/openPs');
    };

    return psNextService;

});


