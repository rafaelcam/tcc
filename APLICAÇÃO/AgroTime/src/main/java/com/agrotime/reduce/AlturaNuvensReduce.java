/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.reduce;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 *
 * @author jrcsilva
 */
public class AlturaNuvensReduce extends MapReduceBase implements Reducer<Text, FloatWritable, Text, FloatWritable> {

    @Override
    public void reduce(Text key, Iterator<FloatWritable> values, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {
        int cont = 0;
        float soma = 0f;
        float value;
        
        while(values.hasNext()) {
        	value = values.next().get();
        	
        	if(value != 0.0) {
        		cont++;
        		soma += value;
        	}
        }
        
        float media = soma / cont;
        output.collect(key, new FloatWritable(media));
    }
}
