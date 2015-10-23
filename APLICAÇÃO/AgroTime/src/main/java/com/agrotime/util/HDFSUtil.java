package com.agrotime.util;

import java.security.PrivilegedExceptionAction;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

public class HDFSUtil {
    public void removeDirectory(final String diretorioHDFS) throws Exception {
        
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hduser");
        
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
}
