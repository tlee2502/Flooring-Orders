package com.tom.flooringorder.dao;

import com.tom.flooringorder.dto.Order;
import java.util.List;

public interface OrderDao {
    
    public int addOrder(Order newOrder) throws DataPersistenceException;
    public Order getOrder(String orderDate, int orderNumber) throws DataPersistenceException;
    public void replaceOrder(String orderDate, Order orderToEdit, Order editedOrder) throws DataPersistenceException;
    public void removeOrder(String orderDate, int orderNumber) throws DataPersistenceException;
    public List<Order> listOrders(String date) throws DataPersistenceException;
}
