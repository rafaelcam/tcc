/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.entidades;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JRafael
 */
@XmlRootElement
public class TemperaturaDiaria implements Serializable {
    
    private String temperatura;
    private String mes;
    private String dia;

    public TemperaturaDiaria() {
    }

    public TemperaturaDiaria(String temperatura, String mes, String dia) {
        this.temperatura = temperatura;
        this.mes = mes;
        this.dia = dia;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
