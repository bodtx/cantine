myApp.factory('transportService', function($http) {

    var transportService={};

    transportService.prochainsPassages = function() {
         return $http.get('prochainsPassages')
                .then(function(result) {
                     return result.data;
                 });
    };

    transportService.velib = function() {
     return $http.get('velib')
                 .then(function(result) {
                      return result.data;
                  });
     };

     transportService.problemeTisseo = function() {
         return $http.get('problemeTisseo')
                 .then(function(result) {
                      return result.data;
                  });
     }


    return transportService;

});
