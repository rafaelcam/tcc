/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.bo;

import com.agrotime.mapreduce.TemperaturaDiariaMapReduce;
import com.agrotime.util.HDFSUtil;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author JRafael
 */
public class TemperaturaDiariaBO {
    
    Map<Double, Double> mapTemperaturasDiarias = new LinkedHashMap<Double, Double>();
    Map<String,Map<Double, Double>> mapDadosMensais = new LinkedHashMap<String,Map<Double, Double>>();
    
    public Map<String,Map<Double, Double>> processarDadosTemperaturaDiaria() throws Exception {
        Map<String, String> meses = new LinkedHashMap<>();
        meses.put("Janeiro", "01");
        meses.put("Fevereiro", "02");
        meses.put("Marco", "03");
        meses.put("Abril", "04");
        meses.put("Maio", "05");
        meses.put("Junho", "06");
        meses.put("Julho", "07");
        meses.put("Agosto", "08");
        meses.put("Setembro", "09");
        meses.put("Outubro", "10");
        meses.put("Novembro", "11");
        meses.put("Dezembro", "12");
        
        for (Map.Entry<String, String> entry : meses.entrySet()) {
            new HDFSUtil().removeDirectory("/agrotime/output/temperaturadiaria");
            new TemperaturaDiariaMapReduce().runMapReduce(entry.getValue());
            lerDadosTemperaturaDiaria(entry.getKey(), entry.getValue());
        }
        
        return mapDadosMensais;
    }
    
    public void lerDadosTemperaturaDiaria(String nomeMes, String mes) throws Exception {
        System.out.println("<<<<<---------------- Lendo Resultado MapReduce Temperatura Diária ---------------->>>>>");
        
        String[] lines = new HDFSUtil().readLinesFile("/agrotime/output/temperaturadiaria/mes"+mes+"/part-00000");
        double i = 1.0;
        this.mapTemperaturasDiarias = new LinkedHashMap<>();
        
        for(String line : lines) {
            this.mapTemperaturasDiarias.put(i, Double.parseDouble(line.substring(6)));
            i++;
        }
        
        this.mapDadosMensais.put(nomeMes, this.mapTemperaturasDiarias);
    }
}
