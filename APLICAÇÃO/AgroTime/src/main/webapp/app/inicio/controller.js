agroTimeApp.controller("inicioController", function($scope, serviceInicio) { 

    $scope.hoje = moment().format('DD/MM/YYYY');
    $scope.variaveis = {
        temperatura : "Carregando...",
        coberturaNuvens : "Carregando...",
        alturaNuvens : "Carregando...",
        velocidadeVento : "Carregando...",
        umidadeAr : "Carregando..." 
    };
    
    var data = $scope.hoje.split("/");
    
    serviceInicio.getVarsHoje(data[1], data[0]).success(
                function (data, status, headers, config) {
                    angular.forEach(data, function (value, key) {
                        if(isNaN(value)) {
                            value = 0.0;
                        }
                        if(key === "temperatura") {
                            $scope.variaveis.temperatura = parseFloat(value).toFixed(2)+"°C";
                        }
                        if(key === "velocidadevento") {
                            $scope.variaveis.velocidadeVento = parseFloat(value).toFixed(2)+"m/s";
                        }
                        if(key === "alturanuvens") {
                            $scope.variaveis.alturaNuvens = parseFloat(value).toFixed(2)+"m";
                        }
                        if(key === "coberturanuvens") {
                            $scope.variaveis.coberturaNuvens = parseFloat(value).toFixed(2)+"%";
                        }
                        if(key === "umidade") {
                            $scope.variaveis.umidadeAr = parseFloat(value).toFixed(2)+"%";
                        }
                    });
                }).error(function (data, status, headers, config) {
                    $("#statusprocess").hide();
                    switch (status) {
                        case 401:
                        {
                            $scope.message = "Você precisa ser autenticado!";
                            break;
                        }
                        case 500:
                        {
                            $scope.message = "Erro!";
                            break;
                        }
                    }
                    console.log(data, status);
                });
});