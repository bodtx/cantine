var myApp = angular.module('ILikeCirso', ['ngRoute', 'accueilControllers', 'menuControllers', 'menuDuJourControllers', 'badgeuseControllers','ui.bootstrap','iLikeCirsoAnimations']);



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
        when('/menuDuJour', {
                    templateUrl: 'menuDuJour.html',
                    controller: 'MenuDuJourCtrl'
                }).
        when('/badgeuse', {
                    templateUrl: 'badgeuse.html',
                    controller: 'HeureCtrl'
                }).
        otherwise({
        redirectTo: '/accueil'
        });
    }]);