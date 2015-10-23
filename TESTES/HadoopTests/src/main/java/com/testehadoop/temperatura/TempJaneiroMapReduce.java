package com.testehadoop.temperatura;

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

import com.testehadoop.hadooptests.Enem;

public class TempJaneiroMapReduce {
	
    public static void main(String[] args) throws Exception {
        
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hduser"); 
        
        try {
            ugi.doAs(new PrivilegedExceptionAction<Void>() {
                public Void run() throws Exception {
                   
                    TempJaneiroMapReduce tmr = new TempJaneiroMapReduce();
                    tmr.executeMapReduce();
                    return null; 
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	public int executeMapReduce() throws Exception {
        
        JobConf conf = new JobConf(Enem.class);
        conf.setJobName("Temperatura Diaria Janeiro");
        conf.set("hadoop.job.ugi", "hduser");
        conf.set("fs.default.name", "hdfs://192.168.81.100:9000");
        conf.set("mapred.job.tracker", "192.168.81.100:9001");
        
        long milliSeconds = 1000*60*60;
        conf.setLong("mapred.task.timeout", milliSeconds);
        
        conf.setJar("D://HadoopTests-1.0-SNAPSHOT.jar");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(FloatWritable.class);
        
        conf.setMapperClass(TempJaneiroMapper.class);
        conf.setCombinerClass(TempJaneiroReduce.class);
        conf.setReducerClass(TempJaneiroReduce.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path("/agrotime/input"));
        FileOutputFormat.setOutputPath(conf, new Path("/agrotime/temperaturadiaria/janeiro"));

        JobClient.runJob(conf);
        return 0;
    }
}