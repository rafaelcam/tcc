/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testehadoop.temperatura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author jrcsilva
 */
public class Util {
    public static String getStringFromInputStream(InputStream is) {

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
