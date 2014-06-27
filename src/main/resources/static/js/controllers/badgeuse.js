
var badgeuseControllers  = angular.module('badgeuseControllers', []);

//TODO utiliser les ng-show?
//calcule badgeuse
badgeuseControllers.controller('HeureCtrl', function($scope, badgeInfoService) {
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