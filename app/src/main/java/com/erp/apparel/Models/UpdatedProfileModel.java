package com.erp.apparel.Models;

import java.io.Serializable;

public class UpdatedProfileModel implements Serializable {

    private String msg;

    public UpdatedProfileModel(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}