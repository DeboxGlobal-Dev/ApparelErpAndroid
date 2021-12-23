package com.erp.apparel.Models;

import java.io.Serializable;

public class OtdsChartModel implements Serializable {

    private int FactorySot;
    private int OverallSot;
    private int CFair;
    private int AirPort;
    private int SeaPort;

    public OtdsChartModel(int factorySot, int overallSot, int CFair, int airPort, int seaPort) {
        FactorySot = factorySot;
        OverallSot = overallSot;
        this.CFair = CFair;
        AirPort = airPort;
        SeaPort = seaPort;
    }

    public int getFactorySot() {
        return FactorySot;
    }

    public void setFactorySot(int factorySot) {
        FactorySot = factorySot;
    }

    public int getOverallSot() {
        return OverallSot;
    }

    public void setOverallSot(int overallSot) {
        OverallSot = overallSot;
    }

    public int getCFair() {
        return CFair;
    }

    public void setCFair(int CFair) {
        this.CFair = CFair;
    }

    public int getAirPort() {
        return AirPort;
    }

    public void setAirPort(int airPort) {
        AirPort = airPort;
    }

    public int getSeaPort() {
        return SeaPort;
    }

    public void setSeaPort(int seaPort) {
        SeaPort = seaPort;
    }
}
