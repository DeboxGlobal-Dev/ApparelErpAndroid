package com.erp.apparel.Models;

import java.io.Serializable;

public class BusinessModel implements Serializable {

    private String Buyer;
    private String Brand;
    private String Qty;

    public BusinessModel(String buyer, String brand, String qty) {
        Buyer = buyer;
        Brand = brand;
        Qty = qty;
    }

    public String getBuyer() {
        return Buyer;
    }

    public void setBuyer(String buyer) {
        Buyer = buyer;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }
}