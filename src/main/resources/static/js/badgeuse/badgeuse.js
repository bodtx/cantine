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

//badgeInfo service
myApp.factory('badgeInfoService', function($http) {
	   return {
	        getBadgeInfo: function() {
	             return $http.get('badgeInfo')
	                       .then(function(result) {
	                            //resolve the promise as the data
	                            return result.data;
	                        });
	        }
	   }
	});


//calcule badgeuse
myApp.controller('heureCtrl', function($scope, badgeInfoService) {
	badgeInfoService.getBadgeInfo().then(function(badgeInfo) {
		$scope.presenceAujourdhui = badgeInfo.presenceAujourdhui;
		$scope.resteAFaireAujourdhui = badgeInfo.resteAfaireAujourdhui;
		$scope.tpsRecupereAujourdhui = badgeInfo.tpsRecupereAujourdhui;
	});

});