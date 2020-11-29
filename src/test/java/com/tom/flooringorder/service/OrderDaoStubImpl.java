package com.tom.flooringorder.service;

import com.tom.flooringorder.dao.DataPersistenceException;
import com.tom.flooringorder.dto.Order;
import java.util.ArrayList;
import java.util.List;
import com.tom.flooringorder.dao.OrderDao;

public class OrderDaoStubImpl implements OrderDao {

    @Override
    public int addOrder(Order newOrder) throws DataPersistenceException {
        return 0;
    }

    @Override
    public Order getOrder(String orderDate, int orderNumber) throws DataPersistenceException {
        return null;
    }

    @Override
    public void replaceOrder(String orderDate, Order orderToEdit, Order editedOrder) throws DataPersistenceException {
        
    }

    @Override
    public void removeOrder(String orderDate, int orderNumber) throws DataPersistenceException {
        
    }

    @Override
    public List<Order> listOrders(String date) throws DataPersistenceException {
        List<Order> emptyList = new ArrayList<>();
        return emptyList;
    }
    
}
