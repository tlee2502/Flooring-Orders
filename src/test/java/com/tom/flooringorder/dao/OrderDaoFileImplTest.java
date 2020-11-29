package com.tom.flooringorder.dao;

import com.tom.flooringorder.dto.Order;
import com.tom.flooringorder.dto.Product;
import com.tom.flooringorder.dto.StateTax;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderDaoFileImplTest {
    
    private final String testFilenameStart = "TestFiles/Orders/Order_";
    private final String testPrevOrderNumberFile = "TestFiles/Orders/previousordernum.txt";
    ProductDao productDao = new ProductDaoStubImpl();
    StateTaxDao stateDao = new StateTaxDaoStubImpl();
    OrderDao testOrderDao = new OrderDaoFileImpl(testFilenameStart, testPrevOrderNumberFile, productDao, stateDao);
    
    public OrderDaoFileImplTest() {
    }

    @Test
    public void testCreateGetOrder() throws DataPersistenceException {
        Product board = productDao.getProducts().get(0);
        StateTax alabama = stateDao.getStateTaxes().get("AL");
        Order thisOrder = new Order("Test name", alabama, board, "100");
        String testDate = "01012025";
        thisOrder.setDate(testDate);
        
        int thisOrderNumber = testOrderDao.addOrder(thisOrder);
        Order returnedOrder = testOrderDao.getOrder(testDate, thisOrderNumber);
        
        assertNull(testOrderDao.getOrder("01/01/2021", thisOrderNumber), "should be null");
        assertNull(testOrderDao.getOrder("01/01/2025", 0), "No order number 0 for this date");
        assertEquals(thisOrder, returnedOrder, "Order returned should be same");       
    }
    
    
    @Test
    public void testReplaceOrder() throws DataPersistenceException {
        Product board = productDao.getProducts().get(0);
        StateTax alabama = stateDao.getStateTaxes().get("AL");
        Order thisOrder = new Order("Test name", alabama, board, "100");
        String testDate = "01/01/2025";
        thisOrder.setDate(testDate);
        
        Product foam = productDao.getProducts().get(1);
        StateTax arizona = stateDao.getStateTaxes().get("AZ");
        Order myOrder = new Order("Tester", arizona, foam, "200");
        myOrder.setDate(testDate);
        
        int thisOrderNumber = testOrderDao.addOrder(thisOrder);
        myOrder.setOrderNumber(thisOrderNumber);
        testOrderDao.replaceOrder(testDate, thisOrder, myOrder);
        Order actualOrder = testOrderDao.getOrder(testDate, thisOrderNumber);
        
        assertEquals(myOrder, actualOrder, "Order should be same");
        assertNotSame(thisOrder, actualOrder, "Order has been edited");
    }
    
    @Test
    public void testListRemoveOrders() throws DataPersistenceException {
        String testDate = "02022022";
        
        Product board = productDao.getProducts().get(0);
        StateTax alabama = stateDao.getStateTaxes().get("AL");
        Order order1 = new Order("Test name", alabama, board, "100");        
        order1.setDate(testDate);
        
        Product foam = productDao.getProducts().get(1);
        StateTax arizona = stateDao.getStateTaxes().get("AZ");
        Order order2 = new Order("Tester", arizona, foam, "200");
        order2.setDate(testDate);
        
        testOrderDao.addOrder(order1);
        testOrderDao.addOrder(order2);
        List<Order> orders = testOrderDao.listOrders(testDate);
        
        assertNotNull(orders, "Check not null");
        assertEquals(2, orders.size(), "only 2 elements in list");
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }
}
