/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.rest;

import com.agrotime.bo.VarsHojeBO;
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
@Path("/varshoje")
public class VarsHojeRecurso {
    
    @GET
    @Produces({ MediaType.APPLICATION_JSON/* , MediaType.APPLICATION_XML */ })
    @Path("/all/{mes}/{dia}")
    public Map<String, Double> getVelocidadeVento(@PathParam("mes") String mes, @PathParam("dia") String dia) {
        try {
            System.out.println("Mes: "+mes);
            System.out.println("Dia: "+dia);
            
            
            return new VarsHojeBO().processarDados(mes, dia);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
