package com.tom.flooringorder.dao;

import com.tom.flooringorder.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoStubImpl implements ProductDao {

    @Override
    public List<Product> getProducts() throws DataPersistenceException {
        Product board = new Product("Board", new BigDecimal("1.25"), new BigDecimal("2.00"));
        Product foam = new Product("Foam", new BigDecimal("0.75"), new BigDecimal("0.50"));
        Product metal = new Product("Metal", new BigDecimal("10.00"), new BigDecimal("15.00"));
        
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.clear();
        expectedProducts.add(board);
        expectedProducts.add(foam);
        expectedProducts.add(metal);
        
        return expectedProducts;
    }
    
}
