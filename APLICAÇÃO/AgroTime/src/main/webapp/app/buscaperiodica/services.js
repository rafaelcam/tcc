agroTimeApp.factory("serviceBuscaPeriodica", function($http) {
    
    var _getVelocidadeVento = function(mesInicio, mesFim) {
        return $http.get("rest/velocidadevento/all/"+mesInicio+"/"+mesFim);
    };
    
    var _getTemperatura = function(mesInicio, mesFim) {
        return $http.get("rest/temperatura/all/"+mesInicio+"/"+mesFim);
    };

    return {
    	getVelocidadeVento : _getVelocidadeVento,
        getTemperatura : _getTemperatura
    };
 
});