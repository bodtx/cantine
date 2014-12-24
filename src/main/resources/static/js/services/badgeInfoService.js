//badgeInfo service
myApp.factory('badgeInfoService', function($http) {

    var badgeInfoService ={};

    badgeInfoService.getBadgeInfo = function() {
         return $http.get('badgeInfo')
               .then(function(result) {
                    return result.data;
                });
    };

    badgeInfoService.asTuBadge = function() {
         return $http.get('asTuBadge')
                   .then(function(result) {
                        return result.data;
                    });
    };

    badgeInfoService.openTemptation = function() {
        return $http.get('/openTemptation');
    };

    return badgeInfoService;
});


