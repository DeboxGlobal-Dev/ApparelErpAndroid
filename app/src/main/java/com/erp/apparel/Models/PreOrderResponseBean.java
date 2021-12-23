package com.erp.apparel.Models;

import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class PreOrderResponseBean implements Serializable {

    public String Status;
    public String Message;
    public ArrayList<PreOrderModel> TotalRecord;

    public static PreOrderResponseBean fromJson(String json) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, PreOrderResponseBean.class);
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "Response: " + e.getMessage() );
            return null;
        }
    }
}
