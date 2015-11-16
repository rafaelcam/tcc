agroTimeApp.factory("serviceBuscaPeriodica", function($http) {
 
    var _getVelocidadeVento = function(mesInicio, mesFim) {
        return $http.get("rest/velocidadevento/all/"+mesInicio+"/"+mesFim);
    };


    return {
    	getVelocidadeVento : _getVelocidadeVento
    };
 
});