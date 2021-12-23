package com.erp.apparel.Models;

import java.io.Serializable;

public class ProcessLightUpcomingModel implements Serializable {



    private String ResponsiblePerson;
    private String Count;
    private String nameid;

    public ProcessLightUpcomingModel(String responsiblePerson, String count, String nameid) {
        ResponsiblePerson = responsiblePerson;
        Count = count;
        this.nameid = nameid;
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

    public String getNameid() {
        return nameid;
    }

    public void setNameid(String nameid) {
        this.nameid = nameid;
    }
}