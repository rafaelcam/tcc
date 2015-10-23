package com.testehadoop.temperatura;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class TempJaneiroMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, FloatWritable> {
    
    @Override
    public void map(LongWritable key, Text value, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {
    	String linha = value.toString();
        
        String[] atributos = linha.split(",");
        String[] datetime = atributos[0].split(" ");
        String[] mes = datetime[0].split("/");
        String chave = datetime[0].substring(0, 5);

        if(mes[1].equals("01")) {
            output.collect(new Text(chave), new FloatWritable(Float.parseFloat(atributos[13])));
        }
    }
}