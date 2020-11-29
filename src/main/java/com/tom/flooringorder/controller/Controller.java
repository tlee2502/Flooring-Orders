package com.tom.flooringorder.controller;

import com.tom.flooringorder.dao.DataPersistenceException;
import com.tom.flooringorder.dto.Order;
import com.tom.flooringorder.service.InvalidOrderException;
import com.tom.flooringorder.ui.View;
import com.tom.flooringorder.service.ServiceLayer;

public class Controller {
        
    private final View view;
    private final ServiceLayer service;
    
    public Controller(View view, ServiceLayer service) {
        this.view = view;
        this.service = service;
    }
    
    public void run() {
        boolean isRunning = true;
        view.displayWelcomeMessage();
        view.displayTodaysDate();
        
        while (isRunning) {
            int selection = view.displayMainMenuGetSelection();
            switch (selection) {
                case 1:
                    displayOrders();
                    break;
                case 2:
                    addAnOrder();
                    break;
                case 3:
                    editAnOrder();
                    break;
                case 4:
                    removeAnOrder();
                    break;  
                case 5:
                    exportAll();
                    break;
                case 6:
                    isRunning = false;                   
                    break;    
            }
        }
        view.displayExitBanner();
    }

    private void displayOrders() {
        view.displayOrdersMessage();
        String dateFromUser = view.getDateFromUser();
        try {
            view.listOrders(service.listOrders(dateFromUser));
        } catch (InvalidOrderException e) {
            view.displayNoOrdersForDate();
        } catch (DataPersistenceException x) {
            view.displayErrorMessage();
        }
        view.enterToContinue();
    }

    private void addAnOrder() {
        view.addNewOrderBanner();
        try {
            Order newOrder = view.getNewOrder(service.getProductList(), service.getStateTaxes());
            view.displayOrderInfo(newOrder);
            if (view.getConfirmationFromUser("add")) {
                service.addOrder(newOrder);
                view.addSuccessfulBanner();
            } else {
                view.enterToContinue();
            }
        } catch (DataPersistenceException e) {
            view.displayErrorMessage();
        }
    }

    private void editAnOrder() {
        String orderDate = view.getDateFromUser();
        int orderNumber = view.getOrderNumber();
        try {
            Order orderToEdit = service.getOrder(orderDate, orderNumber);
            Order editedOrder = view.displayEditOrder(orderToEdit, service.getProductList(), service.getStateTaxes());
            view.displayOrderInfo(editedOrder);
            if (view.getConfirmationFromUser("edit")) {
                service.replaceOrder(orderDate, orderToEdit, editedOrder);
                view.displayEditSuccessful();
            }
        } catch (InvalidOrderException e) {
            view.orderExistError();
        } catch (DataPersistenceException x) {
            view.displayErrorMessage();
        }
        view.enterToContinue();
    }

    private void removeAnOrder() {
        String orderDate = view.getDateFromUser();
        int orderNumber = view.getOrderNumber();
        try {
            view.displayOrderInfo(service.getOrder(orderDate, orderNumber));
            if (view.getConfirmationFromUser("remove")) {
                service.removeOrder(orderDate, orderNumber);
                view.displayRemoveSuccessful();
            }
        } catch (InvalidOrderException e) {
            view.orderExistError();
        } catch (DataPersistenceException x) {
            view.displayErrorMessage();
        }
        view.enterToContinue();
    }

    private void exportAll() {
        try {
            service.exportAll();
            view.displayExportSuccessful();
        } catch (DataPersistenceException e) {
            view.displayErrorMessage();
        }
        view.enterToContinue();        
    }
 
}