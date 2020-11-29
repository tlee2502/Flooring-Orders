package com.tom.flooringorder.dao;

import com.tom.flooringorder.dto.StateTax;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StateTaxDaoFileImplTest {
    
    StateTaxDao testDao = new StateTaxDaoStubImpl();
    public StateTaxDaoFileImplTest() {
    }

    @Test
    public void testGetStateTaxes() throws DataPersistenceException {
        StateTax al = new StateTax("AL", "Alabama", new BigDecimal("2.50"));
        StateTax az = new StateTax("AZ", "Arizona", new BigDecimal("4.00"));
        StateTax ar = new StateTax("AR", "Arkansas", new BigDecimal("6.30"));
        Map<String, StateTax> testMap = new TreeMap<>();
        testMap.clear();
        testMap.put(al.getStateInitial(), al);
        testMap.put(az.getStateInitial(), az);
        testMap.put(ar.getStateInitial(), ar);
        
        Map<String, StateTax> actualMap = testDao.getStateTaxes();
        
        assertEquals(testMap.keySet(), actualMap.keySet());
        assertEquals(3, actualMap.size());
        assertEquals(3, testMap.size());
        assertEquals(testMap, actualMap);
    }
    
}
