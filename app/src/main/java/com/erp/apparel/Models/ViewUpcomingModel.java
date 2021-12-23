package com.erp.apparel.Models;

import java.io.Serializable;

public class ViewUpcomingModel implements Serializable {

    private String ResponsiblePerson;
    private String keys;
    private String styleid;
    private String planned;
    private String taskid;
    private String style;

    public ViewUpcomingModel(String responsiblePerson, String keys, String styleid, String planned, String taskid, String style) {
        ResponsiblePerson = responsiblePerson;
        this.keys = keys;
        this.styleid = styleid;
        this.planned = planned;
        this.taskid = taskid;
        this.style = style;
    }

    public String getResponsiblePerson() {
        return ResponsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        ResponsiblePerson = responsiblePerson;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getStyleid() {
        return styleid;
    }

    public void setStyleid(String styleid) {
        this.styleid = styleid;
    }

    public String getPlanned() {
        return planned;
    }

    public void setPlanned(String planned) {
        this.planned = planned;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}