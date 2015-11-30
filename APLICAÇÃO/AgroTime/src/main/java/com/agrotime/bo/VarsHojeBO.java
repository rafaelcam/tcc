/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.bo;

import com.agrotime.mapreduce.VarsHojeMapReduce;
import java.util.LinkedHashMap;
import java.util.Map;

import com.agrotime.util.HDFSUtil;

/**
 *
 * @author JRafael
 */
public class VarsHojeBO {
    
    Map<String, Double> mapResult = new LinkedHashMap<String, Double>();
	
    public Map<String, Double> processarDados(String mes, String dia) throws Exception {

            new HDFSUtil().removeDirectory("/agrotime/output/varshoje");
            new VarsHojeMapReduce().runMapReduce(mes, dia);
            lerDados();
            
            return mapResult;
    }
    
    public void lerDados() throws Exception {
        System.out.println("<<<<<---------------- Lendo Resultado MapReduce Vars Hoje ---------------->>>>>");
        
        String[] lines = new HDFSUtil().readLinesFile("/agrotime/output/varshoje/part-00000");
        int i = 1;
        String key = "", value = "";
        
        for(String line : lines) {
            
            if(i == 1) {
                mapResult.put(line.substring(0, 12), Double.parseDouble(line.substring(13)));
            } else if (i == 2 || i == 5) {
                mapResult.put(line.substring(0, 15), Double.parseDouble(line.substring(16)));
            } else if (i == 3) {
                mapResult.put(line.substring(0, 11), Double.parseDouble(line.substring(12)));
            } else if (i == 4) {
                mapResult.put(line.substring(0, 7), Double.parseDouble(line.substring(8)));
            }
            
            i++;
        }
    }
}
