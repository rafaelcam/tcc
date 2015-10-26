/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.bo;

import com.agrotime.mapreduce.temperatura.TemperaturaDiariaMapReduce;
import com.agrotime.util.HDFSUtil;
import javax.inject.Inject;

/**
 *
 * @author JRafael
 */
public class TemperaturaDiariaBO {
    
    @Inject
    private TemperaturaDiariaMapReduce temperaturaDiariaMapReduce;
    
    @Inject
    private HDFSUtil hdfsUtil;
    
    public void processarDadosTemperaturaDiaria() throws Exception {
        hdfsUtil.removeDirectory("/agrotime/output/temperaturadiaria");
        temperaturaDiariaMapReduce.runMapReduce("01");
    }
    
}
