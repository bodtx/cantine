// menu service
myApp.factory('menuService', function($http) {

    var menuService ={};

    menuService.getMenu = function() {
        return $http.get('/menu').then(function(result) {
            csrf = result.headers("X-CSRF-TOKEN");
            return result.data;
        });
    };

	menuService.getMenuDuJour = function() {
        return $http.get('/menuDuJour').then(function(result) {
            csrf = result.headers("X-CSRF-TOKEN");
            return result.data;
        });
    };

    return menuService;

});


