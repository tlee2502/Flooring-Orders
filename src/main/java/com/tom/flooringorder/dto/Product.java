package com.tom.flooringorder.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    
    private String productType;
    private BigDecimal materialCostPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    
    public Product() {
        
    }
    
    public Product(String x, BigDecimal y, BigDecimal z) {
        this.productType = x;
        this.materialCostPerSquareFoot = y;
        this.laborCostPerSquareFoot = z;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getMaterialCostPerSquareFoot() {
        return materialCostPerSquareFoot;
    }

    public void setMaterialCostPerSquareFoot(BigDecimal materialCostPerSquareFoot) {
        this.materialCostPerSquareFoot = materialCostPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.productType);
        hash = 71 * hash + Objects.hashCode(this.materialCostPerSquareFoot);
        hash = 71 * hash + Objects.hashCode(this.laborCostPerSquareFoot);
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.materialCostPerSquareFoot, other.materialCostPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSquareFoot, other.laborCostPerSquareFoot)) {
            return false;
        }
        return true;
    }
    
}
