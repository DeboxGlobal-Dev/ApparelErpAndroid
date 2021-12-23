package com.erp.apparel.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.erp.apparel.AppConstant.AppNetworkConstants;
import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.Data.Prefs;
import com.erp.apparel.Models.MD5;
import com.erp.apparel.Models.OtdsChartResponseBean;
import com.erp.apparel.Models.OtdsResponseBean;
import com.erp.apparel.Models.ProfileModel;
import com.erp.apparel.Models.ProfileResponseBean;
import com.erp.apparel.Models.UpdatedProfileModel;
import com.erp.apparel.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Profile extends AppCompatActivity {

    TextView m_fname, m_lname, m_phone,m_email,m_username,m_update,m_desc,m_profilename,m_email_ED,m_username_ED;
    EditText m_oldpass,m_newpass,m_confirmpass,m_fname_ED, m_lname_ED, m_phone_ED;
    ImageView m_tick;
    RelativeLayout m_oldpass_ll,m_newpass_ll, m_confirmpass_ll,m_showprofile_ll, m_editprofile_ll;
    LinearLayout m_edit,m_changepass_ll, m_changepassback_ll;
    Dialog loaderdialog;
    ArrayList<ProfileModel> profiles;
    String updatePassword="";
    String firstName="";
    String lastName="";
    String phoneNo="";
    String confirmPassword="";
    NestedScrollView m_nested_header;
    String AES="AES";
    String outputpassword="";
    String oldPassword="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        loaderdialog= DialogManager.getLoaderDialog(this);

        m_nested_header=findViewById(R.id.nested_PROFILE);
        m_edit=findViewById(R.id.edit_ll);
        m_tick=findViewById(R.id.tick_PROFILE);
        m_desc=findViewById(R.id.desc_PROFILE);
        m_oldpass_ll=findViewById(R.id.oldpass_ll);
        m_newpass_ll=findViewById(R.id.newpass_ll);
        m_confirmpass_ll=findViewById(R.id.confirmpass_ll);
        m_update=findViewById(R.id.update_PROFILE);
        m_profilename=findViewById(R.id.profilename_PROFILE);

        m_oldpass=findViewById(R.id.oldpass_PROFILE);
        m_newpass=findViewById(R.id.newpass_PROFILE);
        m_confirmpass=findViewById(R.id.confirmpass_PROFILE);

        m_changepass_ll=findViewById(R.id.chgpass_PROFILE);
        m_changepassback_ll=findViewById(R.id.chgpassback_PROFILE);

        m_showprofile_ll=findViewById(R.id.showprofile_ll);
        m_editprofile_ll=findViewById(R.id.edit_profile_ll);


        m_fname=findViewById(R.id.fname_PROFILE);
        m_lname=findViewById(R.id.lname_PROFILE);
        m_phone=findViewById(R.id.phone_PROFILE);
        m_email=findViewById(R.id.email_PROFILE);
        m_username=findViewById(R.id.usename_PROFILE);

        m_fname_ED=findViewById(R.id.fname_edit_PROFILE);
        m_lname_ED=findViewById(R.id.lname_edit_PROFILE);
        m_phone_ED=findViewById(R.id.phone_edit_PROFILE);
        m_email_ED=findViewById(R.id.email_edit_PROFILE);
        m_username_ED=findViewById(R.id.usename_edit_PROFILE);


        profiles=new ArrayList<>();
        getProfile();



        m_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_edit.setVisibility(View.GONE);
                m_showprofile_ll.setVisibility(View.GONE);
                m_tick.setVisibility(View.VISIBLE);
                m_editprofile_ll.setVisibility(View.VISIBLE);
            }
        });

        m_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstName=m_fname_ED.getText().toString();
                lastName=m_lname_ED.getText().toString();
                phoneNo=m_phone_ED.getText().toString();


                if (TextUtils.isEmpty(firstName)) {

                    Snackbar snackbar = Snackbar.make(m_nested_header, "Please Fill First Name", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if (TextUtils.isEmpty(lastName)) {

                    Snackbar snackbar = Snackbar.make(m_nested_header, "Please Fill Last Name", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if (TextUtils.isEmpty(phoneNo)) {

                    Snackbar snackbar = Snackbar.make(m_nested_header, "Please Fill Phone Number", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else {
                    m_tick.setVisibility(View.GONE);
                    m_editprofile_ll.setVisibility(View.GONE);
                    m_showprofile_ll.setVisibility(View.VISIBLE);
                    m_edit.setVisibility(View.VISIBLE);

                   updateProfile();
                    Toast.makeText(Profile.this, "Profile has been successfully updated", Toast.LENGTH_SHORT).show();

                }
            }
        });

        m_changepass_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_desc.setVisibility(View.GONE);
                m_changepass_ll.setVisibility(View.GONE);
                m_changepassback_ll.setVisibility(View.VISIBLE);
                m_oldpass_ll.setVisibility(View.VISIBLE);
                m_newpass_ll.setVisibility(View.VISIBLE);
                m_confirmpass_ll.setVisibility(View.VISIBLE);
                m_update.setVisibility(View.VISIBLE);
            }
        });

        m_changepassback_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_desc.setVisibility(View.VISIBLE);
                m_changepass_ll.setVisibility(View.VISIBLE);
                m_changepassback_ll.setVisibility(View.GONE);
                m_oldpass_ll.setVisibility(View.GONE);
                m_newpass_ll.setVisibility(View.GONE);
                m_confirmpass_ll.setVisibility(View.GONE);
                m_update.setVisibility(View.GONE);
            }
        });

        m_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmPassword=m_confirmpass.getText().toString();
                updatePassword=m_newpass.getText().toString();
                oldPassword=m_oldpass.getText().toString();

                byte[] md5Input=oldPassword.getBytes();
                BigInteger md5Data=null;
                try {
                    md5Data=new BigInteger(1, MD5.encrpytMD5(md5Input));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                outputpassword= md5Data.toString(16);

                Log.e("mylog","input password are "+outputpassword);
                Log.e("mylog","encrypt password are "+Prefs.INSTANCE.getOldPassowrd());


                if (TextUtils.isEmpty(m_oldpass.getText().toString())) {

                    Snackbar snackbar = Snackbar.make(m_nested_header, "Please Fill Old Password", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if (TextUtils.isEmpty(updatePassword)) {

                    Snackbar snackbar = Snackbar.make(m_nested_header, "Please Fill New Password", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if (TextUtils.isEmpty(confirmPassword)) {

                    Snackbar snackbar = Snackbar.make(m_nested_header, "Please Fill Confirm Password", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

               else if(!updatePassword.equals(confirmPassword)) {
                    Toast.makeText(Profile.this, "New Password and Confirm Password doesn't Match", Toast.LENGTH_SHORT).show();
                }

               else if (!Prefs.INSTANCE.getOldPassowrd().equals(outputpassword))
                {
                    Toast.makeText(Profile.this, "Please Verify Old Password", Toast.LENGTH_SHORT).show();
                }

                else {
                    m_desc.setVisibility(View.VISIBLE);
                    m_changepass_ll.setVisibility(View.VISIBLE);
                    m_changepassback_ll.setVisibility(View.GONE);
                    m_oldpass_ll.setVisibility(View.GONE);
                    m_newpass_ll.setVisibility(View.GONE);
                    m_confirmpass_ll.setVisibility(View.GONE);
                    m_update.setVisibility(View.GONE);

                    updatePassword();
                    Toast.makeText(Profile.this, "Profile has been updated Successfully", Toast.LENGTH_SHORT).show();

                }
           }
        });


    }

    public void getProfile() {

        loaderdialog.show();

        String url = AppNetworkConstants.PROFILE +"id="+Prefs.INSTANCE.getID();

        Log.e("mylog", "MY profile url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your Profile response is :" + response);

                try {
                    ProfileResponseBean responseBean = ProfileResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            profiles.clear();
                            profiles.addAll(responseBean.Profile);

                            String email = profiles.get(0).getEmail();
                            String firstName = profiles.get(0).getFirstName();
                            String id = profiles.get(0).getId();
                            String lastName = profiles.get(0).getLastName();
                            String password = profiles.get(0).getPassword();
                            String phone = profiles.get(0).getPhone();
                            String userName = profiles.get(0).getUserName();

                            Prefs.INSTANCE.setOldPassword(password);

                            m_fname.setText(firstName);
                            m_lname.setText(lastName);
                            m_email.setText(email);
                            m_phone.setText(phone);
                            m_email.setText(email);
                            m_username.setText(userName);
                            m_profilename.setText(""+firstName+" "+lastName);

                           if(!firstName.equals(""))
                           {
                               m_fname_ED.setText(firstName);
                           }
                           else
                               {
                                   m_fname_ED.setHint("First Name");
                               }


                            if(!lastName.equals(""))
                            {
                                m_lname_ED.setText(lastName);
                            }
                            else
                            {
                                m_lname_ED.setHint("Last Name");
                            }

                            if(!phone.equals(""))
                            {
                                m_phone_ED.setText(phone);
                            }
                            else
                            {
                                m_phone_ED.setHint("Phone No");
                            }



                            m_email_ED.setText(email);
                            m_username_ED.setText(userName);


                         //   Log.e("Mylog","my facorry sot "+factorySot);

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("MyLog", "onResponse: Error: " + e.getMessage());

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loaderdialog.dismiss();
                Toast.makeText(Profile.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updateProfile() {

        loaderdialog.show();
        try {

            // progressHud.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = AppNetworkConstants.UPDATEPROFILE;

            Log.e("mylog","Url is : "+URL);
            Log.e("mylog","UserId is : "+ Prefs.INSTANCE.getID());
            Log.e("mylog","First Name is : "+firstName);
            Log.e("mylog","Last Name is : "+lastName);
            Log.e("mylog","Phone is : "+phoneNo);

            JsonObjectRequest dataRequest = new JsonObjectRequest(Request.Method.POST,URL,
                    new JSONObject().put("userId",Prefs.INSTANCE.getID()).put("firstname",firstName).put("lastname",lastName).put("phone",phoneNo),
                    response ->
                    {
                        loaderdialog.dismiss();
                        try {
                            getProfile();
                            Log.e("mylog","Update Profile response is : "+response);

                            String message= response.getString("Message");
                            Log.e("my log"," Message is :" +message);


                         //   Toast.makeText(Profile.this, message, Toast.LENGTH_SHORT).show();
                        //    getProfile();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        loaderdialog.dismiss();

                         Log.e("mylog", error.toString());
                        Log.e("my log"," Invalid Credentials :");
                        Toast.makeText(Profile.this, "Invalid Credentials ",Toast.LENGTH_SHORT).show();
                    });
            dataRequest.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(dataRequest);

        }catch (Exception e){
            e.printStackTrace();
            //    showSnackBar("Failed to load Data !!", android.R.color.holo_red_dark);
        }
    }

    private void updatePassword() {

        loaderdialog.show();
        try {

            // progressHud.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = AppNetworkConstants.UPDATEPASSWORD;

            Log.e("mylog","Url is : "+URL);
            Log.e("mylog","UserId is : "+ Prefs.INSTANCE.getID());
            Log.e("mylog","Updated Passord is : "+updatePassword);


            JsonObjectRequest dataRequest = new JsonObjectRequest(Request.Method.POST,URL,
                    new JSONObject().put("userId",Prefs.INSTANCE.getID()).put("updatepassword",updatePassword),
                    response ->
                    {
                        loaderdialog.dismiss();
                        try {
                            Log.e("mylog","Update Password response is : "+response);
                            getProfile();

                          /*  String id= response.getString("userId");
                            String firstname= response.getString("firstname");
                            String lastname= response.getString("lastname");
                            String phone= response.getString("phone");

                            Log.e("my log"," User id is :" +id);
                            Log.e("my log"," first Name is :" +firstname);
                            Log.e("my log"," last Name is :" +lastname);
                            Log.e("my log"," phone is :" +phone);*/



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        loaderdialog.dismiss();

                         Log.e("mylog", error.toString());
                          Log.e("my log"," Invalid Credentials :");
                         Toast.makeText(Profile.this, "Invalid Credentials ",Toast.LENGTH_SHORT).show();
                    });
            dataRequest.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(dataRequest);

        }catch (Exception e){
            e.printStackTrace();
            //    showSnackBar("Failed to load Data !!", android.R.color.holo_red_dark);
        }
    }



    public void btnMD() {
        byte[] md5Input=m_oldpass.getText().toString().getBytes();
        BigInteger md5Data=null;

        try {
            md5Data=new BigInteger(1, MD5.encrpytMD5(md5Input));
        } catch (Exception e) {
            e.printStackTrace();
        }

        outputpassword= md5Data.toString(16);

        Log.e("mylog","encrypt password are"+outputpassword);
    }

}