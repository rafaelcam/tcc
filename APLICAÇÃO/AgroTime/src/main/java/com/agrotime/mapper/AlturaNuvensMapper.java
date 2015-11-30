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
public class AlturaNuvensMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, FloatWritable> {
    
    String mesInicio;
    String mesFim;
    
    @Override
    public void configure(JobConf job) {
        mesInicio = job.get("mesInicio");
        mesFim = job.get("mesFim");
        super.configure(job); 
    }
    
    @Override
    public void map(LongWritable key, Text value, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {
    	String linha = value.toString();
        
        String[] atributos = linha.split(",");
        String[] datetime = atributos[0].split(" ");
        String[] mes = datetime[0].split("/");
        String chave = datetime[0].substring(0, 5);

        if(Integer.parseInt(mes[1]) >= Integer.parseInt(mesInicio) && Integer.parseInt(mes[1]) <= Integer.parseInt(mesFim)) {
            output.collect(new Text(chave), new FloatWritable(Float.parseFloat(atributos[7])));
        }
    }
}
