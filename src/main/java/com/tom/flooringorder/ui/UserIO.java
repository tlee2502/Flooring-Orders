package com.tom.flooringorder.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UserIO {
    
    void print(String msg);
    String readString(String msg);
    int readInt(String msg);
    int readInt(String msg, int min, int max);
    BigDecimal readBigDecimal(String msg);
    LocalDate readDate(String msg);
    LocalDate readDate(String msg, LocalDate min, LocalDate max);
}