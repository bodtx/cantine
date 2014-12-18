var myApp = angular.module('ILikeCirso', ['ngRoute', 'accueilControllers', 'menuControllers', 'badgeuseControllers', 'ui.bootstrap','iLikeCirsoAnimations','transportControllers']);



// Bonjour
myApp.controller('BjrCtrl', ['$scope', 'userNameService', '$location' , function ($scope, userNameService, $location) {

	userNameService.getUserName().then(function(username) {
    		$scope.hello= username;
    	});

    //permet de savoir quel onglet est actif
    $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

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
        when('/transport', {
            templateUrl: 'transport.html',
            controller: 'TransportCtrl'
        }).
        otherwise({
        redirectTo: '/accueil'
        });
    }]);