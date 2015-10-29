package com.agrotime.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivilegedExceptionAction;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

public class HDFSUtil {
    public void removeDirectory(final String diretorioHDFS) throws Exception {
        System.out.println("<<<<<---------------- Removendo Diretótio '"+diretorioHDFS+"' ---------------->>>>>");
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser(Propriedades.USER_HDFS);
        
        ugi.doAs(new PrivilegedExceptionAction<Void>() {
            public Void run() throws Exception {
                
                Path path = new Path(diretorioHDFS);

                Configuration conf = new Configuration();
                conf.set("hadoop.job.ugi", Propriedades.USER_HDFS);
                conf.set("fs.default.name", Propriedades.URL_HDFS);

                FileSystem fs = FileSystem.get(conf);

                fs.delete(path, true);

                fs.close();
                
                return null;

            }
        });
    }
    
    public String[] readLinesFile(final String caminho) throws Exception {
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser(Propriedades.USER_HDFS);
        
        return ugi.doAs(new PrivilegedExceptionAction<String[]>() {
            public String[] run() throws Exception {
                
                Path path = new Path(caminho);
                
                Configuration conf = new Configuration();
                conf.set("hadoop.job.ugi", Propriedades.USER_HDFS);
                conf.set("fs.default.name", Propriedades.URL_HDFS);

                FileSystem fs = FileSystem.get(conf);

                InputStream input = fs.open(path);

                String[] result = getStringFromInputStream(input).split("\n");

                fs.close();
                
                return result;
            }
        });
    }

    private String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                    br = new BufferedReader(new InputStreamReader(is));
                    while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                    }

            } catch (IOException e) {
                    e.printStackTrace();
            } finally {
                    if (br != null) {
                            try {
                                    br.close();
                            } catch (IOException e) {
                                    e.printStackTrace();
                            }
                    }
            }

            return sb.toString();

    }
}
