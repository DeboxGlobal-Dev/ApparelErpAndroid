package com.erp.apparel.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.erp.apparel.Adapter.StyleInfoAdapter;
import com.erp.apparel.AppConstant.AppNetworkConstants;
import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.Models.HomeModel;
import com.erp.apparel.Models.HomeResponseBean;
import com.erp.apparel.Models.StyleInfoModel;
import com.erp.apparel.Models.StyleInfoResponseBean;
import com.erp.apparel.R;

import java.util.ArrayList;

public class StyleInfo extends AppCompatActivity {

    ArrayList<StyleInfoModel> styleInfo;
    Dialog loaderdialog;
    RecyclerView m_rec_style;
    StyleInfoAdapter adapter;
    LinearLayoutManager layoutManager;
    TextView m_totalstyle,m_projection,m_onorder;
    ArrayList<HomeModel> homeModels;
    EditText m_search;
    ImageView m_cancel_search;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_info);

        m_rec_style=findViewById(R.id.style_REC);
        loaderdialog= DialogManager.getLoaderDialog(this);

            m_totalstyle=findViewById(R.id.totalstyle_STYLEINO);
            m_projection=findViewById(R.id.projection_STYLEINO);
            m_onorder=findViewById(R.id.onorder_STYLEINO);
            m_search=findViewById(R.id.search_TV);
            m_cancel_search=findViewById(R.id.cancel_search);

        homeModels=new ArrayList<>();
        styleInfo=new ArrayList<>();
        getDashBoard();
        getStyleInfo();

       /* String search=m_search.getText().toString();

            if(!search.equals(""))
            {
                m_cancel_search.setVisibility(View.VISIBLE);
            }
            else {
                m_cancel_search.setVisibility(View.GONE);
            }*/


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

            m_rec_style.setLayoutManager(layoutManager);
            adapter = new StyleInfoAdapter(this, styleInfo);
            m_rec_style.setAdapter(adapter);


    }

    public void filter(String text) {
        ArrayList<StyleInfoModel> filterlist=new ArrayList<>();
        for(StyleInfoModel item : styleInfo)
        {
            if(item.getStyleId().toLowerCase().contains(text.toLowerCase())||
                    item.getPriority().toLowerCase().contains(text.toLowerCase()) ||
                    item.getStatus().toLowerCase().contains(text.toLowerCase())) {
                filterlist.add(item);
            }
        }
        adapter.filterList(filterlist);
    }

    public void getStyleInfo(){

        loaderdialog.show();

        String url = AppNetworkConstants.STYLEINFO;

        Log.e("mylog", "MY StyleInfo url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your StyleInfo response is :" + response);

                try {
                    StyleInfoResponseBean responseBean = StyleInfoResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            styleInfo.addAll(responseBean.TotalRecord);

                        }
                        adapter.notifyDataSetChanged();
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
                Toast.makeText(StyleInfo.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                            String delayed=homeModels.get(0).getDelayed();
                            String onGoing=homeModels.get(0).getOnGoing();
                            String onOrder=homeModels.get(0).getOnOrder();
                            String projection=homeModels.get(0).getProjection();
                            String upComing=homeModels.get(0).getUpComing();

                            if(totalStyle.length()!=1)
                            {
                                m_totalstyle.setText(totalStyle);
                            }else {
                                m_totalstyle.setText("0"+totalStyle);
                            }

                            if(onOrder.length()!=1)
                            {
                                m_onorder.setText(onOrder);
                            }else {
                                m_onorder.setText("0"+onOrder);
                            }

                            if(projection.length()!=1)
                            {
                                m_projection.setText(projection);
                            }else {
                                m_projection.setText("0"+projection);
                            }

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
                Toast.makeText(StyleInfo.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}