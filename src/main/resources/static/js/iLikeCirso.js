var myApp = angular.module('ILikeCirso', ['ngRoute', 'accueilControllers']);

myApp.config(['$routeProvider',
    function($routeProvider){
        $routeProvider.when('/accueil', {
            templateUrl: 'accueil.html',
            controller: 'accueilCtrl'
        }).
        otherwise({
        redirectTo: '/accueil'
        });
    }]);