package com.erp.apparel.Models;

import java.io.Serializable;

public class MaterialModel implements Serializable {

    private String Materialid;
    private String value;
    private String styleId;

    public MaterialModel(String materialid, String value, String styleId) {
        Materialid = materialid;
        this.value = value;
        this.styleId = styleId;
    }

    public String getMaterialid() {
        return Materialid;
    }

    public void setMaterialid(String materialid) {
        Materialid = materialid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }
}