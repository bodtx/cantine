//userName service
myApp.factory('userNameService', function($http) {
	   return {
		   getUserName: function() {
	             return $http.get('/userName').then(function(result) {
	                            return result.data;
	                        });
	        }
	   }
	});


////menu service
//myApp.factory('badgeuseService', function($http) {
//	   return {
//	        getHeure: function(h) {
//	             //return the promise directly.
//	             return $http.get('/heure?heure='+ h)
//	                       .then(function(result) {
//	                            //resolve the promise as the data
//	                            return result.data;
//	                        });
//	        }
//	   }
//	});
