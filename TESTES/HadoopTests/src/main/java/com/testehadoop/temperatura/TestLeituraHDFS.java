package com.testehadoop.temperatura;

import java.io.InputStream;
import java.security.PrivilegedExceptionAction;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import org.apache.hadoop.security.UserGroupInformation;

public class TestLeituraHDFS {

	public static void main(String[] args) throws Exception{
            UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hduser");
        
            ugi.doAs(new PrivilegedExceptionAction<Void>() {
                public Void run() throws Exception {
                    
                    Path path = new Path("/agrotime/temperaturadiaria/janeiro/part-00000");

                    Configuration conf = new Configuration();
                    conf.set("hadoop.job.ugi", "hduser");
                    conf.set("fs.default.name", "hdfs://192.168.81.100:9000");

                    FileSystem fs = FileSystem.get(conf);

                    InputStream input = fs.open(path);

                    //IOUtils.copyBytes(input, System.out, 4096);
                    
                    String[] result = Util.getStringFromInputStream(input).split("\n");
                    
                    for(String var : result) {
                        String date = var.substring(0, 5);
                        String temperatura = var.substring(6);

                        System.out.println(date+"==>"+temperatura);
                    }
                    
                    System.out.println("Done");

                    fs.close();

                    return null;

                }
            });
	}

}
