//User service
myApp.factory('userService', function($http) {
    var userService ={};

    userService.getUserName = function() {
        return $http.get('/userName').then(function(result) {
            return result.data;
        });
    };

    userService.getCer = function() {
        return $http.get('/cer').then(function(result) {
            return result.data;
        });
    };

    return userService;
	});


