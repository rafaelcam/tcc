agroTimeApp.controller("buscaPeriodicaController", function($scope, serviceBuscaPeriodica) { 
    
	
	
    $scope.processBuscaPeriodica = function() {
    	
    	serviceBuscaPeriodica.getVelocidadeVento("02", "06").success(
                function (data, status, headers, config) {

                    console.log(data);
                    $("#statusprocess").hide();
                    
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
    }
});