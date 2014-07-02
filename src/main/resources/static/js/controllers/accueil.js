
var accueilControllers = angular.module('accueilControllers',['ui.bootstrap']);

//accueil Hello
accueilControllers.controller('AccueilCtrl', ['$scope', 'asTuBadgeService', 'menuDuJourService' , function ($scope, asTuBadgeService, menuDuJourService) {

	   	asTuBadgeService.asTuBadge().then(function(badgeInfo) {
       		$scope.asTuBadge = badgeInfo.astuBadge  ;
       	});

        menuDuJourService.getMenuDuJour().then(function(menuDujour) {
            $scope.menuDuJour = menuDujour  ;
        });

}]);

