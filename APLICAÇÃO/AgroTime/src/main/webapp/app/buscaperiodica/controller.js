agroTimeApp.controller("buscaPeriodicaController", function($scope, serviceBuscaPeriodica) { 
    $("#statusprocess").hide();
    $scope.mesInicio = "";
    $scope.mesFim = "";
    $scope.variaveis = {
                            temperatura : {check : false, view : false},
                            velocidadeVento : {check : false, view : false},
                            alturaNuvens: {check : false, view : false},
                            coberturaNuvens : {check : false, view : false},
                            umidadeAr : {check : false, view : false}
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
        
        $("#statusprocess").show();
        
        if($scope.variaveis.temperatura.check) {
            processarTemperatura();
        } 
        if($scope.variaveis.velocidadeVento.check) {
            processarVelocidadeVento();
        } 
        if($scope.variaveis.alturaNuvens.check) {
            processarAlturaNuvens();
        } 
        if($scope.variaveis.coberturaNuvens.check) {
            processarCoberturaNuvens();
        } 
        if($scope.variaveis.umidadeAr.check) {
            processarUmidadeRelativa();
        }        
    };
    
    function processarVelocidadeVento() {
        serviceBuscaPeriodica.getVelocidadeVento($scope.mesInicio, $scope.mesFim).success(
                function (data, status, headers, config) {
                    $scope.variaveis.velocidadeVento.view = true;
                    plotarDados(data, "Velocidade do Vento", "#chart_velocidade_vento", "green", "m/s");
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
                    $scope.variaveis.temperatura.view = true;
                    plotarDados(data, "Temperatura", "#chart_temperatura", "blue", "°C");
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
    
    function processarAlturaNuvens() {
        serviceBuscaPeriodica.getAlturaNuvens($scope.mesInicio, $scope.mesFim).success(
                function (data, status, headers, config) {
                    $scope.variaveis.alturaNuvens.view = true;
                    plotarDados(data, "Altura Nuvens", "#chart_altura_nuvens", "red", "m");
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
    
    function processarCoberturaNuvens() {
        serviceBuscaPeriodica.getCoberturaNuvens($scope.mesInicio, $scope.mesFim).success(
                function (data, status, headers, config) {
                    $scope.variaveis.coberturaNuvens.view = true;
                    plotarDados(data, "Cobertura Nuvens", "#chart_cobertura_nuvens", "green", "%");
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
    
    function processarUmidadeRelativa() {
        serviceBuscaPeriodica.getUmidadeRelativa($scope.mesInicio, $scope.mesFim).success(
                function (data, status, headers, config) {
                    $scope.variaveis.umidadeAr.view = true;
                    plotarDados(data, "Umidade Relativa do Ar", "#chart_umidade_relativa", "blue", "%");
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
    
    function plotarDados(data, label, idGrafico, cor, unidade) {
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
			content: '%s: %y'+unidade+' no dia %x'
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