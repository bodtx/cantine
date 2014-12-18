myApp.factory('prochainsPassagesService', function($http) {
	   return {
	        prochainsPassages: function() {
	             return $http.get('prochainsPassages')
                       .then(function(result) {
                            return result.data;
                        });
	        }
	   }
	});


myApp.factory('velibService', function($http) {
	   return {
	        velib: function() {
	             return $http.get('velib')
                       .then(function(result) {
                            return result.data;
                        });
	        }
	   }
	});


myApp.factory('problemeTisseoService', function($http) {
	   return {
	        problemeTisseo: function() {
	             return $http.get('problemeTisseo')
                       .then(function(result) {
                            return result.data;
                        });
	        }
	   }
	});
