package com.erp.apparel.Models;

import java.io.Serializable;

public class StyleInfoModel implements Serializable {

    private String Id;
    private String StyleId;
    private String TnaProgress;
    private String Status;
    private String Priority;

    public StyleInfoModel(String id, String styleId, String tnaProgress, String status, String priority) {
        Id = id;
        StyleId = styleId;
        TnaProgress = tnaProgress;
        Status = status;
        Priority = priority;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStyleId() {
        return StyleId;
    }

    public void setStyleId(String styleId) {
        StyleId = styleId;
    }

    public String getTnaProgress() {
        return TnaProgress;
    }

    public void setTnaProgress(String tnaProgress) {
        TnaProgress = tnaProgress;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }
}