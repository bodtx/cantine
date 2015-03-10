
var transportControllers  = angular.module('transportControllers', []);

transportControllers.controller('TransportCtrl', function($scope, $rootScope,  transportService) {
	$scope.loadBus = function () {
        $scope.loadingBus = "bouttonRefresh glyphicon glyphicon-refresh glyphicon-refresh-animate";
        transportService.prochainsPassages().then(function(prochainsPassages) {

            $scope.stopCode = prochainsPassages.stopCode;

            $scope.stopName = prochainsPassages.stopName;

            $scope.departs = prochainsPassages.departs;

            $scope.loadingBus = "bouttonRefresh glyphicon glyphicon-refresh";

        });
	};


    $scope.loadVelib = function () {
        $scope.loadingVelouse = "bouttonRefresh glyphicon glyphicon-refresh glyphicon-refresh-animate";
        transportService.velib().then(function(velibs) {
            $scope.velibs = velibs;
            $scope.loadingVelouse = "bouttonRefresh glyphicon glyphicon-refresh";
        });
    };



    //trafic
    $scope.isCollapsed = false;
    var adresse=$rootScope.setting.adresse;
    var direction;
    var coordonneesCirso = new google.maps.LatLng(43.629289, 1.475916);
    var coordonneesToulouse = new google.maps.LatLng(43.604724, 1.444196);

    $scope.loadMap = function initialize() {

        var mapOptions = {
          center: coordonneesToulouse,
          zoom: 12
        };
        var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

        var trafficLayer = new google.maps.TrafficLayer();
        trafficLayer.setMap(map);

        direction = new google.maps.DirectionsRenderer({
        map   : map
        });


        //calcul du trafic
         origin      = coordonneesCirso;   //cirso
         destination = adresse;
         if(origin && destination){
             var request = {
                 origin      : origin,
                 destination : destination,
                 travelMode  : google.maps.DirectionsTravelMode.DRIVING // Mode de conduite
             }
             var directionsService = new google.maps.DirectionsService(); // Service de calcul d'itinéraire
             directionsService.route(request, function(response, status){ // Envoie de la requête pour calculer le parcours
                 if(status == google.maps.DirectionsStatus.OK){
                     direction.setDirections(response); // Trace l'itinéraire sur la carte et les différentes étapes du parcours
                 }
             });
         }

    };

    //initial load
    $scope.loadVelib();
    $scope.loadBus();
    $scope.loadMap();

    // refresh auto quand on revient sur la page
    window.onfocus = function() {
        $scope.loadVelib();
        $scope.loadBus();
        $scope.loadMap();
    };

});