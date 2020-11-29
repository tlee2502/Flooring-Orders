package com.tom.flooringorder.dao;

import com.tom.flooringorder.dto.StateTax;
import java.util.Map;


public interface StateTaxDao {
    Map<String, StateTax> getStateTaxes() throws DataPersistenceException;
}
