/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testehadoop.hadooptests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivilegedExceptionAction;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.security.UserGroupInformation;

/**
 *
 * @author JRafael
 */
public class HDFSTest {

    public static void main(String[] args) throws IOException {
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hduser"); 
        
        try {
            ugi.doAs(new PrivilegedExceptionAction<Void>() {
                public Void run() throws Exception {
                    HDFSTest simpleLocalLs = new HDFSTest();
                    
                    simpleLocalLs.testRemoveDiretorio();
                    //simpleLocalLs.runMapReduceMediaEnem();
                    
                    
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void testCreateFile() {
        try {
            //String texto = "Testando a escrita do HDFS";
                    
            //InputStream in = new BufferedInputStream(new ByteArrayInputStream(texto.getBytes()));
            InputStream in = new FileInputStream(new File("D://DADOS_ENEM_2010.txt"));
            
            
            Path path = new Path("/mediaenem/in/DADOS_ENEM_2010.txt");

            Configuration conf = new Configuration();
            conf.set("hadoop.job.ugi", "hduser");
            conf.set("fs.defaultFS", "hdfs://192.168.81.100:9000");

            FileSystem fs = FileSystem.get(conf);

            FSDataOutputStream out = fs.create(path);

            IOUtils.copyBytes(in, out, conf); 
            
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void testReadFile() {
        try {
            Path path = new Path("/testecreate/texto.txt");

            Configuration conf = new Configuration();
            conf.set("hadoop.job.ugi", "hduser");
            conf.set("fs.default.name", "hdfs://192.168.81.100:9000");

            FileSystem fs = FileSystem.get(conf);

            InputStream input = fs.open(path);
            
            IOUtils.copyBytes(input, System.out, 4096);
            
            fs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void testRemoveDiretorio() {
        try {
            Path path = new Path("/mediaenem/out");

            Configuration conf = new Configuration();
            conf.set("hadoop.job.ugi", "hduser");
            conf.set("fs.defaultFS", "hdfs://192.168.81.100:9000");

            FileSystem fs = FileSystem.get(conf);

            fs.delete(path, true);
            
            fs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void runMapReduceMediaEnem() throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        conf.set("yarn.resourcemanager.address", "192.168.81.100:8032"); 
        conf.set("mapreduce.framework.name", "yarn");
        conf.set("fs.defaultFS", "hdfs://192.168.81.100:9000");
        conf.set("mapreduce.jobhistory.address", "192.168.81.100:10020");
        conf.set("mapreduce.app-submission.cross-platform", "true");
        
        
        Job job = new Job(conf);
        //job.setJar("D://HadoopTests-1.0-SNAPSHOT.jar");
        job.setJobName("Media das Notas do Enem por UF");
        FileInputFormat.addInputPath(job, new Path("/mediaenem/in"));
        FileOutputFormat.setOutputPath(job, new Path("/mediaenem/out"));
        //job.setMapperClass(MediaEnemMapper.class);
        //job.setReducerClass(MediaEnemReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
        
    }
}