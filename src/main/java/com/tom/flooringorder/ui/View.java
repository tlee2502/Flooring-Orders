package com.tom.flooringorder.ui;

import com.tom.flooringorder.dto.Order;
import com.tom.flooringorder.dto.Product;
import com.tom.flooringorder.dto.StateTax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

public class View {

    private final UserIO io;

    public View(UserIO io) {
        this.io = io;
    }

    public void displayWelcomeMessage() {
        io.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        io.print("~~~ Tom's Flooring Mastery ~~~\n");
    }

    public int displayMainMenuGetSelection() {
        io.print("~ FLOORING PROGRAM MAIN MENU");
        io.print("~ 1. Display Orders");
        io.print("~ 2. Add an order");
        io.print("~ 3. Edit an order");
        io.print("~ 4. Remove an order");
        io.print("~ 5. Export all data");
        io.print("~ 6. Quit");
        return io.readInt("Please select an option.", 1, 6);
    }

    public void displayOrdersMessage() {
        io.print("~~~ Display orders ~~~");
    }

    public void enterToContinue() {
        io.readString("Hit enter to continue.");
    }

    public String getFutureDateInputFromUser() {
        String dateString = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        boolean invalidInput = true;
        while (invalidInput) {
            dateString = io.readString("Please enter date (MM/dd/yyyy):");
            try {
                LocalDate date = LocalDate.parse(dateString, formatter);
                if (date.compareTo(LocalDate.now()) >= 0) {
                    invalidInput = false;
                } else {
                    io.print("Must enter future date.");
                }
            } catch (DateTimeParseException e) {
                io.print("Invalid input.");
            }
        }
        return dateRemoveSlashes(dateString);
    }
    
    public String getDateFromUser() {
        String dateAsString = "";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        boolean invalidInput = true;
        while (invalidInput) {
            dateAsString = io.readString("Enter date: (MM/dd/yyyy): ");
            try {
                LocalDate date = LocalDate.parse(dateAsString, fmt);
                invalidInput = false;
            } catch (DateTimeParseException e) {
                io.print("Invalid input.");
            }
        }        
        return dateRemoveSlashes(dateAsString);
    }

    private String dateWithSlashes(String dateString) {
        return dateString.substring(0, 2) + "/" + dateString.substring(2, 4) + "/" + dateString.substring(4, 8);
    }

    private String dateRemoveSlashes(String dateString) {
        return dateString.substring(0, 2) + dateString.substring(3, 5) + dateString.substring(6, 10);
    }

    public void displayNoOrdersForDate() {
        io.print("No orders for this date!\n");
    }

    public void addNewOrderBanner() {
        io.print("~~~ Add new order ~~~");
    }

    public void displayOrderInfo(Order order) {
        io.print("~~~ Order Information ~~~");
        if (order.getOrderNumber() != 0) {
            io.print("Order Number: " + order.getOrderNumber());
        }
        io.print("Date:          " + dateWithSlashes(order.getDate()));
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State:         " + order.getState().getStateName());
        io.print("Product:       " + order.getProduct().getProductType());
        io.print("Area:          " + order.getArea().toString());
        io.print("Total Cost :   $" + order.getTotal().setScale(2, RoundingMode.HALF_UP) + "\n");
    }

    public Order getNewOrder(List<Product> productList, Map<String, StateTax> stateList) {
        String date = getFutureDateInputFromUser();
        String name = getCustomerNameFromUser();
        StateTax state = getStateChoice(stateList);
        Product product = getProductChoice(productList);
        String area = getAreaFromUser();
        Order newOrder = new Order(name, state, product, area);
        newOrder.setDate(date);
        return newOrder;
    }

    private String getCustomerNameFromUser() {
        boolean invalidInput = true;
        String input = "";
        while (invalidInput) {
            input = io.readString("Enter customer name (allowed to contain [a-z][0-9] as well as '.' and ','): ");
            // check if input is allowed:
            if (input.matches("[\\w\\., ]+")) {
                invalidInput = false;
            } else {
                io.print("Invalid input.");
            }
        }
        return input.replaceAll(",", "");
    }

    private StateTax getStateChoice(Map<String, StateTax> stateTaxes) {
        boolean invalidState = true;
        StateTax state = null;
        while (invalidState) {
            String stateInitials = io.readString("Enter state initials: ");
            if (stateTaxes.keySet().contains(stateInitials)) {
                state = stateTaxes.get(stateInitials);
                invalidState = false;
            } else {
                io.print("We do not conduct business in this state.");
            }
        }
        return state;
    }

    private Product getProductChoice(List<Product> productList) {
        io.print("Please choose a product: ");
        int count = 1;
        for (Product product : productList) {
            io.print("(" + count + ") " + product.getProductType());
            count++;
        }
        int productIndex = io.readInt("Enter choice: ", 1, count);
        return productList.get(productIndex - 1);
    }

    private String getAreaFromUser() {
        int areaInt = io.readInt("Enter area (100 sq feet minimum): ", 100, 2147483647);
        String areaString = ("" + areaInt + "");
        return areaString;
    }

    public boolean getConfirmationFromUser(String operation) {
        int confirmation = io.readInt("Are you sure you want to " + operation + " this order? Enter 1 for Yes, 0 For No.", 0, 1);
        return (confirmation == 1);
    }

    public void addSuccessfulBanner() {
        io.print("~~~ Order Added ~~~\n");
    }

    public int getOrderNumber() {
        return io.readInt("Enter order number: ");
    }

    public Order displayEditOrder(Order orderToEdit, List<Product> productList, Map<String, StateTax> stateTaxes) {
        io.print("~EDITING~");
        io.print("Edit order customer name? (Current Entry is: " + orderToEdit.getCustomerName() + ")");
        
        boolean invalidInput = true;
        while (invalidInput) {
            String nameEdit = io.readString("Enter new customer name (allowed to contain [a-z][0-9][,][.]): ");
            if (nameEdit.isEmpty()) {
                break;
            } else if (nameEdit.matches("[\\w\\., ]+")) {
                invalidInput = false;
                orderToEdit.setCustomerName(nameEdit);
            }
        }
        
        io.print("Edit order state? (Current Entry is: " + orderToEdit.getState().getStateInitial() + ")");
        boolean invalidState = true;

        while (invalidState) {
            String stateInitials = io.readString("Enter state initials: ");
            if (stateInitials == null || stateInitials.isEmpty()) {
                invalidState = false;
            } else if (stateTaxes.keySet().contains(stateInitials)) {
                StateTax newState = stateTaxes.get(stateInitials);
                orderToEdit.setState(newState);
                invalidState = false;
            } else {
                io.print("we do not conduct business in this state. Please enter a state: ");
            }
        }
   
        io.print("Edit order product? (Current Entry is: " + orderToEdit.getProduct().getProductType() + ")");
        Product productEdit = getProductChoice(productList);
        orderToEdit.setProduct(productEdit);

        io.print("Edit order area? (Current Entry is: " + orderToEdit.getArea().toString() + ")");
        String areaEdit = getAreaFromUser();
        orderToEdit.setArea(new BigDecimal(areaEdit));

        Order editedOrder = new Order(orderToEdit.getCustomerName(), orderToEdit.getState(), orderToEdit.getProduct(), areaEdit);
        editedOrder.setDate(orderToEdit.getDate());
        editedOrder.setOrderNumber(orderToEdit.getOrderNumber());
        return editedOrder;
    }

    public void displayEditSuccessful() {
        io.print("~~~ Edit successful ~~~\n");
    }
    
    public void orderExistError() {
        io.print("Order does not exist!");
    }
    
    public void displayRemoveSuccessful() {
        io.print("~~~ Order successfully removed ~~~\n");
    }
    
    public void displayExportSuccessful() {
        io.print("~~~ Export Successful ~~~\n");
    }
    
    public void listOrders(List<Order> orderList) {
        orderList.forEach((i) -> displayOrderInfo(i));
    }
    
    public void displayTodaysDate() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String todayDate = LocalDate.now().format(fmt);
        io.print("~ Today's Date: " + todayDate);
    }
    
    public void displayExitBanner() {
        io.print("~~~ Thank you ~~~");
        io.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
    
    public void displayErrorMessage() {
        io.print("Error.");
    }

}