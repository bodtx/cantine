//badgeInfo service
myApp.factory('badgeInfoService', function($http) {
	   return {
	        getBadgeInfo: function() {
	             return $http.get('badgeInfo')
	                       .then(function(result) {
	                            //resolve the promise as the data
	                            return result.data;
	                        });
	        }
	   }
	});