
package com.importdata.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author jrcsilva
 */
public class Controle {
    public String transferExcelToMongoDB(String nomeBanco, String nomeCollection, String diretorio) throws IOException {
        MongoClient mongoClient = null;
        FileInputStream file = null;
        DBCollection collection = null;
        
        try {
            file = new FileInputStream(new File(diretorio));
            Integer index = 0, cellIndex = 0;
            List<String> listNameVars = new ArrayList<String>();

            mongoClient = new MongoClient();
            DB db = mongoClient.getDB(nomeBanco);
            collection = db.getCollection(nomeCollection);
            
            BasicDBObject document;

            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet folha = wb.getSheetAt(0);

            Iterator<Row> iterator = folha.iterator();
            while (iterator.hasNext()) {
                Row row = iterator.next();

                if(index == 0) {
                    Iterator<Cell> iteratorCelulas = row.cellIterator();
                    while (iteratorCelulas.hasNext()) {
                        Cell celula = iteratorCelulas.next();

                        switch (celula.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:
                                collection.drop();
                                return "A primeira linha da planilha não deve conter valores numéricos.";

                            case Cell.CELL_TYPE_STRING:
                                if(!listNameVars.contains(celula.getStringCellValue())) {
                                    listNameVars.add(celula.getStringCellValue());
                                } else {
                                    collection.drop();
                                    return "A primeira linha da planilha não deve conter células com valores repetidos.";
                                }

                                break;
                        }
                    }
                } else {
                    cellIndex = 0;
                    document = new BasicDBObject();
                    
                    Iterator<Cell> iteratorCelulas = row.cellIterator();
                    while (iteratorCelulas.hasNext()) {
                        Cell celula = iteratorCelulas.next();
                        
                        switch (celula.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:
                                document.put(listNameVars.get(cellIndex), celula.getNumericCellValue());
                                break;
                            case Cell.CELL_TYPE_STRING:
                                document.put(listNameVars.get(cellIndex), celula.getStringCellValue());
                                break;
                            default:
                                document.put(listNameVars.get(cellIndex), celula.getStringCellValue());
                                break;
                        }
                        cellIndex++;
                    }
                    if(!document.isEmpty()) {
                        
                        System.out.println("Documento: "+document);
                        collection.insert(document);
                    }
                }

                index++;
            }
            mongoClient.close();
            file.close();
            return "Os dados foram importados com sucesso para o Banco "+nomeBanco+".";
        } catch (Exception e) {
            if(collection != null) {
                collection.drop();
            }
            
            if(mongoClient != null) {
                mongoClient.close();
            }
            
            if(file != null) {
                file.close();
            }
            
            e.printStackTrace();
            return "Ocorreu um erro inesperado na gravação do Banco de Dados.";
        }
    }
}