package com.erp.apparel.Models;

import java.io.Serializable;

public class PreOrderModel implements Serializable {

    private String StyleId;
    private String product;
    private String effective;
    private String profit;
    private String linkid;

    public PreOrderModel(String styleId, String product, String effective, String profit, String linkid) {
        StyleId = styleId;
        this.product = product;
        this.effective = effective;
        this.profit = profit;
        this.linkid = linkid;
    }

    public String getStyleId() {
        return StyleId;
    }

    public void setStyleId(String styleId) {
        StyleId = styleId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getEffective() {
        return effective;
    }

    public void setEffective(String effective) {
        this.effective = effective;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getLinkid() {
        return linkid;
    }

    public void setLinkid(String linkid) {
        this.linkid = linkid;
    }
}