var myApp = angular.module('Cantine',[]);

//controleur Hello
myApp.controller('HelloCtrl',['$scope', function ($scope) {
    $scope.yourName = 'Aurelien';
}]);


//Ajout d'autres personnes
myApp.controller('CopainCtrl', ['$scope', function ($scope) {
	
	$scope.personnes =[{nom:'DIJOUX', inscrit:true},
	                   {nom:'KRIER', inscrit:false},
	                   {nom:'PATBOC', inscrit:true}
	                   ];
	
    $scope.getTotalPersonne = function(){
    	   var count = 0;
    	    angular.forEach($scope.personnes, function(personne) {
    	      count += personne.inscrit ? 1 : 0;
    	    });
    	    return count;
    	
    };
    
    $scope.addPersonne = function() {
		$scope.personnes.push({nom : $scope.formPersonneNom, inscrit:true});
		$scope.formPersonneNom ='';
	};
    
}]);


//menu service
myApp.factory('menuService', function($http) {
	   return {
	        getMenu: function() {
	             //return the promise directly.
	             return $http.get('/menu')
	                       .then(function(result) {
	                            //resolve the promise as the data
	                            return result.data;
	                        });
	        }
	   }
	});



myApp.controller('MenuCtrl', function($scope, menuService) {
menuService.getMenu().then(function(menu) {
	
	// ligne des jours de la semaine
	$scope.jours = menu.plats[0];
	var lundi =  menu.plats[0][1];
	var mardi =  menu.plats[0][2];
	var mercredi =  menu.plats[0][3];
	var jeudi =  menu.plats[0][4];
	var vendredi =  menu.plats[0][5];
	
	//on enlève la ligne des jours pour récupérer uniquement les lignes des plats
	menu.plats.splice(0,1);
	$scope.plats = menu.plats;
	$scope.semaine = menu.semaine;
	
	
	$('.semaine').append(menu.semaine);

	$('.table').dataTable({
		"data" : menu.plats,
		paging: false,
		searching: false,
		ordering:  false,
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
	$('.table tbody').on('click', 'td', function() {
		console.debug("ligne"+table.cell(this).index().row+ " column "+table.cell(this).index().column);
		if ($(this).hasClass('cell_selected')) {
			$(this).removeClass('cell_selected');
		} else {
			$(this).addClass('cell_selected');
		}
	});
	
	
	
	
});


});



