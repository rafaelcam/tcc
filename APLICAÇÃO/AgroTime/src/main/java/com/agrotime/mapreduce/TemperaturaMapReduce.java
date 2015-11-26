/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrotime.mapreduce;

import com.agrotime.mapper.TemperaturaMapper;
import com.agrotime.mapper.VelocidadeVentoMapper;
import com.agrotime.reduce.TemperaturaReduce;
import com.agrotime.reduce.VelocidadeVentoReduce;
import com.agrotime.util.Propriedades;
import java.security.PrivilegedExceptionAction;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.security.UserGroupInformation;

/**
 *
 * @author jrcsilva
 */
public class TemperaturaMapReduce {
    public void runMapReduce(final String mesInicio, final String mesFim) throws Exception {
        System.out.println("<<<<<---------------- Executando MapReduce Temperatura do Ar ---------------->>>>>");
        
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser(Propriedades.USER_HDFS); 

            ugi.doAs(new PrivilegedExceptionAction<Void>() {
                public Void run() throws Exception {
                   
                    JobConf conf = new JobConf(TemperaturaMapReduce.class);
                    conf.setJobName("Temperatura do Ar do Mes "+mesInicio+" ate o mes "+mesFim);
                    conf.set("hadoop.job.ugi", Propriedades.USER_HDFS);
                    conf.set("fs.default.name", Propriedades.URL_HDFS);
                    conf.set("mapred.job.tracker", Propriedades.URL_JOB_TRACKER);
                    conf.set("mesInicio", mesInicio);
                    conf.set("mesFim", mesFim);
                            
                    long milliSeconds = 1000*60*60;
                    conf.setLong("mapred.task.timeout", milliSeconds);

                    conf.setJar(Propriedades.CAMINHO_JAR);

                    conf.setOutputKeyClass(Text.class);
                    conf.setOutputValueClass(FloatWritable.class);

                    conf.setMapperClass(TemperaturaMapper.class);
                    conf.setCombinerClass(TemperaturaReduce.class);
                    conf.setReducerClass(TemperaturaReduce.class);

                    conf.setInputFormat(TextInputFormat.class);
                    conf.setOutputFormat(TextOutputFormat.class);

                    FileInputFormat.setInputPaths(conf, new Path(Propriedades.INPUT_HDFS_AGROTIME));
                    FileOutputFormat.setOutputPath(conf, new Path(Propriedades.OUTPUT_TEMPERATURA+mesInicio+mesFim));

                    JobClient.runJob(conf);
                    
                    return null; 
                }
            });
    }
}