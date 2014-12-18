
var transportControllers  = angular.module('transportControllers', []);

transportControllers.controller('TransportCtrl', function($scope, prochainsPassagesService, velibService) {
	prochainsPassagesService.prochainsPassages().then(function(prochainsPassages) {

        $scope.stopCode = prochainsPassages.stopCode;

        $scope.stopName = prochainsPassages.stopName;

        $scope.departs = prochainsPassages.departs;

	});


    velibService.velib().then(function(velibs) {
        $scope.velibs = velibs;
    });

});