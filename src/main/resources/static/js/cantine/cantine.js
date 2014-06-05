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
	
	var platsMail = [];
	var semaine; 
	var header;
	
	// ligne des jours de la semaine
	$scope.jours = menu.plats[0];
	header = menu.plats[0];
	var lundi =  menu.plats[0][1];
	var mardi =  menu.plats[0][2];
	var mercredi =  menu.plats[0][3];
	var jeudi =  menu.plats[0][4];
	var vendredi =  menu.plats[0][5];
	
	//on enlève la ligne des jours pour récupérer uniquement les lignes des plats
	menu.plats.splice(0,1);
	$scope.plats = menu.plats;
	$scope.semaine = menu.semaine;
	semaine = menu.semaine;
	
	
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
	
	
	function email() {
		var sBody = "Bonjour voici mon choix\n";
		var table = $('.table').DataTable();
		cells = table.cells(".cell_selected");
		
		for ( var i = 1; i < 6; i++) {
			platsMail[i - 1] = "\n" + header[i] + ":    ";
		}
		$.each(table.cells(".cell_selected").eq(0), function(cellIdx, i) {
			console.debug("\nNumero jour " + header[this.column] + " plat "
					+ table.cell(this.row, this.column).data());
			platsMail[this.column - 1] += " "
					+ table.cell(this.row, this.column).data() + " --- ";
		});

		sBody += platsMail.join("\n") + "\n\nMerci";
		console.debug(sBody);
		var sMailTo = "mailto:";
		sMailTo += escape("mg133@ansamble.fr") + "?subject="
				+ escape("choix: " + semaine) + "&body=" + escape(sBody);
		window.location.href = sMailTo;
	}

	$scope.setUserChoix = function () {
		$.cookie('nom', $(".nom").val(), { expires: 365 });
		var now=new Date();
		var choix = {
				nom:$(".nom").val(),
				date:now.getTime(),
				plats:[]
		}
		j=0;
		$.each($('.table').DataTable().cells(".cell_selected").eq(0), function(cellIdx, i) {
			choix.plats[j++] = $('.table').DataTable().cell(this.row, this.column).data();
		});
		
		$.ajax({
			  type: "POST",
			  headers: {          
	                 Accept : "application/json",         
	                "Content-Type": "application/json"   
	  		},
			  url: "choix",
			  data: JSON.stringify(choix),
			  success:email(),
			  dataType: "json"
			});
	} 
	
	
	
	
});




});





