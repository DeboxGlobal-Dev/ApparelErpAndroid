package com.erp.apparel.Models;

import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginResponseBean implements Serializable {


    public ArrayList<LoginModel> Response;

    public static LoginResponseBean fromJson(String json) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, LoginResponseBean.class);
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "Response: " + e.getMessage() );
            return null;
        }
    }
}
