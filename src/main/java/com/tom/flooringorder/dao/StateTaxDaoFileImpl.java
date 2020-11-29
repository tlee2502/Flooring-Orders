package com.tom.flooringorder.dao;

import com.tom.flooringorder.dto.StateTax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class StateTaxDaoFileImpl implements StateTaxDao {

    private final String DELIMITER = ",";
    private final String STATE_FILE;
    Map<String, StateTax> stateTaxMap;

    public StateTaxDaoFileImpl() {
        this.STATE_FILE = "Data/Taxes.txt";
    }

    public StateTaxDaoFileImpl(String STATE_FILE) {
        this.STATE_FILE = STATE_FILE;
    }

    @Override
    public Map<String, StateTax> getStateTaxes() throws DataPersistenceException {
        stateTaxMap = new TreeMap<>();
        readStates();
        return stateTaxMap;
        
    }

    private void readStates() throws DataPersistenceException {
        Scanner sc;
        try {
            sc = new Scanner(new BufferedReader(new FileReader(STATE_FILE)));
            String currentLine;
            StateTax currentState;
            sc.nextLine();
            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentState = unmarshallData(currentLine);
                stateTaxMap.put(currentState.getStateInitial(), currentState);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            throw new DataPersistenceException("Could not load state tax data into memory", e);
        }
    }

    private StateTax unmarshallData(String stateTaxAsString) {
        String[] parts = stateTaxAsString.split(DELIMITER);
        return new StateTax(parts[0], parts[1], new BigDecimal(parts[2]));
    }

}