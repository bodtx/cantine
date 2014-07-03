
var badgeuseControllers  = angular.module('badgeuseControllers', []);

//TODO utiliser les ng-show?
//calcule badgeuse
badgeuseControllers.controller('HeureCtrl', function($scope, badgeInfoService) {
	badgeInfoService.getBadgeInfo().then(function(badgeInfo) {
		$scope.nbTentativeConnexion ="Tu as économisé " + badgeInfo.nbTentativeConnexion + " clics en utilisant ce site" ;
		$scope.presenceAujourdhui ="Aujourd'hui tu as fait : " + badgeInfo.presenceAujourdhui;
		$scope.pauseMidi ="Temps de pause repas (30m minimum) : " + badgeInfo.pauseMidi;
		if(badgeInfo.resteAfaireAujourdhui!=""){
			$scope.resteAFaireAujourdhui = "Il te faut encore faire : " + badgeInfo.resteAfaireAujourdhui;
		}else{
			$scope.resteAFaireAujourdhui ="";
		}
		if(badgeInfo.tpsRecupereAujourdhui!=""){
			$scope.tpsRecupereAujourdhui = "Aujourd'hui tu as récupéré : " + badgeInfo.tpsRecupereAujourdhui;
		}else{
			$scope.tpsRecupereAujourdhui ="";
		}
		$scope.tpsTotalCummuleAujourdhui = "Temps total récupéré : " + badgeInfo.tpsTotalCummuleAujourdhui;
		$scope.simulationDepart = "Si tu pars maintenant, tu aura cumulé : " + badgeInfo.simulationDepart;
		
	});

});