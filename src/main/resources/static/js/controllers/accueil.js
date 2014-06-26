
var accueilControllers = angular.module('accueilControllers', []);

//accueil Hello
accueilControllers.controller('accueilCtrl', ['$scope', 'userNameService' , function ($scope, userNameService) {
	
	userNameService.getUserName().then(function(username) {
    		$scope.hello= 'Hello ' + username;
    	});
	
}]);

