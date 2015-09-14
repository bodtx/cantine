
var settingControllers = angular.module('settingControllers',[]);

//accueil Hello
settingControllers.controller('SettingCtrl', ['$scope', '$rootScope', '$injector', function ($scope, $rootScope, $injector) {

    var settingService = $injector.get('settingService');

    settingService.getSetting().then(function(setting) {
        $scope.setting = setting ;
    });


    $scope.saveSetting = function(setting) {
        setting.cer = $rootScope.user.cer;
        settingService.saveSetting(setting).then(function(result) {
            $rootScope.user.name = setting.nom;
            $rootScope.setting = setting;
        });
    };

    $scope.isCafeActive = function (day, cafeJour) {
        return day === cafeJour;
    };

}]);

