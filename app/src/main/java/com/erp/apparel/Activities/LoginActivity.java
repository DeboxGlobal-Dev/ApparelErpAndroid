package com.erp.apparel.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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
import com.erp.apparel.Models.HomeModel;
import com.erp.apparel.Models.HomeResponseBean;
import com.erp.apparel.Models.LoginResponseBean;
import com.erp.apparel.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText m_email,m_pass;
    ImageView m_button;
    String email="";
    String pass="";
    String validemail="";
    TextView m_forgetpwd;
    RelativeLayout m_login_header;
    Dialog loaderdialog;
    ArrayList<HomeModel> homeModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loaderdialog= DialogManager.getLoaderDialog(this);

        m_email=findViewById(R.id.email_LOGN);
        m_pass=findViewById(R.id.pass_LOGIN);
        m_button=findViewById(R.id.button_LOGIN);
        m_forgetpwd=findViewById(R.id.forgetpsd_LOGIN);
        m_login_header=findViewById(R.id.login_header);

        homeModels=new ArrayList<>();

        getDashBoard();

        m_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=m_email.getText().toString();
                pass=m_pass.getText().toString();

                if (TextUtils.isEmpty(email)) {

                    Snackbar snackbar = Snackbar.make(m_login_header, "Please Fill E-Mail", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
               else if (TextUtils.isEmpty(pass)) {

                    Snackbar snackbar = Snackbar.make(m_login_header, "Please Fill Password", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
               else {

                    userLogin();
                }
            }
        });


        m_forgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              dialogForget();
            }
        });


    }

    private void userLogin() {

        loaderdialog.show();
        try {

            // progressHud.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = AppNetworkConstants.LOGIN;

            Log.e("mylog","Url is : "+URL);
            Log.e("mylog","Email is : "+email);
            Log.e("mylog","password is : "+pass);

            JsonObjectRequest dataRequest = new JsonObjectRequest(Request.Method.POST,URL,
                    new JSONObject().put("username",email).put("userpass",pass),
                    response ->
                    {
                        loaderdialog.dismiss();
                        try {
                            Log.e("mylog","login response is : "+response);

                            String id= response.getString("id");
                            String email= response.getString("email");
                            String firstName= response.getString("firstName");
                            String lastName= response.getString("lastName");
                            String profileId= response.getString("profileId");

                            Log.e("my log"," id is :" +id);
                            Log.e("my log"," email is :" +email);
                            Log.e("my log"," first Name is :" +firstName);
                            Log.e("my log"," last Name is :" +lastName);
                            Log.e("my log"," profile id :" +profileId);

                            Prefs.INSTANCE.setFirstName(firstName);
                            Prefs.INSTANCE.setLastName(lastName);
                            Prefs.INSTANCE.setID(id);

                            Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(i);
                            finish();

                            //    progressHud.setVisibility(View.GONE);

                            //    ArrayList<CategoryEntity> categoryEntityArrayList = new ArrayList<>();

                            //     String Base_URL = response.getJSONObject("data").getString("base_url");

                          /*  for (int j = 0; j < response.getJSONObject("data").getJSONArray("subcats").length(); j++) {

                                JSONObject dataObject = response.getJSONObject("data").getJSONArray("subcats").getJSONObject(j);

                                CategoryEntity entity = new CategoryEntity();
                                entity.setCategoryId(dataObject.getString("cat_id"));
                                entity.setCategoryName(dataObject.getString("sub_cat_name"));
                                entity.setCategoryImage(categoryEntity.getCategoryImage());

                                entity.setSubCategoryId(dataObject.getString("id"));
                                entity.setSubcategoryName(dataObject.getString("sub_cat_name"));
                                entity.setSubCategoryImage(Base_URL + dataObject.getString("images"));

                                entity.setCateGoryDescription("Grocery stores also offer non-perishable foods that are packaged in bottles, boxes, and cans; some also have bakeries, butchers, delis, and fresh produce. Large grocery stores that stock significant amounts of non-food products, such as clothing and household items, are called supermarkets.");
                                categoryEntityArrayList.add(entity);
                                SubCategoryDataListAdapter adapter = new SubCategoryDataListAdapter(SubCategoryListingActivity.this,categoryEntityArrayList,recyclerView);
                                recyclerView.setAdapter(adapter);

                            }*/


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        loaderdialog.dismiss();
                       // Log.e("mylog", error.toString());
                        Log.e("my log"," Invalid Credentials :");
                        Toast.makeText(LoginActivity.this, "Invalid Credentials ",Toast.LENGTH_SHORT).show();
                        //  progressHud.setVisibility(View.GONE);
                        //   showSnackBar("Failed to load Data !!", R.color.red);
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

    private void userForget() {
        loaderdialog.show();
        try {

            // progressHud.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = AppNetworkConstants.FORGETOTP;

            Log.e("mylog"," Forget Url is : "+URL);
            Log.e("mylog","valid Email Id is :" + validemail);

            JsonObjectRequest dataRequest = new JsonObjectRequest(Request.Method.POST,URL,
                    new JSONObject().put("username",validemail),
                    response ->
                    {
                        loaderdialog.dismiss();
                        try {
                            Log.e("mylog","forget response is : "+response);

                            String id= response.getString("id");

                            Log.e("my log"," id is :" +id);

                            Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
                            startActivity(i);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        loaderdialog.dismiss();
                        // Log.e("mylog", error.toString());
                        Log.e("my log"," Invalid Email-ID :");
                        Toast.makeText(LoginActivity.this, "Invalid E-mail ",Toast.LENGTH_SHORT).show();
                        //  progressHud.setVisibility(View.GONE);
                        //   showSnackBar("Failed to load Data !!", R.color.red);
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

    public void dialogForget() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.resetdialog);

        EditText m_validemail = dialog.findViewById(R.id.validemail_DIALOG);
        TextView m_submit = dialog.findViewById(R.id.submit_DIALOG);
        TextView m_cancel = dialog.findViewById(R.id.cancel_DIALOG);

        m_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              validemail = m_validemail.getText().toString();

                if (TextUtils.isEmpty(validemail)) {

                    Snackbar snackbar = Snackbar.make(m_login_header, "Please Fill Valid E-Mail", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else {
                    userForget();
                    dialog.cancel();
                }

               // Log.e("mylog","Email Id is :" + email);

             /* if(validemail.equals("apparelerp@deboxcrm.com"))
              {
                  userForget();
                  dialog.cancel();
              }
              else {
                  Toast.makeText(LoginActivity.this,"Please Enter Valid E-mail Id ",Toast.LENGTH_SHORT).show();
              }*/

            }
        });

        m_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    public void getDashBoard(){

        loaderdialog.show();

        String url = AppNetworkConstants.DASHBOARD;

        Log.e("mylog", "MY DashBoard url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your Dashboard response is :" + response);

                try {
                    HomeResponseBean responseBean = HomeResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            homeModels.addAll(responseBean.TotalRecord);

                            String totalStyle=homeModels.get(0).getTotalStyle();
                            String onOrder=homeModels.get(0).getOnOrder();
                            String projection=homeModels.get(0).getProjection();
                            String upComing=homeModels.get(0).getUpComing();
                            String delayed=homeModels.get(0).getDelayed();
                            String  onGoing=homeModels.get(0).getOnGoing();

                            Prefs.INSTANCE.setDelayed(Integer.parseInt(delayed));
                            Prefs.INSTANCE.setOngoing(Integer.parseInt(onGoing));
                            Prefs.INSTANCE.setUpcoming(Integer.parseInt(upComing));
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
                Toast.makeText(LoginActivity.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}