/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.mapper;

import java.io.IOException;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 *
 * @author jrcsilva
 */
public class VarsHojeMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, FloatWritable> {
    
    String mes;
    String dia;
    
    @Override
    public void configure(JobConf job) {
        mes = job.get("mes");
        dia = job.get("dia");
        super.configure(job); 
    }
    
    @Override
    public void map(LongWritable key, Text value, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {
    	String linha = value.toString();
        
        String[] atributos = linha.split(",");
        String[] datetime = atributos[0].split(" ");
        String[] data = datetime[0].split("/");
        String mesData = data[1];
        String diaData = data[0];

        if(mesData.equals(mes) && diaData.equals(dia)) {
            output.collect(new Text("temperatura"), new FloatWritable(Float.parseFloat(atributos[13])));
            output.collect(new Text("velocidadevento"), new FloatWritable(Float.parseFloat(atributos[6])));
            output.collect(new Text("alturanuvens"), new FloatWritable(Float.parseFloat(atributos[7])));
            output.collect(new Text("coberturanuvens"), new FloatWritable(Float.parseFloat(atributos[8])));
            output.collect(new Text("umidade"), new FloatWritable(Float.parseFloat(atributos[18])));
        }
    }
}
