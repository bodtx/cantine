var myApp = angular.module('ILikeCirso', ['ngRoute', 'accueilControllers', 'menuControllers', 'badgeuseControllers']);



// Bonjour
myApp.controller('BjrCtrl', ['$scope', 'userNameService' , function ($scope, userNameService) {

	userNameService.getUserName().then(function(username) {
    		$scope.hello= 'Bonjour ' + username;
    	});

}]);

//routage des urls
myApp.config(['$routeProvider',
    function($routeProvider){
        $routeProvider.
        when('/accueil', {
            templateUrl: 'accueil.html',
            controller: 'AccueilCtrl'
        }).
        when('/cantine', {
                    templateUrl: 'cantine.html',
                    controller: 'MenuCtrl'
                }).
        when('/badgeuse', {
                    templateUrl: 'badgeuse.html',
                    controller: 'HeureCtrl'
                }).
        otherwise({
        redirectTo: '/accueil'
        });
    }]);