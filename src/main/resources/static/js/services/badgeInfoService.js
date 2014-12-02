//badgeInfo service
myApp.factory('badgeInfoService', function($http) {
	   return {
	        getBadgeInfo: function() {
	             return $http.get('badgeInfo')
	                       .then(function(result) {
	                            return result.data;
	                        });
	        }
	   }
	});


myApp.factory('asTuBadgeService', function($http) {
	   return {
	        asTuBadge: function() {
	             return $http.get('asTuBadge')
	                       .then(function(result) {
	                            return result.data;
	                        });
	        }
	   }
	});

myApp.factory('openTemptationService', function($http) {
	   	return {
       		openTemptation : function() {
       			return $http.get('/openTemptation');
       		}
       	}
	});