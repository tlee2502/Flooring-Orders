package com.tom.flooringorder.dao;

import com.tom.flooringorder.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductDaoFileImpl implements ProductDao{
    
    private final String DELIMITER = ",";
    private final String PRODUCT_FILE;
    List<Product> productList;
    
    public ProductDaoFileImpl() {
        this.PRODUCT_FILE = "Data/Products.txt";
    }
    
    public ProductDaoFileImpl(String PRODUCT_FILE) {
        this.PRODUCT_FILE = PRODUCT_FILE;
    }

    @Override
    public List<Product> getProducts() throws DataPersistenceException {
        productList = new ArrayList<>();
        readProducts();
        return productList;
    }
    
    private void readProducts() throws DataPersistenceException {
        Scanner sc;
        try {
            sc = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
            String currentLine;
            Product currentProduct;
            sc.nextLine();
            while(sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentProduct = unmarshallData(currentLine);
                productList.add(currentProduct);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            throw new DataPersistenceException("Could not load product data into memory.", e);
        }
        
    }

    private Product unmarshallData(String productAsString) {
        String[] parts = productAsString.split(DELIMITER);
        String productType = parts[0];
        BigDecimal productCost = new BigDecimal(parts[1]);
        BigDecimal productLaborCost = new BigDecimal(parts[2]);
        Product productOut = new Product(productType, productCost, productLaborCost);
        return productOut;
    }
    
}
