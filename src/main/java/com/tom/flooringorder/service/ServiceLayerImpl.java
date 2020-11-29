package com.tom.flooringorder.service;

import com.tom.flooringorder.dao.DataPersistenceException;
import com.tom.flooringorder.dto.Order;
import com.tom.flooringorder.dto.Product;
import com.tom.flooringorder.dto.StateTax;
import java.util.List;
import java.util.Map;
import com.tom.flooringorder.dao.ExportDao;
import com.tom.flooringorder.dao.OrderDao;
import com.tom.flooringorder.dao.ProductDao;
import com.tom.flooringorder.dao.StateTaxDao;

public class ServiceLayerImpl implements ServiceLayer {
    
    private final ExportDao exportDao;
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final StateTaxDao stateDao;
    
    public ServiceLayerImpl(ExportDao exportDao, OrderDao orderDao, ProductDao productDao, StateTaxDao stateDao) {
        this.exportDao = exportDao;
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.stateDao = stateDao;
    }

    @Override
    public void addOrder(Order newOrder) throws DataPersistenceException {
        int orderNumber = orderDao.addOrder(newOrder);
    }

    @Override
    public Order getOrder(String orderDate, int orderNumber) throws DataPersistenceException, InvalidOrderException {
        Order order = orderDao.getOrder(orderDate, orderNumber);
        if (order == null) {
            throw new InvalidOrderException("Order does not exist.");
        } else {
            return order;
        }
    }

    @Override
    public void replaceOrder(String orderDate, Order orderToEdit, Order editedOrder) throws DataPersistenceException {
        orderDao.replaceOrder(orderDate, orderToEdit, editedOrder);
    }

    @Override
    public void removeOrder(String orderDate, int orderNumber) throws DataPersistenceException {
        orderDao.removeOrder(orderDate, orderNumber);
    }

    @Override
    public void exportAll() throws DataPersistenceException {
        exportDao.exportAll();
    }

    @Override
    public List<Product> getProductList() throws DataPersistenceException {
        return productDao.getProducts();
    }

    @Override
    public Map<String, StateTax> getStateTaxes() throws DataPersistenceException {
        return stateDao.getStateTaxes();
    }

    @Override
    public List<Order> listOrders(String date) throws DataPersistenceException, InvalidOrderException {
        List<Order> orderList = orderDao.listOrders(date);
        if (orderList.isEmpty()) {
            throw new InvalidOrderException("No orders found for given date.");
        } else {
            return orderList;
        }
    }
}