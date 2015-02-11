
var accueilControllers = angular.module('accueilControllers',['ui.bootstrap']);



//accueil Hello
accueilControllers.controller('AccueilCtrl', ['$scope','$route', 'userService', 'badgeInfoService', 'menuService', 'psNextService', 'transportService' , '$rootScope', function ($scope, $route, userService, badgeInfoService, menuService, psNextService, transportService, $rootScope) {

        //problème Tisseo
        $scope.tisseoClos = getAlerteCookie('tisseoClos');

        transportService.problemeTisseo().then(function(problemeTisseo) {
            $scope.problemeTisseo = problemeTisseo ;
        });


        //as tu badgé?
        $scope.asTubadgeClos = getAlerteCookie('asTubadgeClos');
        badgeInfoService.asTuBadge().then(function(asTuBadge) {
            $scope.badgeKo = !asTuBadge  ;
        });


        //café gratos
        $scope.cafeClos = getAlerteCookie('cafeClos');

        $scope.cafeGratos = false;
        if($rootScope.setting.cafe){
            var today=new Date();
            //decalage d'un jour entre java et javascript
            if(today.getDay()==$rootScope.setting.cafeJour -1){
                $scope.cafeGratos = true;
            }
        }

        //psNext
        $scope.psNextClos = getAlerteCookie('psNextClos');
        psNextService.getPsNext().then(function(psNext) {
            $scope.psNext = psNext  ;
        });

//        $scope.openPsNext = function() {
//            psNextService.openPsNext();
//        }

//        $scope.openTemptation = function() {
//            badgeInfoService.openTemptation();
//        }


        //inscription cantine
        $scope.inscritCantineClos = getAlerteCookie('inscritCantineClos');

        var now = new Date();

        // passé 13h, on n'afiche plus les messages de la cantine
        var timeLimitMenu = new Date();
        timeLimitMenu.setHours(12);

        if (now.getTime() > timeLimitMenu.getTime()) {
            $scope.timeLimitMenu =true;
        } else{
            $scope.timeLimitMenu =false;
        }

        var inscritTemp= $.cookie('inscrit');
        if(inscritTemp!=null){
            $scope.inscrit = inscritTemp;
        }else{
            $scope.inscrit = false;
        }



        menuService.getMenuDuJour().then(function(menuDujour) {
            $scope.menuDuJour = menuDujour  ;
                    $scope.viande = '';

                    $scope.accompagnements = {
                       a1: false,
                       a2: false
                      };
                      // TODO à deplacer lors de la recuperation du menu
                      $scope.plat1 =encodeURIComponent($scope.menuDuJour[1]).replace(/'/g, "&#39;");
                      $scope.plat2 = encodeURIComponent($scope.menuDuJour[2]).replace(/'/g, "&#39;");
                      $scope.accompagnement1 =encodeURIComponent($scope.menuDuJour[7]).replace(/'/g, "&#39;");
                      $scope.accompagnement2 = encodeURIComponent($scope.menuDuJour[8]).replace(/'/g, "&#39;");
        });


        // TODO refactoriser avec la methode mail de cantine
        // methode appeler lorsqu'on appuie sur le bouton mail
        // 1. enregiste le choix du plat
        // 2. appelle de la méthode mail en cas de succes
        $scope.mail = function(nom) {

            //creation du cookie pour dire qu'on est inscrit, valable 12h
            var date = new Date();
            var heures = 12;
            date.setTime(date.getTime() + (heures * 60 * 60 * 1000));

            $.cookie('inscrit', "true", {
                    expires : date
                });

             //rechargement de la page
             $route.reload();

            var now = new Date();
            var choix = {
                nom : $rootScope.user.cer,
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

        //creation du cookie pour dire qu'on a fermé l'alerte
        $scope.closeAlert = function(a) {
            var date = new Date();
            var heures = 12;
            date.setTime(date.getTime() + (heures * 60 * 60 * 1000));

            $.cookie(a, "true", {
                    expires : date
                });
        }

        // recuperation de l'eventuel cookie d'une alerte
        function getAlerteCookie(alerteCookie) {
            var tmp= $.cookie(alerteCookie);
            if(tmp!=null){
                return tmp;
            }else{
                return false;
            }
        }

}]);

