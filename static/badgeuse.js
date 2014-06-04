var myApp = angular.module('Badgeuse',[]);

//controleur Hello
myApp.controller('HelloCtrl',['$scope', function ($scope) {
    $scope.yourName = 'Aurelien';
}]);


//menu service
myApp.factory('badgeuseService', function($http) {
	   return {
	        getHeure: function(h) {
	             //return the promise directly.
	             return $http.get('/heure?heure='+ h)
	                       .then(function(result) {
	                            //resolve the promise as the data
	                            return result.data;
	                        });
	        }
	   }
	});

//calcule badgeuse
myApp.controller('heureCtrl', function($scope, badgeuseService) {
	badgeuseService.getHeure('6h50').then(function(heure) {
		$scope.heure = heure;
	});

});