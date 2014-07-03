
var accueilControllers = angular.module('accueilControllers',['ui.bootstrap']);

//accueil Hello
accueilControllers.controller('AccueilCtrl', ['$scope', 'asTuBadgeService', 'menuDuJourService' , function ($scope, asTuBadgeService, menuDuJourService) {

	   	asTuBadgeService.asTuBadge().then(function(badgeInfo) {
       		$scope.asTuBadge = badgeInfo.astuBadge  ;
       	});

        menuDuJourService.getMenuDuJour().then(function(menuDujour) {
            $scope.menuDuJour = menuDujour  ;
                    $scope.viande = '';

                    $scope.accompagnements = {
                       a1: false,
                       a2: false
                      };
        });


        //        TODO refactoriser avec la methode mail de cantine
        // methode appeler lorsqu'on appuie sur le bouton mail
        // 1. enregiste le choix du plat
        // 2. appelle de la méthode mail en cas de succes
        $scope.mail = function(nom) {
            var now = new Date();
            var choix = {
                nom : 'Aurelien', //TODO
                date : now.getTime(),
                plats : []
            }
            var i = 0;
            choix.plats[i++]={"nom":$scope.viande,"accompagnement":false};
            if($scope.accompagnements.a1){
                choix.plats[i++]={"nom": $scope.menuDuJour[7],"accompagnement":true};
             }

             if($scope.accompagnements.a2){
                 choix.plats[i++]={"nom": $scope.menuDuJour[8],"accompagnement":true};
              }

             

            $.ajax({
                type : "POST",
                headers : {
                    Accept : "application/json",
                    "Content-Type" : "application/json",
                    "X-CSRF-TOKEN" : csrf
                },
                url : "menu",
                data : JSON.stringify(choix),
                success : email(nom),
                dataType : "json"
            });
        }


        function email(nom) {
            var sBody = "Bonjour, \n\nvoici mon choix :\n\n";

            sBody += $scope.viande;

             if($scope.accompagnements.a1){
                sBody += ' --- ' + $scope.menuDuJour[7];
             }

             if($scope.accompagnements.a2){
                 sBody += ' --- ' + $scope.menuDuJour[8];
              }


            sBody +=  "\n\nMerci"  + "\n";


            //envoie du mail
            var sMailTo = "mailto:";
            sMailTo += escape("mg133@ansamble.fr") + "?subject="
                    + escape("1 personne") + "&body="
                    + escape(sBody);
            window.location.href = sMailTo;
        }


}]);

