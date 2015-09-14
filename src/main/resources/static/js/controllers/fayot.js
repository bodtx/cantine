
var fayotModule  = angular.module('fayotModule', []);

fayotModule.controller('FayotCtrl',['$scope', '$injector', function($scope, $injector) {

    //injection des services
    var fayotService = $injector.get('fayotService');

    fayotService.getFayots().then(function(fayots) {
      for (var fayot in fayots) {
        $scope.addSlide(fayot);
      }
    });


    $scope.myInterval = 3000;
      var slides = $scope.slides = [];
      $scope.addSlide = function(fayot) {
        var newWidth = 600 + slides.length + 1;
        slides.push({
          image: 'http://placekitten.com/' + newWidth + '/300',
          text: fayot.cer+ ' ' + fayot.credit
        });
      };



}]);