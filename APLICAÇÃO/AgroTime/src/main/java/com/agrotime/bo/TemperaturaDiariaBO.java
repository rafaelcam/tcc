/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.bo;

import com.agrotime.mapreduce.temperatura.TemperaturaDiariaMapReduce;
import com.agrotime.util.HDFSUtil;

/**
 *
 * @author JRafael
 */
public class TemperaturaDiariaBO {
    
    public void processarDadosTemperaturaDiaria() throws Exception {
        new HDFSUtil().removeDirectory("/agrotime/output/temperaturadiaria");
        new TemperaturaDiariaMapReduce().runMapReduce("01");
    }
    
}
