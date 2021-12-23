package com.erp.apparel.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Editable;
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
import com.erp.apparel.Adapter.BusinessAdapter;

import com.erp.apparel.AppConstant.AppNetworkConstants;
import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.Models.BusinessChartModel;
import com.erp.apparel.Models.BusinessChartResponseBean;
import com.erp.apparel.Models.BusinessModel;
import com.erp.apparel.Models.BusinessResponseBean;

import com.erp.apparel.Models.StyleInfoModel;
import com.erp.apparel.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;

import static java.lang.Float.*;

public class BusinessSummary extends AppCompatActivity {

    private PieChart chart;
    private int i1 = 45;
    private int i2 = 25;
    private int i3 = 18;
    private int i4 = 15;
    private int i5 = 12;
    private int i6 = 15;

    ArrayList<BusinessModel> businessModels;
    ArrayList<BusinessChartModel> businessChartModels;
    Dialog loaderdialog;
    RecyclerView m_rec_bs;
    BusinessAdapter businessAdapter;
    LinearLayoutManager layoutManager;
    EditText m_search;
    ImageView m_cancel_search;

    final int[] piecolors = new int[]{
            Color.rgb(255,113,67),
            Color.rgb(190,61,66),
            Color.rgb(69,132,245),
            Color.rgb(245,197,11),
            Color.rgb(46,187,31),
            Color.rgb(91,177,238)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_summary);

        chart = findViewById(R.id.pie_chart_BS);

        m_rec_bs=findViewById(R.id.rec_BS);
        loaderdialog= DialogManager.getLoaderDialog(this);

        m_search=findViewById(R.id.search_TV);
        m_cancel_search=findViewById(R.id.cancel_search);

        businessModels=new ArrayList<>();
        businessChartModels=new ArrayList<>();

        getBusiness();
        getBusinessChart();

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
        m_rec_bs.setLayoutManager(layoutManager);

        businessAdapter = new BusinessAdapter(this, businessModels);
        m_rec_bs.setAdapter(businessAdapter);


    }

    private void addToPieChart() {

        // add to pie chart

     /*   chart.addPieSlice(new PieModel("Integer 1", i1, Color.parseColor("#FF7143"))); //  carrot
        chart.addPieSlice(new PieModel("Integer 2", i2, Color.parseColor("#BB3F3F"))); //  red
        chart.addPieSlice(new PieModel("Integer 3", i3, Color.parseColor("#4286F5"))); // dark blue
        chart.addPieSlice(new PieModel("Integer 4", i4, Color.parseColor("#F7C409"))); // yellow
        chart.addPieSlice(new PieModel("Integer 5", i5, Color.parseColor("#32B720"))); // green
        chart.addPieSlice(new PieModel("Integer 6", i6, Color.parseColor("#59B1F0"))); //blue

        chart.startAnimation();*/

       ArrayList<PieEntry> pieEntries= new ArrayList<>();

       for (int i=0; i< businessChartModels.size(); i++)
       {
           pieEntries.add(new PieEntry(Float.parseFloat(businessChartModels.get(i).getBuyerQty()),businessChartModels.get(i).getBuyerName()));
       }

        PieDataSet dataSet = new PieDataSet(pieEntries,"");
       dataSet.setColors(ColorTemplate.createColors(piecolors));
        dataSet.setValueTextSize(10f);

        PieData data= new PieData(dataSet);

        Log.e("mylog","business model size is : "+businessChartModels.size());
        chart.setData(data);
        chart.setEntryLabelTextSize(8f);

        Legend legend =chart.getLegend();

        legend.setTextSize(8);
        legend.setWordWrapEnabled(true);
        chart.getLegend().setEnabled(false);
        chart.animateXY(2000,2000);
        chart.getDescription().setEnabled(false);
        chart.invalidate();



    }

    public void getBusinessChart(){

        loaderdialog.show();

        String url = AppNetworkConstants.BUSINESSSUMMARYCHART;

        Log.e("mylog", "MY Bussiness chart Summary url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your Bussiness chart Summary response is :" + response);

                try {
                    BusinessChartResponseBean responseBean = BusinessChartResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            businessChartModels.clear();
                            businessChartModels.addAll(responseBean.ChartRecord);
                            addToPieChart();

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
                Toast.makeText(BusinessSummary.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void getBusiness(){

        loaderdialog.show();

        String url = AppNetworkConstants.BUSINESSSUMMARY;

        Log.e("mylog", "MY Bussiness Summary url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your Bussiness Summary response is :" + response);

                try {
                    BusinessResponseBean responseBean = BusinessResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            businessModels.clear();
                            businessModels.addAll(responseBean.TotalRecord);

                        }
                        businessAdapter.notifyDataSetChanged();
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
                Toast.makeText(BusinessSummary.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void filter(String text) {
        ArrayList<BusinessModel> filterlist=new ArrayList<>();
        for(BusinessModel item : businessModels)
        {
            if(item.getBuyer().toLowerCase().contains(text.toLowerCase())||
                    item.getBrand().toLowerCase().contains(text.toLowerCase())) {
                filterlist.add(item);
            }
        }
        businessAdapter.filterList(filterlist);
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