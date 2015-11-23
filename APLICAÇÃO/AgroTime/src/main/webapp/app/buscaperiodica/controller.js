agroTimeApp.controller("buscaPeriodicaController", function($scope, serviceBuscaPeriodica) { 
    
	
	
    $scope.processBuscaPeriodica = function() {        
    	serviceBuscaPeriodica.getVelocidadeVento("02", "06").success(
                function (data, status, headers, config) {
                    plotarDadosVelocidadeVento(data);
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
                
    };
    
    function plotarDadosVelocidadeVento(data) {
	//var d1 = [[Number(new Date(2015,0,22)), 17], [Number(new Date(2015,2,10)), 41], [Number(new Date(2015,3,10)), 12]];
        var d1 = construirDadosVelocidadeVento(data);

	var data1 = [
		{ label: "Total clicks", data: d1, color: App.getLayoutColorCode('green') }
	];

	$.plot("#chart_velocidade_vento", data1, $.extend(true, {}, Plugins.getFlotDefaults(), {
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
    
    function construirDadosVelocidadeVento(data) {
        var array = [];
        
        angular.forEach(data, function (value, key) {
            array.push([new Date(key+"/2015"), value]);
            console.log(new Date(key+"/2015").toLocaleDateString("pt-BR"));
        });
        
        return array;
    }
});