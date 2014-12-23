//Setting service
myApp.factory('settingService', function($http) {

    var settingService ={};

    settingService.getSetting = function() {
         return $http.get('getSetting', {params: { cer: cer }})
                   .then(function(result) {
                        return result.data;
                    });
    };

    settingService.saveSetting = function(setting) {
        var data = JSON.stringify(setting);
        var  headers = {"headers" : { Accept : "application/json", "Content-Type" : "application/json", "X-CSRF-TOKEN" : csrf}};
        return $http.post('saveSetting', data, headers).then(function(result) {
            return result.data;
        });
    };


    return settingService;
	});


