package com.tom.flooringorder.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {
    
    final private Scanner sc = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public String readString(String msg) {
        System.out.println(msg);
        return sc.nextLine();
    }

    @Override
    public int readInt(String msg) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                String stringValue = this.readString(msg);
                num = Integer.parseInt(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    @Override
    public int readInt(String msg, int min, int max) {
        int result;
        do {
            result = readInt(msg);
        } while (result < min || result > max);
        return result;
    }

    @Override
    public BigDecimal readBigDecimal(String msg) {
        String inputAsString = this.readString(msg);
        BigDecimal input = null;
        try {
            input = new BigDecimal(inputAsString);
        } catch (NumberFormatException e) {
            this.print("Input error. Please try again");
        }
        return input;
    }

    @Override
    public LocalDate readDate(String msg) {
        boolean isValid = false;
        LocalDate result = null;
        do {
            String value = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                value = readString(msg);
                result = LocalDate.parse(value, formatter);
                isValid = true;
            } catch (DateTimeParseException ex) {
                this.print("The value " + value + " is not a valid date. (MM-DD-YYYY)");
            }
        } while (!isValid);
        return result;
    }

    @Override
    public LocalDate readDate(String msg, LocalDate min, LocalDate max) {
        boolean isValid = false;
        LocalDate result = null;
        do {
            result = readDate(msg);
            if (result.isAfter(min) && result.isBefore(max)) {
                isValid = true;
            } else {
                this.print("Please choose a valid date");
            }
        } while (!isValid);
        return result;
    }
    
}
