package com.erp.apparel.Models;

import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class ProcessLightUpcomingResponseBean implements Serializable {

    public String Status;
    public String Message;
    public ArrayList<ProcessLightUpcomingModel> TotalRecord;

    public static ProcessLightUpcomingResponseBean fromJson(String json) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, ProcessLightUpcomingResponseBean.class);
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "Response: " + e.getMessage() );
            return null;
        }
    }
}
