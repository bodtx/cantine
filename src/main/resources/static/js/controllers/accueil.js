
var accueilControllers = angular.module('accueilControllers',['ui.bootstrap']);

//accueil Hello
accueilControllers.controller('AccueilCtrl', ['$scope', 'asTuBadgeService' , function ($scope, asTuBadgeService) {

	   	asTuBadgeService.asTuBadge().then(function(badgeInfo) {
       		$scope.asTuBadge = badgeInfo.astuBadge  ;
       	});


}]);

