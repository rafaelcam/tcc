agroTimeApp.controller("temperaturaController", function($scope, serviceTemperatura) { 
             
    processTemperaturasPorMes();
             
    function processTemperaturasPorMes() {
        serviceTemperatura.getTemperaturasPorMes().success(
                function (data, status, headers, config) {

                    plotarDados(data);
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
    
    function plotarDados(data) {
        
        angular.forEach(data, function (value, key) {
            
            var d1 = construirDadosParaGrafico(value);
            console.log("Dados:");
            console.log(d1);
            var data1 = [
                    { label: "Temperatura", data: d1, color: App.getLayoutColorCode('blue') }
            ];

            $.plot(getIdGraphPorNameMes(key), data1, $.extend(true, {}, Plugins.getFlotDefaults(), {
                    yaxis: {
                            min: 0,
                            max: 60
                            },
                    xaxis: {
                            min: 1,
                            max: d1.length
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
                            },
                            grow: { active: true, growings:[ { stepMode: "maximum" } ] }
                    },
                    grid: {
                            hoverable: true,
                            clickable: true
                    },
                    tooltip: true,
                    tooltipOpts: {
                            content: '%s: %yº'
                    }
            }));
            
            
        });

    }
    
    function construirDadosParaGrafico(data) {
        var array = [];
        
        angular.forEach(data, function (value, key) {
            array.push([key, value]);
        });
        
        return array;
    }
    
    function getIdGraphPorNameMes(mes){
        
        if (mes === "Janeiro") {
            return "#chart_temperatura_janeiro";
        } if (mes === "Fevereiro") {
            return "#chart_temperatura_fevereiro";
        } if (mes === "Marco") {
            return "#chart_temperatura_marco";
        } if (mes === "Abril") {
            return "#chart_temperatura_abril";
        } if (mes === "Maio") {
            return "#chart_temperatura_maio";
        } if (mes === "Junho") {
            return "#chart_temperatura_junho";
        } if (mes === "Julho") {
            return "#chart_temperatura_julho";
        } if (mes === "Agosto") {
            return "#chart_temperatura_agosto";
        } if (mes === "Setembro") {
            return "#chart_temperatura_setembro";
        } if (mes === "Outubro") {
            return "#chart_temperatura_outubro";
        } if (mes === "Novembro") {
           return "#chart_temperatura_novembro"; 
        } if (mes === "Dezembro") {
           return "#chart_temperatura_dezembro"; 
        }
    }     
});