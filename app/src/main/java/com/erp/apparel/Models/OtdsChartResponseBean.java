package com.erp.apparel.Models;

import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class OtdsChartResponseBean implements Serializable {

    public String Status;
    public String Message;
    public ArrayList<OtdsChartModel> ChartRecord;

    public static OtdsChartResponseBean fromJson(String json) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, OtdsChartResponseBean.class);
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "Response: " + e.getMessage() );
            return null;
        }
    }
}
