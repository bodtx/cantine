var myApp = angular.module('ILikeCirso', ['ngRoute', 'accueilControllers', 'menuControllers', 'badgeuseControllers', 'ui.bootstrap','iLikeCirsoAnimations','transportControllers', 'settingControllers']);

myApp.directive('loading',   ['$http' ,function ($http)
    {
        return {
                restrict: 'AE',
                replace:true,
                template: '<div class="loading"><img src="../img/loader.gif" width="20" height="20" /></div>',
                link: function (scope, element, attr) {
                      scope.$watch('loading', function (val) {
                          if (val)
                              $(element).show();
                          else
                              $(element).hide();
                      });
                }
              }

    }]);

//Service de variables partagées
myApp.service('sharedProperties', function () {
    //contient le CER de l'uilisateur.
    var cer = '';

    return {
        getCer: function () {
            return cer;
        },
        setCer: function(value) {
            cer = value;
        }
    };
});



// Bonjour
myApp.controller('BjrCtrl', ['$scope', 'userService', '$location' ,'sharedProperties', function ($scope, userService, $location, sharedProperties) {

    userService.getCer().then(function(result) {
        sharedProperties.setCer(result);
    });

	userService.getUserName().then(function(username) {
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
        when('/setting', {
            templateUrl: 'setting.html',
            controller: 'SettingCtrl'
        }).
        otherwise({
        redirectTo: '/accueil'
        });
    }]);