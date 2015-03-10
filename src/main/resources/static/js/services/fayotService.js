//Fayot service
myApp.factory('fayotService', function($http, $rootScope) {

    var fayotService ={};

    fayotService.getFayot = function() {
         return $http.get('getFayot', {params: { cer: $rootScope.user.cer }})
                   .then(function(result) {
                        return result.data;
                    });
    };

    fayotService.saveFayot = function(fayot) {
        var data = JSON.stringify(fayot);
        var  headers = {"headers" : { Accept : "application/json", "Content-Type" : "application/json", "X-CSRF-TOKEN" : csrf}};
        return $http.post('saveFayot', data, headers).then(function(result) {
            return result.data;
        });
    };

    fayotService.getFayots = function() {
    var  headers = {"headers" : { Accept : "application/json", "Content-Type" : "application/json"}};
         return $http.get('getFayots', headers).then(function(result) {
            return result.data;
        });
    };



    return fayotService;
	});


