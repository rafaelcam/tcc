agroTimeApp.factory("serviceBuscaPeriodica", function($http) {
    
    var _getUmidadeRelativa = function(mesInicio, mesFim) {
        return $http.get("rest/umidaderelativa/all/"+mesInicio+"/"+mesFim);
    };
    
    var _getCoberturaNuvens = function(mesInicio, mesFim) {
        return $http.get("rest/coberturanuvens/all/"+mesInicio+"/"+mesFim);
    };
    
    var _getAlturaNuvens = function(mesInicio, mesFim) {
        return $http.get("rest/alturanuvens/all/"+mesInicio+"/"+mesFim);
    };
    
    var _getVelocidadeVento = function(mesInicio, mesFim) {
        return $http.get("rest/velocidadevento/all/"+mesInicio+"/"+mesFim);
    };
    
    var _getTemperatura = function(mesInicio, mesFim) {
        return $http.get("rest/temperatura/all/"+mesInicio+"/"+mesFim);
    };

    return {
        getUmidadeRelativa : _getUmidadeRelativa,
        getCoberturaNuvens : _getCoberturaNuvens,
        getAlturaNuvens : _getAlturaNuvens,
    	getVelocidadeVento : _getVelocidadeVento,
        getTemperatura : _getTemperatura
    };
 
});