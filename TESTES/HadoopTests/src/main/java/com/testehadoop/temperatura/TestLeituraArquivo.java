package com.testehadoop.temperatura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestLeituraArquivo {

	public static void main(String[] args) {
		try {
			Map<String, List<Float>> result = new HashMap<String, List<Float>>();
			String key;
			Float value;
			
			FileReader arq = new FileReader(new File("D://Nomes.txt"));
			BufferedReader lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			while (linha != null) {
				String[] atributos = linha.split(",");
				String[] datetime = atributos[0].split(" ");
				String[] mes = datetime[0].split("/");
				
				System.out.println("data: "+datetime[0].substring(0, 6));
				
				key = datetime[0];
				value = Float.parseFloat(atributos[5]);
				
				if(result.containsKey(key)) {
					result.get(key).add(value);
				} else {
					result.put(key, new ArrayList<Float>());
					result.get(key).add(value);
				}
				linha = lerArq.readLine();
			}
			
			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		System.out.println();

	}

}
