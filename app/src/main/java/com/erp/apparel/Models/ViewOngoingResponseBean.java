package com.erp.apparel.Models;

import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewOngoingResponseBean implements Serializable {

    public String Status;
    public String Message;
    public ArrayList<ViewOngoingModel> TotalRecord;

    public static ViewOngoingResponseBean fromJson(String json) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, ViewOngoingResponseBean.class);
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "Response: " + e.getMessage() );
            return null;
        }
    }
}
