
package com.importdata.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivilegedExceptionAction;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.UserGroupInformation;

/**
 *
 * @author jrcsilva
 */
public class Controle {
    public String transferDataForHDFS(final String diretorio, 
                                         final String nomeArquivo, 
                                         final String caminhoNoHDFS) throws Exception {
        
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hduser");
        
        ugi.doAs(new PrivilegedExceptionAction<Void>() {
                public Void run() throws Exception {
                   
                    String path = diretorio.replace("\\", "//");
        
                    InputStream in = new FileInputStream(new File(path));

                    Configuration conf = new Configuration();
                    conf.set("hadoop.job.ugi", "hduser");
                    conf.set("fs.default.name", "hdfs://192.168.81.100:9000");

                    FileSystem fs = FileSystem.get(conf);

                    FSDataOutputStream out = fs.create(new Path(caminhoNoHDFS+nomeArquivo));

                    IOUtils.copyBytes(in, out, conf); 

                    fs.close();
                    
                    return null;
                }
            });
        
        return "Transferência concluida com sucesso";
    }
    
    public String listFilesAndDirectoriesByDirectory(final String diretorioHDFS) throws Exception {
        
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hduser");
        
        return ugi.doAs(new PrivilegedExceptionAction<String>() {
            public String run() throws Exception {
                String result = "";
                Path path = new Path(diretorioHDFS);
                System.out.println("Testando 123");
                Configuration conf = new Configuration();
                conf.set("hadoop.job.ugi", "hduser");
                conf.set("fs.default.name", "hdfs://192.168.81.100:9000");

                FileSystem fs = FileSystem.get(conf);
                
                FileStatus[] files = fs.listStatus(path);

                for(FileStatus file : files) {
                    result += file.getPath().getName()+"\n";
                }

                fs.close();

                return result;
            }
        });
    }
}