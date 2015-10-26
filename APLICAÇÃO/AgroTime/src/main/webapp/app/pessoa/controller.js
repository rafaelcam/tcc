agroTimeApp.controller("ctrlPpessoasFormValidation", function($scope, serviceForm) {
 
    $scope.listaPessoas = {};
    $scope.pessoa = {};
     
    //registra o id de uma pessoa na tabela
    $scope.checkboxIdPessoa = {
            id : -1,
     };
     
    //seleciona uma pessoa na tabela
    $scope.selectPessoa = function(pessoa) {
         
        $scope.pessoa = pessoa;     
         
    };
 
    //calculate idade
    $scope.calcular = function(pessoa) {
 
        // $scope.age = 19;
 
        $scope.idadePessoa = serviceForm.calculate(pessoa);
        $scope.pessoa.idade = $scope.idadePessoa.age;
        //console.log(pessoa.idade);
    };
     
    //getAllPessoas lista todos os dados
    var listarPessoas = function() {
        serviceForm.getAllPessoas().success(
                function(data, status, headers, config) {
 
                    $scope.listaPessoas = data;
 
                }).error(function(data, status, headers, config) {
 
            switch (status) {
            case 401: {
                $scope.message = "Você precisa ser autenticado!"
                break;
            }
            case 500: {
                $scope.message = "Erro!";
                break;
            }
            }
            console.log(data, status);
        });
    };
    listarPessoas();
     
     
     
     
     
    //getByIdPessoa
    //savePessoa salva um novo registro de uma pessoa
    $scope.savePessoa = function(pessoa) {
        serviceForm.savePessoa(pessoa).success(
                function(data, status, headers, config) {
                    listarPessoas();
                    $scope.message = "registro Pessoa savo com sucesso!";
                }).error(function(data, status, headers, config) {
            switch (status) {
            case 401: {
                $scope.message = "Você precisa ser autenticado!"
                break;
            }
            case 500: {
                $scope.message = "Erro!";
                break;
            }
            }
            console.log(data, status);
        });
    };
     
    //updatePessoa atualiza registro de uma pessoa quando selecionado na tabela
    $scope.updatePessoa = function(pessoa) {
        serviceForm.updatePessoa(pessoa).success(
                function(data, status, headers, config) {
                    listarPessoas();
                    $scope.message = "registro Pessoa savo com sucesso!";
                }).error(function(data, status, headers, config) {
            switch (status) {
            case 401: {
                $scope.message = "Você precisa ser autenticado!"
                break;
            }
            case 500: {
                $scope.message = "Erro!";
                break;
            }
            }
            console.log(data, status);
        });
    };
     
     
     
    //deletePessoa remove um registro de uma pessoa quando selecionado na tabela
    $scope.deletePessoa = function(pessoa) {
        serviceForm.deletePessoa(pessoa.id).success(
                function(data, status, headers, config) {
                    listarPessoas();
                    $scope.message = "registro Pessoa savo com sucesso!";
                }).error(function(data, status, headers, config) {
            switch (status) {
            case 401: {
                $scope.message = "Você precisa ser autenticado!"
                break;
            }
            case 500: {
                $scope.message = "Erro!";
                break;
            }
            }
            console.log(data, status);
        });
    };
         
 
});