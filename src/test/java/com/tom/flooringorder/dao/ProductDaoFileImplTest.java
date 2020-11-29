package com.tom.flooringorder.dao;

import com.tom.flooringorder.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductDaoFileImplTest {
    
    ProductDao testPDao = new ProductDaoStubImpl();
    
    public ProductDaoFileImplTest() {
    }

    @Test
    public void testGetProducts() throws DataPersistenceException {
        
        Product board = new Product("Board", new BigDecimal("1.25"), new BigDecimal("2.00"));
        Product foam = new Product("Foam", new BigDecimal("0.75"), new BigDecimal("0.50"));
        Product metal = new Product("Metal", new BigDecimal("10.00"), new BigDecimal("15.00"));
        List<Product> testProducts = new ArrayList<>();
        testProducts.clear();
        testProducts.add(board);
        testProducts.add(foam);
        testProducts.add(metal);
        
        List<Product> actualProducts = testPDao.getProducts();
        
        assertEquals(testProducts, actualProducts);
        
    }
    
}
