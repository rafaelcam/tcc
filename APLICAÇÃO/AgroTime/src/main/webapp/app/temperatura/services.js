agroTimeApp.factory("serviceTemperatura", function($http) {
 
    var _getTemperaturasPorMes = function() {
        return $http.get("rest/temperaturadiaria/all");
    };


    return {
        getTemperaturasPorMes : _getTemperaturasPorMes
    };
 
});