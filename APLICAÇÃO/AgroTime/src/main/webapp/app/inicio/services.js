agroTimeApp.factory("serviceInicio", function($http) {
    
    var _getVarsHoje = function(mes, dia) {
        return $http.get("rest/varshoje/all/"+mes+"/"+dia);
    };

    return {
        getVarsHoje : _getVarsHoje
    };
 
});