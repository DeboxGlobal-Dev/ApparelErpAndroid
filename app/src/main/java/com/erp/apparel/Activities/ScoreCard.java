package com.erp.apparel.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.erp.apparel.AppConstant.AppNetworkConstants;
import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.Data.Prefs;
import com.erp.apparel.Models.OtdsChartModel;
import com.erp.apparel.Models.OtdsChartResponseBean;
import com.erp.apparel.Models.ScoreCardModel;
import com.erp.apparel.Models.ScoreCardResponseBean;
import com.erp.apparel.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ScoreCard extends AppCompatActivity {

    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<ScoreCardModel> scoreCardModels;
    Dialog loaderdialog;

    TextView m_earlyfho, m_ontimefho, m_delayedfho, m_earlypcd, m_ontimepcd,m_delayedpcd;

    String fhoEarly="";
    String  fhoDelayed="";
    String fhoOntime="";
    String pcdEarly="";
    String pcdDelayed="";
    String pcdOntime="";

    String labels[]={"","FHO","PCD","FHO","PCD","FHO","PCD"};

    final int[] piecolors = new int[]{
            Color.rgb(74,217,63),
            Color.rgb(50,184,37),
            Color.rgb(120,120,120),
            Color.rgb(65,65,67),
            Color.rgb(221,101,102),
            Color.rgb(187,63,63)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        loaderdialog= DialogManager.getLoaderDialog(this);

        m_earlyfho=findViewById(R.id.earlyfho_SC);
        m_ontimefho=findViewById(R.id.ontimefho_SC);
        m_delayedfho=findViewById(R.id.delayedfho_SC);
        m_earlypcd=findViewById(R.id.earlypcd_SC);
        m_ontimepcd=findViewById(R.id.ontimepcd_SC);
        m_delayedpcd=findViewById(R.id.delayedpcd_SC);

        barChart=findViewById(R.id.bar_chart_SC);

        scoreCardModels=new ArrayList<>();
        getScore();

        m_earlyfho.setText(Prefs.INSTANCE.getFhoEarly());
        m_ontimefho.setText(Prefs.INSTANCE.getFhoOntime());
        m_delayedfho.setText(Prefs.INSTANCE.getFhoDelay());

        m_earlypcd.setText(Prefs.INSTANCE.getPcdEarly());
        m_ontimepcd.setText(Prefs.INSTANCE.getPcdOntime());
        m_delayedpcd.setText(Prefs.INSTANCE.getPcdDelay());

        barEntryArrayList=new ArrayList<>();
        getBarEntries();

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
       // xAxis.setLabelRotationAngle(270);
        xAxis.setTextSize(10);
        xAxis.setGranularityEnabled(true);

        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(2000);
        barChart.invalidate();




    }


    private void getBarEntries() {

        Log.e("Mylog","my Bar Chart Array "+scoreCardModels.size());
      //  Log.e("Mylog","my facorry sot "+factorySot);

        barEntryArrayList.add(new BarEntry(1f, Integer.parseInt(Prefs.INSTANCE.getFhoEarly())));
        barEntryArrayList.add(new BarEntry(2f, Integer.parseInt(Prefs.INSTANCE.getPcdEarly())));
        barEntryArrayList.add(new BarEntry(3f, Integer.parseInt(Prefs.INSTANCE.getFhoOntime())));
        barEntryArrayList.add(new BarEntry(4f, Integer.parseInt(Prefs.INSTANCE.getPcdOntime())));
        barEntryArrayList.add(new BarEntry(5f, Integer.parseInt(Prefs.INSTANCE.getFhoDelay())));
        barEntryArrayList.add(new BarEntry(6f, Integer.parseInt(Prefs.INSTANCE.getPcdDelay())));


    }

    public void getScore() {
        loaderdialog.show();

        String url = AppNetworkConstants.SCORECARD;

        Log.e("mylog", "MY SCORECARD url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your scorecard response is :" + response);

                try {
                    ScoreCardResponseBean responseBean = ScoreCardResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            scoreCardModels.clear();
                            scoreCardModels.addAll(responseBean.ChartRecord);


                            pcdDelayed = scoreCardModels.get(0).getPcdDelayed();
                            pcdEarly = scoreCardModels.get(0).getPcdEarly();
                            pcdOntime = scoreCardModels.get(0).getPcdOntime();
                            fhoEarly = scoreCardModels.get(0).getFhoEarly();
                            fhoOntime = scoreCardModels.get(0).getFhoOntime();
                            fhoDelayed = scoreCardModels.get(0).getFhoDelayed();

                            Prefs.INSTANCE.setPcddelay(pcdDelayed);
                            Prefs.INSTANCE.setPcdEarly(pcdEarly);
                            Prefs.INSTANCE.setPcdOntime(pcdOntime);
                            Prefs.INSTANCE.setFhodelay(fhoDelayed);
                            Prefs.INSTANCE.setFhoEarly(fhoEarly);
                            Prefs.INSTANCE.setFhoOntime(fhoOntime);

                            Log.e("Mylog","my facorry sot ");

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
                Toast.makeText(ScoreCard.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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