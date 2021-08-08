package com.erp.apparel.Models;

import java.io.Serializable;

public class HomeModel implements Serializable {

    private String TotalStyle;
    private String Projection;
    private String OnOrder;
    private String Delayed;
    private String OnGoing;
    private String UpComing;

    public HomeModel(String totalStyle, String projection, String onOrder, String delayed, String onGoing, String upComing) {
        TotalStyle = totalStyle;
        Projection = projection;
        OnOrder = onOrder;
        Delayed = delayed;
        OnGoing = onGoing;
        UpComing = upComing;
    }

    public String getTotalStyle() {
        return TotalStyle;
    }

    public void setTotalStyle(String totalStyle) {
        TotalStyle = totalStyle;
    }

    public String getProjection() {
        return Projection;
    }

    public void setProjection(String projection) {
        Projection = projection;
    }

    public String getOnOrder() {
        return OnOrder;
    }

    public void setOnOrder(String onOrder) {
        OnOrder = onOrder;
    }

    public String getDelayed() {
        return Delayed;
    }

    public void setDelayed(String delayed) {
        Delayed = delayed;
    }

    public String getOnGoing() {
        return OnGoing;
    }

    public void setOnGoing(String onGoing) {
        OnGoing = onGoing;
    }

    public String getUpComing() {
        return UpComing;
    }

    public void setUpComing(String upComing) {
        UpComing = upComing;
    }
}