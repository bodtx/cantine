
var transportControllers  = angular.module('transportControllers', []);

transportControllers.controller('TransportCtrl', function($scope, prochainsPassagesService) {
	prochainsPassagesService.prochainsPassages().then(function(prochainsPassages) {

        $scope.stopCode = prochainsPassages.stopCode;

        $scope.stopName = prochainsPassages.stopName;

        $scope.departs = prochainsPassages.departs;

	});

});