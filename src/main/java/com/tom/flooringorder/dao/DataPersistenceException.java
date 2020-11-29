package com.tom.flooringorder.dao;

public class DataPersistenceException extends Exception {
    
    public DataPersistenceException(String msg) {
        super(msg);
    }
    
    public DataPersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
