package com.erp.apparel.Models;

import java.io.Serializable;

public class BusinessChartModel implements Serializable {

    private String BuyerName;
    private String BuyerQty;

    public BusinessChartModel(String buyerName, String buyerQty) {
        BuyerName = buyerName;
        BuyerQty = buyerQty;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public String getBuyerQty() {
        return BuyerQty;
    }

    public void setBuyerQty(String buyerQty) {
        BuyerQty = buyerQty;
    }
}