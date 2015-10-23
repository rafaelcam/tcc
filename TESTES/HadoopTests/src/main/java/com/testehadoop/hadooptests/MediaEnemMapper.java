/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testehadoop.hadooptests;

/**
 *
 * @author JRafael
 */
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class MediaEnemMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, FloatWritable> {
    
    @Override
    public void map(LongWritable key, Text value, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {
        String linha = value.toString();
        String ano = linha.substring(13 - 1, 13 + 3).trim();
        String uf = linha.substring(178 - 1, 178 + 1).trim();
        if ((ano.equals("2011") || ano.equals("2010"))) {
            float mediaNota = getMedia(linha);
            if (mediaNota != -1f) {
                output.collect(new Text(uf + "/" + ano), new FloatWritable(mediaNota));
            }
        }
    }
    
    private float getMedia(final String linha) {
        if (linha.trim().length() < 563 + 8) {
            return -1f;
        }
        String presenteCN = "";
        String presenteCH = "";
        String presenteLC = "";
        String presenteMT = "";

        String notaCN = "";
        String notaCH = "";
        String notaLC = "";
        String notaMT = "";

        presenteCN = linha.substring(533 - 1, 533);
        presenteCH = linha.substring(534 - 1, 534);
        presenteLC = linha.substring(535 - 1, 535);
        presenteMT = linha.substring(536 - 1, 536);

        notaCN = linha.substring(537 - 1, 537 + 8);
        notaCH = linha.substring(546 - 1, 546 + 8);
        notaLC = linha.substring(555 - 1, 555 + 8);
        notaMT = linha.substring(564 - 1, 564 + 8);

        BigDecimal media = BigDecimal.ZERO;
        if (presenteCN.equals("1") && presenteCH.equals("1")
                && presenteLC.equals("1") && presenteMT.equals("1")) {
            media = new BigDecimal(notaCN.trim()).add(
                    new BigDecimal(notaCH.trim()).add(new BigDecimal(notaLC
                            .trim()).add(new BigDecimal(notaMT.trim()))))
                    .divide(new BigDecimal(4));
            return media.floatValue();
        } else // não está presente nas provas
        {
            return -1;
        }
    }
}
