package com.tom.flooringorder.service;

import com.tom.flooringorder.dao.DataPersistenceException;
import com.tom.flooringorder.dto.Order;
import com.tom.flooringorder.dto.Product;
import com.tom.flooringorder.dto.StateTax;
import java.util.List;
import java.util.Map;

public interface ServiceLayer {
    void addOrder(Order newOrder) throws DataPersistenceException;
    Order getOrder(String orderDate, int orderNumber) throws DataPersistenceException, InvalidOrderException;
    void replaceOrder(String orderDate, Order orderToEdit, Order editedOrder) throws DataPersistenceException;
    void removeOrder(String orderDate, int orderNumber) throws DataPersistenceException;
    void exportAll() throws DataPersistenceException;
    List<Product> getProductList() throws DataPersistenceException;
    Map<String, StateTax> getStateTaxes() throws DataPersistenceException;
    List<Order> listOrders(String date) throws DataPersistenceException, InvalidOrderException;
}
