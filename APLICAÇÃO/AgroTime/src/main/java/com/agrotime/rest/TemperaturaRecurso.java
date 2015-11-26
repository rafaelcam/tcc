/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.rest;

import com.agrotime.bo.TemperaturaBO;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.agrotime.bo.VelocidadeVentoBO;

/**
 *
 * @author jrcsilva
 */
@Path("/temperatura")
public class TemperaturaRecurso {
    
    @GET
    @Produces({ MediaType.APPLICATION_JSON/* , MediaType.APPLICATION_XML */ })
    @Path("/all/{dataInicio}/{dataFim}")
    public Map<String, Double> getVelocidadeVento(@PathParam("dataInicio") String dataInicio, @PathParam("dataFim") String dataFim) {
        try {
            return new TemperaturaBO().processarDados(dataInicio, dataFim);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
