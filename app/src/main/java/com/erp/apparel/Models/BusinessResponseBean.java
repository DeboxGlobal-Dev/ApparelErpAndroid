package com.erp.apparel.Models;

import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class BusinessResponseBean implements Serializable {

    public String Status;
    public String Message;
    public ArrayList<BusinessModel> TotalRecord;

    public static BusinessResponseBean fromJson(String json) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, BusinessResponseBean.class);
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "Response: " + e.getMessage() );
            return null;
        }
    }
}
