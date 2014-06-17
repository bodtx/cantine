var myApp = angular.module('Badgeuse',[]);


//userName service
myApp.factory('userNameService', function($http) {
	   return {
		   getUserName: function() {
	             return $http.get('/userName').then(function(result) {
	                            return result.data;
	                        });
	        }
	   }
	});

//controleur Hello
myApp.controller('HelloCtrl', function ($scope, userNameService) {
	
	userNameService.getUserName().then(function(username) {
		$scope.yourName = username;
	});
	
});


////menu service
//myApp.factory('badgeuseService', function($http) {
//	   return {
//	        getHeure: function(h) {
//	             //return the promise directly.
//	             return $http.get('/heure?heure='+ h)
//	                       .then(function(result) {
//	                            //resolve the promise as the data
//	                            return result.data;
//	                        });
//	        }
//	   }
//	});

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


//TODO utiliser les ng-show?
//calcule badgeuse
myApp.controller('heureCtrl', function($scope, badgeInfoService) {
	badgeInfoService.getBadgeInfo().then(function(badgeInfo) {
		$scope.nbTentativeConnexion ="Tu as gagné " + badgeInfo.nbTentativeConnexion + " clics" ;
		$scope.presenceAujourdhui ="Aujourd'hui j'ai fait : " + badgeInfo.presenceAujourdhui;
		if(badgeInfo.resteAfaireAujourdhui!=""){
			$scope.resteAFaireAujourdhui = "Il faut encore faire : " + badgeInfo.resteAfaireAujourdhui;
		}else{
			$scope.resteAFaireAujourdhui ="";
		}
		if(badgeInfo.tpsRecupereAujourdhui!=""){
			$scope.tpsRecupereAujourdhui = "Aujourd'hui j'ai récupéré : " + badgeInfo.tpsRecupereAujourdhui;
		}else{
			$scope.tpsRecupereAujourdhui ="";
		}
		$scope.tpsTotalCummuleAujourdhui = "Tps total récupéré : " + badgeInfo.tpsTotalCummuleAujourdhui;
		$scope.simulationDepart = "Si vous partez maintenant, votre tps cummulé vaut : " + badgeInfo.simulationDepart;
		
	});

});