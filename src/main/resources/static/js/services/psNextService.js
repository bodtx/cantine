// psNext service
myApp.factory('psNextService', function($http) {
	return {
		getPsNext : function() {
			return $http.get('/psNext').then(function(result) {
				// resolve the promise as the data
				csrf = result.headers("X-CSRF-TOKEN");
				return result.data;
			});
		}
	}
});

