/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.bo;

import com.agrotime.mapreduce.UmidadeRelativaMapReduce;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.agrotime.util.HDFSUtil;

/**
 *
 * @author JRafael
 */
public class UmidadeRelativaBO {
    
	List<Map<String, Double>> listMapResult = new ArrayList<Map<String, Double>>();
	Map<String, Double> mapResult = new LinkedHashMap<String, Double>();
	
    public Map<String, Double> processarDados(String mesInicio, String mesFim) throws Exception {

            new HDFSUtil().removeDirectory("/agrotime/output/umidaderelativa");
            new UmidadeRelativaMapReduce().runMapReduce(mesInicio, mesFim);
            lerDados(mesInicio, mesFim);
            
            return mapResult;
    }
    
    public void lerDados(String mesInicio, String mesFim) throws Exception {
        System.out.println("<<<<<---------------- Lendo Resultado MapReduce Umidade Relativa do Ar ---------------->>>>>");
        
        String[] lines = new HDFSUtil().readLinesFile("/agrotime/output/umidaderelativa/mes"+mesInicio+mesFim+"/part-00000");
        
        for(int i = Integer.parseInt(mesInicio); i <= Integer.parseInt(mesFim); i++) {
        	Map<String, Double> map = new LinkedHashMap<String, Double>();
        	
        	for(String line : lines) {
        		
        		if(Integer.parseInt((line.substring(0, 5).split("/"))[1]) == i) {
                                String[] data = line.substring(0, 5).split("/");
                            
        			map.put(data[1]+"/"+data[0], Double.parseDouble(line.substring(6)));
        		}
        		
            }
        	
        	listMapResult.add(map);
		}
        
        for(Map<String, Double> map : listMapResult) {
        	mapResult.putAll(map);
        }
    }
}
