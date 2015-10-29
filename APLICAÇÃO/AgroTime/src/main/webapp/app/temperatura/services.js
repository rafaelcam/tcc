agroTimeApp.factory("serviceTemperatura", function(config, $http) {
 
    var _getTemperaturasPorMes = function() {
        return $http.get("rest/temperaturadiaria/all");
    };


    return {
        getTemperaturasPorMes : _getTemperaturasPorMes
    };
 
});