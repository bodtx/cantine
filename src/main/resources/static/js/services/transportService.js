//badgeInfo service
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

