/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.rest;

import com.agrotime.bo.TemperaturaDiariaBO;
import java.util.Map;

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
            return new TemperaturaDiariaBO().processarDadosTemperaturaDiaria();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
