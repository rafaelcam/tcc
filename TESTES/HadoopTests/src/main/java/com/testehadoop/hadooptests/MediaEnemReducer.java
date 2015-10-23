/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testehadoop.hadooptests;

/**
 *
 * @author JRafael
 */
import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class MediaEnemReducer extends MapReduceBase implements Reducer<Text, FloatWritable, Text, FloatWritable> {

    @Override
    public void reduce(Text key, Iterator<FloatWritable> values, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {
        int cont = 0;
        float soma = 0f;
        
        while(values.hasNext()) {
            cont++;
            soma = soma + values.next().get();
        }
        
        float media = soma / cont;
        output.collect(key, new FloatWritable(media));
    }
}
