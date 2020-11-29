package com.tom.flooringorder.dao;

import com.tom.flooringorder.dto.StateTax;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class StateTaxDaoStubImpl implements StateTaxDao {

    @Override
    public Map<String, StateTax> getStateTaxes() throws DataPersistenceException {
        StateTax al = new StateTax("AL", "Alabama", new BigDecimal("2.50"));
        StateTax az = new StateTax("AZ", "Arizona", new BigDecimal("4.00"));
        StateTax ar = new StateTax("AR", "Arkansas", new BigDecimal("6.30"));
        Map<String, StateTax> taxMap = new TreeMap<>();
        taxMap.clear();
        taxMap.put(al.getStateInitial(), al);
        taxMap.put(az.getStateInitial(), az);
        taxMap.put(ar.getStateInitial(), ar);
        
        return taxMap;
    }

}
