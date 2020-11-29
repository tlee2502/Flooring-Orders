package com.tom.flooringorder.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Order {
        
    private int orderNumber;
    private String customerName;
    private StateTax state;
    private Product product;
    private String date;
    private BigDecimal area;    
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;
    
    public Order() {
    }
    
    public Order(String customerName, StateTax state, Product product, String areaAsString) {
        this.customerName = customerName;
        this.state = state;
        this.product = product;
        this.area = new BigDecimal(areaAsString);
        
        this.materialCost = this.area.multiply(product.getMaterialCostPerSquareFoot());
        this.laborCost = this.area.multiply(product.getLaborCostPerSquareFoot());
        
        this.tax = (this.materialCost.add(this.laborCost))
                .multiply(state.getTaxRate().divide(new BigDecimal("100")));
        this.total = this.materialCost.add(this.laborCost).add(this.tax);
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public StateTax getState() {
        return state;
    }

    public void setState(StateTax state) {
        this.state = state;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.orderNumber;
        hash = 31 * hash + Objects.hashCode(this.customerName);
        hash = 31 * hash + Objects.hashCode(this.state);
        hash = 31 * hash + Objects.hashCode(this.product);
        hash = 31 * hash + Objects.hashCode(this.date);
        hash = 31 * hash + Objects.hashCode(this.area);
        hash = 31 * hash + Objects.hashCode(this.materialCost);
        hash = 31 * hash + Objects.hashCode(this.laborCost);
        hash = 31 * hash + Objects.hashCode(this.tax);
        hash = 31 * hash + Objects.hashCode(this.total);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }
}