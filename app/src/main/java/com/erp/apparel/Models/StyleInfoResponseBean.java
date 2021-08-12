package com.erp.apparel.Models;

import android.util.Log;

import com.erp.apparel.Activities.StyleInfo;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class StyleInfoResponseBean implements Serializable {

    public String Status;
    public String Message;
    public ArrayList<StyleInfoModel> TotalRecord;

    public static StyleInfoResponseBean fromJson(String json) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, StyleInfoResponseBean.class);
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "Response: " + e.getMessage() );
            return null;
        }
    }
}
