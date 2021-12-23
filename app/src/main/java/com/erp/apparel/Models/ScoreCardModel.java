package com.erp.apparel.Models;

import java.io.Serializable;

public class ScoreCardModel implements Serializable {

    private String FhoEarly;
    private String FhoDelayed;
    private String FhoOntime;
    private String PcdEarly;
    private String PcdDelayed;
    private String PcdOntime;

    public ScoreCardModel(String fhoEarly, String fhoDelayed, String fhoOntime, String pcdEarly, String pcdDelayed, String pcdOntime) {
        FhoEarly = fhoEarly;
        FhoDelayed = fhoDelayed;
        FhoOntime = fhoOntime;
        PcdEarly = pcdEarly;
        PcdDelayed = pcdDelayed;
        PcdOntime = pcdOntime;
    }

    public String getFhoEarly() {
        return FhoEarly;
    }

    public void setFhoEarly(String fhoEarly) {
        FhoEarly = fhoEarly;
    }

    public String getFhoDelayed() {
        return FhoDelayed;
    }

    public void setFhoDelayed(String fhoDelayed) {
        FhoDelayed = fhoDelayed;
    }

    public String getFhoOntime() {
        return FhoOntime;
    }

    public void setFhoOntime(String fhoOntime) {
        FhoOntime = fhoOntime;
    }

    public String getPcdEarly() {
        return PcdEarly;
    }

    public void setPcdEarly(String pcdEarly) {
        PcdEarly = pcdEarly;
    }

    public String getPcdDelayed() {
        return PcdDelayed;
    }

    public void setPcdDelayed(String pcdDelayed) {
        PcdDelayed = pcdDelayed;
    }

    public String getPcdOntime() {
        return PcdOntime;
    }

    public void setPcdOntime(String pcdOntime) {
        PcdOntime = pcdOntime;
    }
}
