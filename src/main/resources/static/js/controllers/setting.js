
var settingControllers = angular.module('settingControllers',[]);

//accueil Hello
settingControllers.controller('SettingCtrl', ['$scope', 'settingService', function ($scope, settingService) {


    settingService.getSetting().then(function(setting) {
        $scope.nom = setting.nom ;
    });


    $scope.saveSetting = function(setting) {
        settingService.saveSetting(setting).then(function(result) {
            $scope.nom = setting.nom ;
            $scope.hello= setting.nom ;
        });
    };



}]);

