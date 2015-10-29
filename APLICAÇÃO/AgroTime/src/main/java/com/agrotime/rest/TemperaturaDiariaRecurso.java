/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.rest;

import com.agrotime.bo.TemperaturaDiariaBO;
import com.agrotime.entidades.Pessoa;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jrcsilva
 */
@Path("/temperaturadiaria")
public class TemperaturaDiariaRecurso {
    
    @GET
    @Produces({ MediaType.APPLICATION_JSON/* , MediaType.APPLICATION_XML */ })
    @Path("/all")
    public Map<String, Map<Double, Double>> getPessoasJSONP() {
        try {
            /*
            Map<Double, Double> mapTemperaturasDiarias = new LinkedHashMap<Double, Double>();
            mapTemperaturasDiarias.put(1.0, 15.0);
            mapTemperaturasDiarias.put(2.0, 21.0);
            mapTemperaturasDiarias.put(3.0, 24.0);
            mapTemperaturasDiarias.put(4.0, 32.0);
            mapTemperaturasDiarias.put(5.0, 40.0);
            mapTemperaturasDiarias.put(6.0, 20.0);
            mapTemperaturasDiarias.put(7.0, 14.0);
            mapTemperaturasDiarias.put(8.0, 26.0);
            mapTemperaturasDiarias.put(9.0, 34.0);
            mapTemperaturasDiarias.put(10.0, 6.0);
            mapTemperaturasDiarias.put(11.0, 15.0);
            mapTemperaturasDiarias.put(12.0, 17.0);
            mapTemperaturasDiarias.put(13.0, 14.0);
            
            Map<String,Map<Double, Double>> listDadosMensais = new LinkedHashMap<String,Map<Double, Double>>();
            listDadosMensais.put("Janeiro", mapTemperaturasDiarias);
            */
            
            return new TemperaturaDiariaBO().processarDadosTemperaturaDiaria();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
