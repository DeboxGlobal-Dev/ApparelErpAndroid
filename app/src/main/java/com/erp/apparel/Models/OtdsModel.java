package com.erp.apparel.Models;

import java.io.Serializable;

public class OtdsModel implements Serializable {

    private String Style;
    private String PO;
    private String BrandSOT;
    private String FactorySOT;

    public OtdsModel(String style, String PO, String brandSOT, String factorySOT) {
        Style = style;
        this.PO = PO;
        BrandSOT = brandSOT;
        FactorySOT = factorySOT;
    }

    public String getStyle() {
        return Style;
    }

    public void setStyle(String style) {
        Style = style;
    }

    public String getPO() {
        return PO;
    }

    public void setPO(String PO) {
        this.PO = PO;
    }

    public String getBrandSOT() {
        return BrandSOT;
    }

    public void setBrandSOT(String brandSOT) {
        BrandSOT = brandSOT;
    }

    public String getFactorySOT() {
        return FactorySOT;
    }

    public void setFactorySOT(String factorySOT) {
        FactorySOT = factorySOT;
    }
}
