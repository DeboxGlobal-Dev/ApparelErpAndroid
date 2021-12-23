package com.erp.apparel.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class SpoModel implements Serializable {

    private String supplier;
    private String style;
    private ArrayList<MaterialModel> materialList;

    public SpoModel(String supplier, String style, ArrayList<MaterialModel> materialList) {
        this.supplier = supplier;
        this.style = style;
        this.materialList = materialList;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public ArrayList<MaterialModel> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(ArrayList<MaterialModel> materialList) {
        this.materialList = materialList;
    }
}