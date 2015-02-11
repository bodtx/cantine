
var badgeuseControllers  = angular.module('badgeuseControllers', []);

//calcule badgeuse
badgeuseControllers.controller('HeureCtrl', function($scope, badgeInfoService) {



    $scope.loadBadgeuse = function () {
        $scope.loadingBadgeuse = "bouttonRefresh glyphicon glyphicon-refresh glyphicon-refresh-animate";
        badgeInfoService.getBadgeInfo().then(function(badgeInfo) {

            //nb clic
            if(badgeInfo.nbTentativeConnexion>0){
                $scope.nbTentativeConnexionVisible = true;
            }else{
                $scope.nbTentativeConnexionVisible = false;
            }
            $scope.nbTentativeConnexion ="Tu as économisé " + badgeInfo.nbTentativeConnexion + " clics en utilisant ce magnifique site!!!" ;


            // presence Aujourd'hui
            $scope.presenceAujourdhui ="Aujourd'hui tu as fait : " + badgeInfo.presenceAujourdhui;


            // pause midi
            if(badgeInfo.pauseMidi!=""){
                $scope.pauseMidiVisible = true;
            } else {
                $scope.pauseMidiVisible = false;
            }
            $scope.pauseMidi ="Temps de pause repas (30m minimum) : " + badgeInfo.pauseMidi;


            //RAF
            if(badgeInfo.resteAfaireAujourdhui!=""){
                $scope.resteAFaireAujourdhuiVisible =true;
            }else{
                $scope.resteAFaireAujourdhuiVisible =false;
            }
            $scope.resteAFaireAujourdhui = "Il te faut encore faire : " + badgeInfo.resteAfaireAujourdhui;


            //Tps récupéré Aujourd'hui
            if(badgeInfo.tpsRecupereAujourdhui!=""){
                $scope.tpsRecupereAujourdhuiVisible =true;
            }else{
                $scope.tpsRecupereAujourdhuiVisible =false;
            }
            $scope.tpsRecupereAujourdhui = "Aujourd'hui tu as récupéré : " + badgeInfo.tpsRecupereAujourdhui;


            // Débit  Crédit
            $scope.tpsTotalCummuleAujourdhui = "Débit / Crédit : " + badgeInfo.tpsTotalCummuleAujourdhui;


            //Simulation départ
            $scope.simulationDepart = "Si tu pars maintenant, ton compteur sera à : " + badgeInfo.simulationDepart;


            //Entrée Sortie
            $scope.mouvements = badgeInfo.entreeSortieList;

            $scope.loadingBadgeuse = "bouttonRefresh glyphicon glyphicon-refresh";


        });

	};

	//initial load
    $scope.loadBadgeuse();

    $scope.openTemptation = function(nom) {
        badgeInfoService.openTemptation();
    }

    // refresh auto quand on revient sur la page
    window.onfocus = function() {
        $scope.loadBadgeuse();
    };

});