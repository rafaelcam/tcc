pessoasFormValidation.factory("serviceForm", function(config, $http) {
 
    var _getAllPessoas = function() {
        return $http.get("rest/pessoa/all");
    };//http://localhost:8080/contex/myapp/pessoa/all
     
    var _getByIdPessoa = function(id) {
        return $http.get("rest/pessoa/" + id);
    };
     
 
    var _savePessoa = function(pessoa) {
        return $http.post("rest/pessoa/salvar", angular.toJson(pessoa));
    };
 
    var _updatePessoa = function(pessoa) {
        return $http.put("rest/pessoa/" + pessoa.id, angular.toJson(pessoa));
    };
 
    var _deletePessoa = function(id) {
        return $http.delete("rest/pessoa/" + id);
    };
 
    var _calculate = function(people) {
 
        var _nascimento = people.nascimento;
        var _dataCorrente = people.dataCorrente;            
 
        var _age = new Date(_dataCorrente).getFullYear()
                - new Date(_nascimento).getFullYear();
         
        return {
            age : _age,
        };
    };
 
    return {
        getAllPessoas : _getAllPessoas,
        getByIdPessoa :_getByIdPessoa,
        savePessoa : _savePessoa,
        updatePessoa : _updatePessoa,
        deletePessoa:_deletePessoa,
        calculate : _calculate
    };
 
});