//TODO factoriser ce service

//controleur Hello
myApp.controller('HelloCtrl', function ($scope, userNameService) {
	userNameService.getUserName().then(function(username) {
		$scope.yourName = username;
	});

});


//copain Service
myApp.factory('copainService', function($http){
    return {
        getCopains: function() {
            return $http.get('/copains').then(
                function(result) {
                    return result.data;
            });
        }
    }
});


// menu service
myApp.factory('menuService', function($http) {
	return {
		getMenu : function() {
			// return the promise directly.
			return $http.get('/menu').then(function(result) {
				// resolve the promise as the data
				return result.data;
			});
		}
	}
});

// conroller qui affiche le menu
myApp.controller('MenuCtrl', function($scope, menuService, copainService) {

    copainService.getCopains().then(function(copains) {
        $scope.personnes = copains;
    });

	$scope.getTotalPersonne = function() {
		var count = 0;
		angular.forEach($scope.personnes, function(personne) {
			count += personne.inscrit ? 1 : 0;
		});
		return count;

	};

	$scope.addPersonne = function() {
		$scope.personnes.push({
			nom : $scope.formPersonneNom,
			inscrit : true
		});
		$scope.formPersonneNom = '';
	};

    $scope.counter = new Array();
    $scope.change = function(nom, inscritOld) {
    //on veut le rajouter dans la liste
    if(inscritOld=='false'){
        //on le rajoute s'il n'est pas déjà dans la liste
        if($scope.counter.indexOf(nom)<0){
          $scope.counter.push(nom);
        }
     }
    //on veut le sortir de la liste
    if(inscritOld=='true'){
        //on l'enleve s'il est dans la liste
        if($scope.counter.indexOf(nom)>-1){
          $scope.counter.remove($scope.counter.indexOf(nom));
        }
    }
    };




	menuService.getMenu().then(
			function(menu) {
				$(".nom").val($.cookie('nom'));

				var platsMail = [];
				var semaine;
				var header;

				header = menu.plats[0];
				var lundi = menu.plats[0][1];
				var mardi = menu.plats[0][2];
				var mercredi = menu.plats[0][3];
				var jeudi = menu.plats[0][4];
				var vendredi = menu.plats[0][5];

				// on enlève la ligne des jours pour récupérer uniquement les
				// lignes des plats
				menu.plats.splice(0, 1);
				$scope.plats = menu.plats;
				$scope.semaine = menu.semaine;
				semaine = menu.semaine;

				$('.semaine').append(menu.semaine);

				$('.table').dataTable({
					"data" : menu.plats,
					paging : false,
					searching : false,
					ordering : false,
					bInfo : false,
					"columns" : [ {
						"title" : ""
					}, {
						"title" : lundi
					}, {
						"title" : mardi
					}, {
						"title" : mercredi
					}, {
						"title" : jeudi
					}, {
						"title" : vendredi
					} ]
				});

				var table = $('.table').DataTable();
				$('.table tbody').on(
						'click',
						'td',
						function() {
							//la première colonne ne peut pas etre selectionnée
							if (table.cell(this).index().column > 0) {
								verifChoix(this);
							}
						});

				// on vérifie que le choix du plats est cohérent
				function verifChoix(td){
					var ligne=table.cell(td).index().row;
					var colonne = table.cell(td).index().column;
					//permet de savoir si la cellule est selectionnée
					var dejaCoche = false;
					//si c'est la viande, on a qu'un choix possible
					if(ligne <6){
						//on deselectionne les autres viandes
						for (var int = 0; int < 6; int++) {
							if($(table.cell(int, colonne).node()).hasClass('cell_selected')){
								$(table.cell(int, colonne).node()).removeClass('cell_selected');
								//si c'est la cellule sélectionnée a déjà était choisie
								if(int==ligne){
								    dejaCoche=true;
								}
							}
						}
						//on selectionne la nouvelle si elle n'avait pas était choisit
						if(dejaCoche==false){
						    $(td).addClass('cell_selected');
						}

					}
					
					//si c'est l'accompagnement, aucun test
					else{
						if ($(td).hasClass('cell_selected')) {
							$(td).removeClass('cell_selected');
						} else {
							$(td).addClass('cell_selected');
						}
					}
				}
				
				// méthode qui prépare le mail
				function email(nom) {
					var sBody = "Bonjour, \n\nvoici mon choix :\n";
					var table = $('.table').DataTable();
					cells = table.cells(".cell_selected");

                    // on affiche que les jours ou on a choisis un repas
                    $.each(table.cells(".cell_selected").eq(0), function() {
                        platsMail[this.column - 1] = "\n" + header[this.column] + ":    ";
                    });

                    //on affecte les plats au bon jour
					$.each(table.cells(".cell_selected").eq(0), function() {
						platsMail[this.column - 1] += " "
								+ table.cell(this.row, this.column).data()
								+ " --- ";
					});

                    // on affiche les plats choisis par jour
                    platsMail.forEach(function(y)
                             {
                                // supression des " --- " en fin de ligne
                                sBody += y.substring(0,y.length-5);;
                             }
                             );
					sBody +=  "\n\nMerci"  + "\n" + nom;

                    //envoie du mail
					var sMailTo = "mailto:";
					sMailTo += escape("mg133@ansamble.fr") + "?subject="
							+ escape("choix: " + semaine) + "&body="
							+ escape(sBody);
					window.location.href = sMailTo;
				}

				// methode appeler lorsqu'on appuie sur le bouton mail
				// 1. enregiste le choix du plat
				// 2. appelle de la méthode mail en cas de succes
				$scope.setUserChoix = function(nom) {
					$.cookie('nom', $(".nom").val(), {
						expires : 365
					});
					var now = new Date();
					var choix = {
						nom : $(".nom").val(),
						date : now.getTime(),
						plats : []
					}
					j = 0;
					$.each($('.table').DataTable().cells(".cell_selected")
							.eq(0), function() {
						choix.plats[j++] = $('.table').DataTable().cell(
								this.row, this.column).data();
					});

					$.ajax({
						type : "POST",
						headers : {
							Accept : "application/json",
							"Content-Type" : "application/json"
						},
						url : "choix",
						data : JSON.stringify(choix),
						success : email(nom),
						dataType : "json"
					});
				}

			});

});
