agroTimeApp.controller("buscaPeriodicaController", function($scope, serviceBuscaPeriodica) { 
    
    $scope.mesInicio = "";
    $scope.mesFim = "";
    $scope.variaveis = {
                            temperatura : false,
                            velocidadeVento : false,
                            alturaNuvens: false,
                            coberturaNuvens : false,
                            umidadeAr : false
                        };
    
    $scope.processBuscaPeriodica = function() {
        if($scope.mesInicio === "" || $scope.mesFim === "") {
            alert("Selecione o mes de Inicio e Fim do processamento");
            return;
        }
        
        if($scope.mesFim < $scope.mesInicio) {
            alert("O mes Final não pode ser menor que o Inicial.");
            return;
        }
        
        if(!$scope.variaveis.temperatura && 
                !$scope.variaveis.velocidadeVento && 
                !$scope.variaveis.alturaNuvens && 
                !$scope.variaveis.coberturaNuvens && 
                !$scope.variaveis.umidadeAr) {
            alert("Selecione pelo menos uma variável.");
            return;
        }
        
        if($scope.variaveis.temperatura) {
            processarTemperatura();
        } 
        if($scope.variaveis.velocidadeVento) {
            processarVelocidadeVento();
        } 
        if($scope.variaveis.alturaNuvens) {
            
        } 
        if($scope.variaveis.coberturaNuvens) {
            
        } 
        if($scope.variaveis.umidadeAr) {
            
        }        
    };
    
    function processarVelocidadeVento() {
        serviceBuscaPeriodica.getVelocidadeVento($scope.mesInicio, $scope.mesFim).success(
                function (data, status, headers, config) {
                    plotarDados(data, "Velocidade do Vento", "#chart_velocidade_vento", "green");
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
    
    function processarTemperatura() {
        serviceBuscaPeriodica.getTemperatura($scope.mesInicio, $scope.mesFim).success(
                function (data, status, headers, config) {
                    plotarDados(data, "Temperatura", "#chart_temperatura", "blue");
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
    
    function plotarDados(data, label, idGrafico, cor) {
        var d1 = construirDados(data);

	var data1 = [
		{ label: label, data: d1, color: App.getLayoutColorCode(cor) }
	];

	$.plot(idGrafico, data1, $.extend(true, {}, Plugins.getFlotDefaults(), {
		xaxis: {
			mode: "time",
			timeformat: "%d/%m",
                        tickSize: [10, "day"]
		},
		series: {
			lines: {
				fill: true,
				lineWidth: 1.5
			},
			points: {
				show: true,
				radius: 2.5,
				lineWidth: 1.1
			}
		},
		grid: {
			hoverable: true,
			clickable: true
		},
		tooltip: true,
		tooltipOpts: {
			content: '%s: %y no dia %x'
		}
	}));
    }
    
    function construirDados(data) {
        var array = [];
        
        angular.forEach(data, function (value, key) {
            if(isNaN(value)) {
                array.push([new Date(key+"/2015"), 0.0]);
            } else {
                array.push([new Date(key+"/2015"), value]);
            }
        });
        
        return array;
    }
});