//ajout des modules et des librairies
var myApp = angular.module('ILikeCirso', ['ngRoute', 'ui.bootstrap' , 'flow', 'angularSpinner', 'accueilModule', 'cantineModule', 'badgeuseModule', 'transportModule', 'settingModule', 'fayotModule', 'avatarModule']);



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
        when('/fayot', {
            templateUrl: 'fayot.html',
            controller: 'FayotCtrl'
        }).
        when('/avatar', {
            templateUrl: 'avatar.html',
            controller: 'AvatarCtrl'
        }).
        otherwise({
        redirectTo: '/accueil'
        });
    }],

    ['usSpinnerConfigProvider', function(usSpinnerConfigProvider) {
    		usSpinnerConfigProvider.setDefaults({
    			lines: 13,

    			// The number of lines to draw
    			length: 20,

    			// The length of each line
    			width: 10,

    			// The line thickness
    			radius: 30,

    			// The radius of the inner circle
    			corners: 1,

    			// Corner roundness (0..1)
    			rotate: 0,

    			// The rotation offset
    			direction: 1,

    			// 1: clockwise, -1: counterclockwise
    			color: '#000',

    			// #rgb or #rrggbb or array of colors
    			speed: 1,

    			// Rounds per second
    			trail: 60,

    			// Afterglow percentage
    			shadow: false,

    			// Whether to render a shadow
    			hwaccel: false,

    			// Whether to use hardware acceleration
    			className: 'spinner',

    			// The CSS class to assign to the spinner
    			zIndex: 2e9,

    			// The z-index (defaults to 2000000000)
    			top: '50%',

    			// Top position relative to parent
    			left: '50%'

    			// Left position relative to parent
    		});
    	}],

    	['$httpProvider', '$provide', function($httpProvider, $provide) {
        		$httpProvider.defaults.transformRequest.push(function(data, headersGetter) {
        			console.log('transformRequest data ', data);
        			console.log('transformRequest headersGetter ', headersGetter);
        			return data;
        		});
        		console.log('transformRequest ', $httpProvider.defaults.transformRequest);

        		$httpProvider.defaults.transformResponse.unshift(function(data, headersGetter) {
        			console.log('transformResponse data ', data);
        			console.log('transformResponse headersGetter ', headersGetter);
        			return data;
        		});
        		console.log('transformResponse ', $httpProvider.defaults.transformResponse);

        		$httpProvider.defaults.cache = true;

        		// register the interceptor as a service
        		$provide.factory('myHttpInterceptor', ['$injector', function($injector) {
        			var $log = $injector.get('$log');
        			var usSpinnerService = $injector.get('usSpinnerService');
        			var $q = $injector.get('$q');
        			var $timeout = $injector.get('$timeout');

        			return {
        				// optional method
        				request: function(config) {
        					// do something on success
        					console.log('running interceptor request ', config);
        					console.log('arguments ', arguments);
        					usSpinnerService.spin('spinner-1');
        					return $timeout(function() { return config; }, 2000);
        				},

        				// optional method
        				requestError: function(rejection) {
        					usSpinnerService.stop('spinner-1');
        					console.log('running interceptor requestError ', rejection);

        					// do something on error
        					if (canRecover(rejection)) {
        						return responseOrNewPromise;
        					}
        					return $q.reject(rejection);
        				},



        				// optional method
        				response: function(response) {
        					$log.debug('running interceptor response ', response);
        					usSpinnerService.stop('spinner-1');
        					if (response.data.content == 'Error') {
        						$log.error('content has error ');
        						return $q.reject(response);
        					}

        					// do something on success
        					return response;
        				},

        				// optional method
        				responseError: function(rejection) {
        					usSpinnerService.stop('spinner-1');
        					console.log('running interceptor responseError ', rejection);

        					// do something on error
        					//					if (canRecover(rejection)) {
        					//						return responseOrNewPromise
        					//					}
        					return $q.reject(rejection);
        				}
        			};
        		}]);

        		$httpProvider.interceptors.push('myHttpInterceptor');

        		console.log('interceptors', $httpProvider.interceptors);
        	}]

//    ['flowFactoryProvider', function (flowFactoryProvider) {
//      flowFactoryProvider.defaults = {
//        target: '#/avatar',
//        permanentErrors: [404, 500, 501],
//        chunkRetryInterval: 5000,
//        simultaneousUploads: 1,
//        singleFile: true
//      };
//      flowFactoryProvider.on('catchAll', function (event) {
//        console.log('catchAll', arguments);
//      });
//    }]


    );