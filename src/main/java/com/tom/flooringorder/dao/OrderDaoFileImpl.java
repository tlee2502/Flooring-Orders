package com.tom.flooringorder.dao;

import com.tom.flooringorder.dto.Order;
import com.tom.flooringorder.dto.Product;
import com.tom.flooringorder.dto.StateTax;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class OrderDaoFileImpl implements OrderDao {

    private final String DELIMITER = ",";
    private final String FILE_NAME_START;
    private final String PREV_ORDER;
    Map<Integer, Order> orderMap = new TreeMap<>();
    ProductDao productDao;
    StateTaxDao stateDao;
    
    public OrderDaoFileImpl(ProductDao productDao, StateTaxDao stateDao) {
        this.productDao = productDao;
        this.stateDao = stateDao;
        this.FILE_NAME_START = "Orders/Order_";
        this.PREV_ORDER = "Orders/previousOrderNumber.txt";
    }
    
    public OrderDaoFileImpl(String orderFileStart, String previousOrderFile, ProductDao productDao, StateTaxDao stateDao) {
        this.FILE_NAME_START = orderFileStart;
        this.PREV_ORDER = previousOrderFile;
        this.productDao = productDao;
        this.stateDao = stateDao;
    }

    @Override
    public int addOrder(Order newOrder) throws DataPersistenceException {
        readOrders(newOrder.getDate());
        int newOrderNumber = getPreviousOrderNumber() + 1;
        newOrder.setOrderNumber(newOrderNumber);
        orderMap.put(newOrderNumber, newOrder);
        setPreviousOrderNumber(newOrderNumber);
        writeOrders(newOrder.getDate());
        
        return newOrderNumber;
    }

    @Override
    public Order getOrder(String orderDate, int orderNumber) throws DataPersistenceException {
        readOrders(orderDate);
        Order order = orderMap.get(orderNumber);
        writeOrders(orderDate);
        return order;
    }

    @Override
    public void replaceOrder(String orderDate, Order orderToEdit, Order editedOrder) throws DataPersistenceException {
        readOrders(orderDate);
        orderMap.replace(orderToEdit.getOrderNumber(), editedOrder);
        writeOrders(orderDate);
    }

    @Override
    public void removeOrder(String orderDate, int orderNumber) throws DataPersistenceException {
        boolean orderExists = readOrders(orderDate);
        if (orderExists) {
            orderMap.remove(orderNumber);
            writeOrders(orderDate);
        }
    }

    @Override
    public List<Order> listOrders(String date) throws DataPersistenceException {
        boolean ordersExist = readOrders(date);
        List<Order> orderList = new ArrayList<>(orderMap.values());
        if (ordersExist) {
            writeOrders(date);
        }
        return orderList;
    }

    private int getPreviousOrderNumber() throws DataPersistenceException {
        Scanner sc;
        int previousOrderNumber = 0;
        try {
            sc = new Scanner(new BufferedReader(new FileReader(PREV_ORDER)));
            previousOrderNumber = sc.nextInt();
            sc.close();
        } catch (FileNotFoundException e) {
            throw new DataPersistenceException("No previous order number file found", e);
        }
        return previousOrderNumber;
    }
    
    private void setPreviousOrderNumber(int prevOrderNumber) throws DataPersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(PREV_ORDER));
            out.println(prevOrderNumber);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new DataPersistenceException("No previous order number file found", e);
        }
    }

    private String marshallData(Order order) {
        String orderAsString = order.getOrderNumber() + DELIMITER + order.getCustomerName()
                + DELIMITER + order.getState().getStateInitial() + DELIMITER
                + order.getState().getTaxRate().toString() + DELIMITER
                + order.getProduct().getProductType() + DELIMITER + order.getArea().toString()
                + DELIMITER + order.getProduct().getMaterialCostPerSquareFoot().toString()
                + DELIMITER + order.getProduct().getLaborCostPerSquareFoot().toString()
                + DELIMITER + order.getMaterialCost().toString() + DELIMITER
                + order.getLaborCost().toString() + DELIMITER + order.getTax().toString()
                + DELIMITER + order.getTotal().toString();
        return orderAsString;
    }

    private Order unmarshallData(String orderAsString) throws DataPersistenceException {
        Map<String, Product> productMap = new TreeMap<>();
        productDao.getProducts().stream()
                .forEach(i -> productMap.put(i.getProductType(), i));

        Map<String, StateTax> stateMap = stateDao.getStateTaxes();

        String[] parts = orderAsString.split(DELIMITER);

        int orderNumber = Integer.parseInt(parts[0]);
        Order outputOrder = new Order(parts[1], stateMap.get(parts[2]), productMap.get(parts[4]), parts[5]);
        outputOrder.setOrderNumber(orderNumber);
        return outputOrder;
    }

    private boolean readOrders(String date) throws DataPersistenceException {
        boolean areOrders = true;
        Scanner sc;
        try {
            sc = new Scanner(new BufferedReader(new FileReader(FILE_NAME_START + date + ".txt")));
            String currentLine;
            Order currentOrder;
            sc.nextLine();
            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentOrder = unmarshallData(currentLine);
                currentOrder.setDate(date);
                orderMap.put(currentOrder.getOrderNumber(), currentOrder);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            areOrders = false;
        }
        return areOrders;
    }

    private void writeOrders(String date) throws DataPersistenceException {
        PrintWriter out;
        File outputFile = new File(FILE_NAME_START + date + ".txt");
        boolean mapIsEmpty = orderMap.isEmpty();
        if (mapIsEmpty) {
            outputFile.delete();
        } else {
            try {
                out = new PrintWriter(new FileWriter(outputFile));
                String orderAsString;
                String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,"
                        + "Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,"
                        + "LaborCost,Tax,Total";
                out.println(header);
                for (Order currentOrder : orderMap.values()) {
                    orderAsString = marshallData(currentOrder);
                    out.println(orderAsString);
                    out.flush();
                }
                orderMap = new TreeMap<>();
                out.close();
            } catch (IOException e) {
                throw new DataPersistenceException("File not found", e);
            }
        }
    }

}
