package com.erp.apparel.Models;

import java.io.Serializable;

public class ProcessLightModel implements Serializable {

    private String ResponsiblePerson;
    private String Count;
    private String id;

    public ProcessLightModel(String responsiblePerson, String count, String id) {
        ResponsiblePerson = responsiblePerson;
        Count = count;
        this.id = id;
    }

    public String getResponsiblePerson() {
        return ResponsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        ResponsiblePerson = responsiblePerson;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}