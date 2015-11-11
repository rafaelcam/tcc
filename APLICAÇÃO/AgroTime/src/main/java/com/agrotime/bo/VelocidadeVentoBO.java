/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.bo;

import com.agrotime.mapreduce.TemperaturaDiariaMapReduce;
import com.agrotime.mapreduce.VelocidadeVentoMapReduce;
import com.agrotime.util.HDFSUtil;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author JRafael
 */
public class VelocidadeVentoBO {
    
    public void processarDadosVelocidadeVento(String mesInicio, String mesFim) throws Exception {

            new HDFSUtil().removeDirectory("/agrotime/output/velocidadevento");
            new VelocidadeVentoMapReduce().runMapReduce(mesInicio, mesFim);
            lerDadosVelocidadeVento(mesInicio, mesFim);
            
    }
    
    public void lerDadosVelocidadeVento(String mesInicio, String mesFim) throws Exception {
        System.out.println("<<<<<---------------- Lendo Resultado MapReduce Velocidade do Vento ---------------->>>>>");
        
        String[] lines = new HDFSUtil().readLinesFile("/agrotime/output/velocidadevento/mes"+mesInicio+mesFim+"/part-00000");
        
        
        for(String line : lines) {
            System.out.println(line);
        }
    }
}
