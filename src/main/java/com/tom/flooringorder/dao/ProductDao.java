package com.tom.flooringorder.dao;

import com.tom.flooringorder.dto.Product;
import java.util.List;

public interface ProductDao {
    
    List<Product> getProducts() throws DataPersistenceException;
}
