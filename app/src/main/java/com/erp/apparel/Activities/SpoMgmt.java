package com.erp.apparel.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.erp.apparel.Adapter.MaterialAdapter;
import com.erp.apparel.Adapter.PreOrderAdapter;
import com.erp.apparel.Adapter.SpoAdapter;
import com.erp.apparel.AppConstant.AppNetworkConstants;
import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.Models.BusinessModel;
import com.erp.apparel.Models.MaterialModel;
import com.erp.apparel.Models.PreOrderModel;
import com.erp.apparel.Models.PreOrderResponseBean;
import com.erp.apparel.Models.SpoModel;
import com.erp.apparel.Models.SpoResponseBean;
import com.erp.apparel.R;

import java.util.ArrayList;

public class SpoMgmt extends AppCompatActivity {

    ArrayList<SpoModel> spoModels;
    SpoAdapter spoAdapter;
    MaterialAdapter materialAdapter;
    RecyclerView m_rec;
    LinearLayoutManager layoutManager;
    Dialog loaderdialog;
    EditText m_search;
    ImageView m_cancel_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spo_mgmt);

        loaderdialog= DialogManager.getLoaderDialog(this);

        m_search=findViewById(R.id.search_TV);
        m_cancel_search=findViewById(R.id.cancel_search);

        m_rec=findViewById(R.id.spomgmt_REC);

        spoModels= new ArrayList<>();

        getSpo();

        m_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());

                if(!s.equals(""))
                {
                    m_cancel_search.setVisibility(View.VISIBLE);
                }
                else {
                    m_cancel_search.setVisibility(View.GONE);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        m_cancel_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_search.setText("");
                m_cancel_search.setVisibility(View.GONE);
            }
        });


        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        m_rec.setLayoutManager(layoutManager);

        spoAdapter = new SpoAdapter(SpoMgmt.this, spoModels);
        m_rec.setAdapter(spoAdapter);

    }
    public void getSpo(){

        loaderdialog.show();

        String url = AppNetworkConstants.SPOMGMT;

        Log.e("mylog", "MY spo mgmt url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your spo mgmt response is :" + response);

                try {
                    SpoResponseBean responseBean = SpoResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            spoModels.clear();
                            spoModels.addAll(responseBean.TotalRecord);

                        }
                        spoAdapter.notifyDataSetChanged();
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
                Toast.makeText(SpoMgmt.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void filter(String text) {
        ArrayList<SpoModel> filterlist=new ArrayList<>();
        for(SpoModel item : spoModels)
        {
            ArrayList<MaterialModel> mm=new ArrayList<>();

             for(MaterialModel item1: item.getMaterialList())
             {
                 if (item.getStyle().toLowerCase().contains(text.toLowerCase()) ||
                         item.getSupplier().toLowerCase().contains(text.toLowerCase()) ||
                         item1.getStyleId().toLowerCase().contains(text.toLowerCase()) ||
                         item1.getMaterialid().toLowerCase().contains(text.toLowerCase()))
                        {
                          filterlist.add(item);
                          mm.add(item1);
                        }
                  }
            }
        spoAdapter.filterList(filterlist);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        loaderdialog.show();
        Intent i=new Intent(this,HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}