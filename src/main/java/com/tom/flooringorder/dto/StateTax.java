package com.tom.flooringorder.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class StateTax {
    
    private String stateInitial;    
    private String stateName;
    private BigDecimal taxRate;

    
    public StateTax() {
        
    }
    
    public StateTax(String stateInitial, String stateName, BigDecimal taxRate) {
        this.stateInitial = stateInitial;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }
    
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }    

    public String getStateInitial() {
        return stateInitial;
    }

    public void setStateInitial(String stateInitial) {
        this.stateInitial = stateInitial;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.stateInitial);
        hash = 59 * hash + Objects.hashCode(this.stateName);
        hash = 59 * hash + Objects.hashCode(this.taxRate);
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
        final StateTax other = (StateTax) obj;
        if (!Objects.equals(this.stateInitial, other.stateInitial)) {
            return false;
        }
        if (!Objects.equals(this.stateName, other.stateName)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        return true;
    }
    
}