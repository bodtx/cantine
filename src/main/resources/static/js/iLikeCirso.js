var myApp = angular.module('ILikeCirso', ['ngRoute', 'accueilControllers', 'menuControllers', 'badgeuseControllers', 'ui.bootstrap','iLikeCirsoAnimations','transportControllers', 'settingControllers']);

myApp.directive('loading',   ['$http' ,function ($http)
    {
        return {
                restrict: 'A',
                replace:true,
                template: '<div><div class="loadingAround glyphicon-refresh-animate"><img src="img/logoLikeCirsoAround.png" align="center" width="35"/></div><div class="loadingCenter"><img src="img/logoLikeCirsoCenter.png" align="center" width="35"/></div></div>',
                link: function (scope, elm, attr) {
                      scope.isLoading = function () {
                          return $http.pendingRequests.length > 0;
                      };

                      scope.$watch(scope.isLoading, function (v)
                      {
                          if(v){
                              elm.show();
                          }else{
                              elm.hide();
                          }
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