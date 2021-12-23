package com.erp.apparel.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.erp.apparel.Adapter.OtdsAdapter;
import com.erp.apparel.AppConstant.AppNetworkConstants;
import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.Data.Prefs;
import com.erp.apparel.Models.BusinessResponseBean;
import com.erp.apparel.Models.OtdsChartModel;
import com.erp.apparel.Models.OtdsChartResponseBean;
import com.erp.apparel.Models.OtdsModel;
import com.erp.apparel.Models.OtdsResponseBean;
import com.erp.apparel.Models.StyleInfoModel;
import com.erp.apparel.R;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Otds extends AppCompatActivity {

    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<OtdsChartModel> otdsChartModels;
    Dialog loaderdialog;
    RecyclerView m_rec_otds;
    OtdsAdapter otdsAdapter;
    LinearLayoutManager layoutManager;
    ArrayList<OtdsModel> otdsModels;
    EditText m_search;
    ImageView m_cancel_search;

    String labels[]={"","Factory SOT","OverAll SOT","CFair","AirPort","SeaPort"};

    int factorySot = 0;
    int overallSot = 0;
    int cFair =0;
    int airPort =0;
    int seaPort =0;

    final int[] piecolors = new int[]{
            Color.rgb(89,177,240),
            Color.rgb(50,184,37),
            Color.rgb(247,196,9),
            Color.rgb(255,113,67),
            Color.rgb(190,61,66)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otds);

        m_search=findViewById(R.id.search_TV);
        m_cancel_search=findViewById(R.id.cancel_search);

        m_rec_otds=findViewById(R.id.rec_OTDS);
        loaderdialog= DialogManager.getLoaderDialog(this);

        barChart=findViewById(R.id.bar_chart_OTDS);
      //  barEntryArrayList=new ArrayList<>();
        barEntryArrayList=new ArrayList<>();
        otdsChartModels=new ArrayList<>();
        getBarChart();

        otdsModels =new ArrayList<>();

        getOTDS();


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
        m_rec_otds.setLayoutManager(layoutManager);

        otdsAdapter = new OtdsAdapter(this, otdsModels);
        m_rec_otds.setAdapter(otdsAdapter);



        getBarEntries();

       /* for(int i=0; i< otdsChartModels.size(); i++)
        {
            barEntryArrayList.add(new BarEntry(i,Integer.parseInt(otdsChartModels.get(i).getNo())));
            isLabled.add(otdsChartModels.get(i).getName());
        }*/

        BarDataSet barDataSet=new BarDataSet(barEntryArrayList,"");
        barDataSet.setColors(ColorTemplate.createColors(piecolors));
        Description description=new Description();
        description.setText("");
        barChart.setDescription(description);

        BarData barData =new BarData(barDataSet);
        barChart.setData(barData);

        XAxis xAxis=barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelRotationAngle(270);
        xAxis.setTextSize(8);
        xAxis.setGranularityEnabled(true);

      //  XAxis xAxis=barChart.getXAxis();

       /* xAxis.setValueFormatter(new IndexAxisValueFormatter(isLabled));

        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(isLabled.size());
        xAxis.setLabelRotationAngle(270);*/
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(2000);
        barChart.invalidate();

    }

    private void getBarEntries() {

        Log.e("Mylog","my Bar Chart Array "+otdsChartModels.size());
        Log.e("Mylog","my facorry sot "+factorySot);

        barEntryArrayList.add(new BarEntry(1f, Prefs.INSTANCE.getFactorysot()));
        barEntryArrayList.add(new BarEntry(2f, Prefs.INSTANCE.getOverallsot()));
        barEntryArrayList.add(new BarEntry(3f, Prefs.INSTANCE.getCfair()));


    }

    public void getBarChart() {
        loaderdialog.show();

        String url = AppNetworkConstants.OTDSCHART;

        Log.e("mylog", "MY OTDS Chart url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your OTDS Chart response is :" + response);

                try {
                    OtdsChartResponseBean responseBean = OtdsChartResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            otdsChartModels.clear();
                            otdsChartModels.addAll(responseBean.ChartRecord);

                             factorySot = otdsChartModels.get(0).getFactorySot();
                             overallSot = otdsChartModels.get(0).getOverallSot();
                             cFair = otdsChartModels.get(0).getCFair();
                             airPort = otdsChartModels.get(0).getAirPort();
                             seaPort =otdsChartModels.get(0).getSeaPort();

                            Prefs.INSTANCE.setFactorysot(factorySot);
                            Prefs.INSTANCE.setOverallsot(overallSot);
                            Prefs.INSTANCE.setCfair(cFair);
                            Prefs.INSTANCE.setAirport(airPort);
                            Prefs.INSTANCE.setSeaport(seaPort);

                            Log.e("Mylog","my facorry sot "+factorySot);

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
                Toast.makeText(Otds.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void getOTDS() {

        loaderdialog.show();

        String url = AppNetworkConstants.OTDS;

        Log.e("mylog", "MY OTDS url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your OTDS response is :" + response);

                try {
                    OtdsResponseBean responseBean = OtdsResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            otdsModels.clear();
                            otdsModels.addAll(responseBean.TotalRecord);

                        }
                        otdsAdapter.notifyDataSetChanged();
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
                Toast.makeText(Otds.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void filter(String text) {
        ArrayList<OtdsModel> filterlist=new ArrayList<>();
        for(OtdsModel item : otdsModels)
        {
            if(item.getBrandSOT().toLowerCase().contains(text.toLowerCase())||
                    item.getFactorySOT().toLowerCase().contains(text.toLowerCase()) ||
                  //  item.getPO().toLowerCase().contains(text.toLowerCase()) ||
                    item.getStyle().toLowerCase().contains(text.toLowerCase()) )
            {
                filterlist.add(item);
            }
        }
        otdsAdapter.filterList(filterlist);
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