/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.importdata.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PrivilegedExceptionAction;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.UserGroupInformation;

/**
 *
 * @author jrcsilva
 */
public class CarregaDadosProtim {

    public static void main(String[] args) throws Exception {
        
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hduser"); 
        
        try {
            ugi.doAs(new PrivilegedExceptionAction<Void>() {
                public Void run() throws Exception {
                   
                    CarregaDadosProtim c = new CarregaDadosProtim();
                    c.transferDataFromHDFS(1996, 2015, "D:\\Repositorios Git\\tcc\\DADOS METEREOLOGICOS PROTIM PLATAFORMAS");
                    return null; 
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void transferDataFromHDFS(Integer anoInicio, Integer anoFim, String diretorio)  throws Exception {
        
        Configuration conf = new Configuration();
        conf.set("hadoop.job.ugi", "hduser");
        conf.set("fs.default.name", "hdfs://192.168.81.100:9000");

        FileSystem fs = FileSystem.get(conf);
        
        for(int i = anoInicio; i <= anoFim; i++) {
            
            System.out.println(diretorio+"\\"+i+".txt");
            
            InputStream in = new FileInputStream(new File(diretorio+"\\"+i+".txt"));

            FSDataOutputStream out = fs.create(new Path("/agrotime/input/"+i+".txt"));

            IOUtils.copyBytes(in, out, conf); 
        }

        fs.close();
    }
    
}
