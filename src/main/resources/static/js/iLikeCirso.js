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


myApp.run(function($rootScope, userService, settingService) {
    $rootScope.user = {};
    $rootScope.setting = {};

    userService.getUserName().then(function(result) {
        $rootScope.user.name = result;
    });

    userService.getCer().then(function(result) {
        $rootScope.user.cer = result;

        settingService.getSetting().then(function(setting) {
            $rootScope.setting = setting ;
        });
    });




});

// Bonjour
myApp.controller('BjrCtrl', ['$scope', '$location', function ($scope, $location ) {


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