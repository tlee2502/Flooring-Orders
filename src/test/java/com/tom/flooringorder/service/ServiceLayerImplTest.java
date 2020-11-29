package com.tom.flooringorder.service;

import com.tom.flooringorder.dao.DataPersistenceException;
import com.tom.flooringorder.dao.ProductDaoFileImpl;
import com.tom.flooringorder.dao.StateTaxDaoFileImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.tom.flooringorder.dao.ExportDao;
import com.tom.flooringorder.dao.OrderDao;
import com.tom.flooringorder.dao.ProductDao;
import com.tom.flooringorder.dao.StateTaxDao;

public class ServiceLayerImplTest {
    ProductDao productDao = new ProductDaoFileImpl();
    StateTaxDao statesDao = new StateTaxDaoFileImpl();
    ExportDao exportDao = new ExportDaoStubImpl();
    OrderDao orderDao = new OrderDaoStubImpl();
    ServiceLayer testService = new ServiceLayerImpl(exportDao, orderDao, productDao, statesDao);
    
    public ServiceLayerImplTest() {
    }

    @Test
    public void testExceptionInGetOrder() {
        try {
            testService.getOrder("01/01/2025", 0);
        } catch (InvalidOrderException e) {
            //do nothing
        } catch (DataPersistenceException x) {
            //do nothing
        }
    }
    
    @Test
    public void testExceptionInList() {
        try {
            testService.listOrders("01/01/2025");
            fail("Test should throw exception");
        } catch (InvalidOrderException e) {
            // test pass
        } catch (DataPersistenceException x) {
            
        }
    }    
}
