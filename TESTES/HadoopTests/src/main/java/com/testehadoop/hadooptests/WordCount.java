/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testehadoop.hadooptests;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivilegedExceptionAction;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.security.UserGroupInformation;

public class WordCount {

    public static void main(String[] args) throws Exception {
        
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hduser"); 
        
        try {
            ugi.doAs(new PrivilegedExceptionAction<Void>() {
                public Void run() throws Exception {
                   
                    WordCount wc = new WordCount();
                    //wc.createFileInput();
                    //wc.listFileInput();
                    wc.testReadFile();
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
        String texto = "Testando a escrita do HDFS";

        InputStream in = new BufferedInputStream(new ByteArrayInputStream(texto.getBytes()));

        Configuration conf = new Configuration();
        conf.set("hadoop.job.ugi", "hduser");
        conf.set("fs.default.name", "hdfs://192.168.81.100:9000");

        FileSystem fs = FileSystem.get(conf);

        FSDataOutputStream out = fs.create(new Path("/wordcount/in/texto.txt"));

        IOUtils.copyBytes(in, out, conf); 
    }
    
        public void removeDiretorio() {
        try {
            Path path = new Path("/wordcount");

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
        Path path = new Path("/wordcount/out/_SUCCESS/_SUCCESS");

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
            Path path = new Path("/wordcount/out/part-00000");

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
    /*
    public void executeMapReduce()  throws Exception {
        JobConf conf = new JobConf(WordCount.class);
        conf.set("hadoop.job.ugi", "hduser");
        conf.set("fs.default.name", "hdfs://192.168.81.100:9000");
        conf.set("mapred.job.tracker", "192.168.81.100:9001");
        
        long milliSeconds = 1000*60*60;
        conf.setLong("mapred.task.timeout", milliSeconds);
        
        conf.setJar("D://HadoopTests-1.0-SNAPSHOT.jar");
        
        conf.setJobName("wordcount");
        
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);

        conf.setMapperClass(Map.class);
        conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path("/wordcount/in"));
        FileOutputFormat.setOutputPath(conf, new Path("/wordcount/out"));

        JobClient.runJob(conf);
    }
    */
        public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

        static enum Counters {
            INPUT_WORDS
        }

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        private boolean caseSensitive = true;
        private Set<String> patternsToSkip = new HashSet<String>();

        private long numRecords = 0;
        private String inputFile;

        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            String line = (caseSensitive) ? value.toString() : value.toString().toLowerCase();

            for (String pattern : patternsToSkip) {
                line = line.replaceAll(pattern, "");
            }

            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
                word.set(tokenizer.nextToken());
                output.collect(word, one);
                reporter.incrCounter(Counters.INPUT_WORDS, 1);
            }

            if ((++numRecords % 100) == 0) {
                reporter.setStatus("Finished processing " + numRecords + " records " + "from the input file: " + inputFile);
            }
        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            int sum = 0;
            while (values.hasNext()) {
                sum += values.next().get();
            }
            output.collect(key, new IntWritable(sum));
        }
    }

    public int executeMapReduce() throws Exception {
        JobConf conf = new JobConf(WordCount.class);
        conf.setJobName("wordcount");
        conf.set("hadoop.job.ugi", "hduser");
        conf.set("fs.default.name", "hdfs://192.168.81.100:9000");
        conf.set("mapred.job.tracker", "192.168.81.100:9001");
        
        long milliSeconds = 1000*60*60;
        conf.setLong("mapred.task.timeout", milliSeconds);
        
        conf.setJar("D://HadoopTests-1.0-SNAPSHOT.jar");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);

        conf.setMapperClass(Map.class);
        conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path("/wordcount/in"));
        FileOutputFormat.setOutputPath(conf, new Path("/wordcount/out"));

        JobClient.runJob(conf);
        return 0;
    }

}
