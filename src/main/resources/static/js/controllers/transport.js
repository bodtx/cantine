
var transportControllers  = angular.module('transportControllers', []);

transportControllers.controller('TransportCtrl', function($scope, prochainsPassagesService, velibService) {
	$scope.loadBus = function () {
        $scope.loadingBus = "bouttonRefresh glyphicon glyphicon-refresh glyphicon-refresh-animate";
        prochainsPassagesService.prochainsPassages().then(function(prochainsPassages) {

            $scope.stopCode = prochainsPassages.stopCode;

            $scope.stopName = prochainsPassages.stopName;

            $scope.departs = prochainsPassages.departs;

            $scope.loadingBus = "bouttonRefresh glyphicon glyphicon-refresh";

        });
	};


    $scope.loadVelib = function () {
        $scope.loadingVelouse = "bouttonRefresh glyphicon glyphicon-refresh glyphicon-refresh-animate";
        velibService.velib().then(function(velibs) {
            $scope.velibs = velibs;
            $scope.loadingVelouse = "bouttonRefresh glyphicon glyphicon-refresh";
        });
    };

    //initial load
    $scope.loadVelib();
    $scope.loadBus();
});