// menu service
myApp.factory('menuService', function($http) {
	return {
		getMenu : function() {
			// return the promise directly.
			return $http.get('/menu').then(function(result) {
				// resolve the promise as the data
				csrf = result.headers("X-CSRF-TOKEN");
				return result.data;
			});
		}
	}
});