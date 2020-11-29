package com.tom.flooringorder.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ExportDaoFileImpl implements ExportDao {
    
    private final String HEADER = "OrderNumber,CustomerName,State,TaxRate,ProductType,"
            + "Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,"
            + "LaborCost,Tax,Total,Date";
    private final String EXPORT_FILE = "Backup/DataExport.txt";
    private final String ORDER_FOLDER = "Orders";
    File directory = new File(ORDER_FOLDER);
    File[] directoryFiles = directory.listFiles();

    @Override
    public void exportAll() throws DataPersistenceException{
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(EXPORT_FILE));
            out.println(HEADER);
            for (File orderFile : directoryFiles) {
                String fileName = orderFile.getName();
                
                if (fileName.equals("previousOrderNumber.txt")) {
                    continue;
                }
                Scanner sc;
                String fileDate = getFileDate(fileName);
                try {
                    sc = new Scanner(new BufferedReader(new FileReader(orderFile)));
                    sc.nextLine(); // skip header line
                    while (sc.hasNextLine()) {
                        out.println(sc.nextLine() + "," + fileDate);
                        out.flush();
                    }
                    sc.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Could not find file.");
                }
            }
            out.close();
        } catch (IOException e) {
            throw new DataPersistenceException("No backup file found", e);
        }
    }

    private String getFileDate(String fileName) {
        // file name to be of the form Order_MMDDYYYY
        // extract the date MMDDYYYY:
        String fileDateWithoutSlash = fileName.substring(6, 14);
        String fileDate = fileDateWithoutSlash.substring(0,2) + "/" + 
                fileDateWithoutSlash.substring(2,4) + "/" +
                fileDateWithoutSlash.substring(4,8);
        return fileDate;
    }
    
}