/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testehadoop.hadooptests;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PrivilegedExceptionAction;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IOUtils;
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
 * @author JRafael
 */
public class Enem {

    public static void main(String[] args) throws Exception {
        
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hduser"); 
        
        try {
            ugi.doAs(new PrivilegedExceptionAction<Void>() {
                public Void run() throws Exception {
                   
                    Enem wc = new Enem();
                    wc.createFileInput();
                    //wc.listFileInput();
                    //wc.testReadFile();
                    //wc.removeDiretorio();
                    //wc.executeMapReduce();
                    
                    return null; 
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void createFileInput()  throws Exception {
        
        InputStream in = new FileInputStream(new File("C:\\dados\\1996.txt"));
        //InputStream in = new BufferedInputStream(new ByteArrayInputStream(texto.getBytes()));

        Configuration conf = new Configuration();
        conf.set("hadoop.job.ugi", "hduser");
        conf.set("fs.default.name", "hdfs://192.168.81.100:9000");

        FileSystem fs = FileSystem.get(conf);

        FSDataOutputStream out = fs.create(new Path("/agrotime/input/1996.txt"));

        IOUtils.copyBytes(in, out, conf); 
        
        fs.close();
    }
    
        public void removeDiretorio() {
        try {
            Path path = new Path("/enem/out");

            Configuration conf = new Configuration();
            conf.set("hadoop.job.ugi", "hduser");
            conf.set("fs.default.name", "hdfs://192.168.81.100:9000");

            FileSystem fs = FileSystem.get(conf);

            fs.delete(path, true);
            
            fs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void listFileInput()  throws Exception {
        Path path = new Path("/enem/out");

        Configuration conf = new Configuration();
        conf.set("hadoop.job.ugi", "hduser");
        conf.set("fs.default.name", "hdfs://192.168.81.100:9000");

        FileSystem fs = FileSystem.get(conf);

        FileStatus [] files = fs.listStatus(path);
        
        for(FileStatus file : files) {
            System.out.println(file.getPath().getName());
        }

        fs.close();
    }
    
    public void testReadFile() {
        try {
            Path path = new Path("/enem/out/part-00000");

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
    
    public int executeMapReduce() throws Exception {
        
        JobConf conf = new JobConf(Enem.class);
        conf.setJobName("EnemMedia");
        conf.set("hadoop.job.ugi", "hduser");
        conf.set("fs.default.name", "hdfs://192.168.81.100:9000");
        conf.set("mapred.job.tracker", "192.168.81.100:9001");
        
        long milliSeconds = 1000*60*60;
        conf.setLong("mapred.task.timeout", milliSeconds);
        
        conf.setJar("D://HadoopTests-1.0-SNAPSHOT.jar");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(FloatWritable.class);
        
        conf.setMapperClass(MediaEnemMapper.class);
        conf.setCombinerClass(MediaEnemReducer.class);
        conf.setReducerClass(MediaEnemReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path("/enem/in"));
        FileOutputFormat.setOutputPath(conf, new Path("/enem/out"));

        JobClient.runJob(conf);
        return 0;
    }
}
