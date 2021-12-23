package com.erp.apparel.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.erp.apparel.Activities.BusinessSummary;
import com.erp.apparel.Activities.HomeActivity;
import com.erp.apparel.Activities.Otds;
import com.erp.apparel.Activities.PreOrder;
import com.erp.apparel.Activities.ProcessLight;
import com.erp.apparel.Activities.ProdutionReport;
import com.erp.apparel.Activities.ScoreCard;
import com.erp.apparel.Activities.SpoMgmt;
import com.erp.apparel.Activities.StyleInfo;
import com.erp.apparel.AppConstant.AppNetworkConstants;
import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.Data.Prefs;
import com.erp.apparel.Models.DashChartModel;
import com.erp.apparel.Models.HomeModel;
import com.erp.apparel.Models.HomeResponseBean;
import com.erp.apparel.Models.OtdsChartModel;
import com.erp.apparel.Models.OtdsChartResponseBean;
import com.erp.apparel.Models.ScoreCardModel;
import com.erp.apparel.Models.ScoreCardResponseBean;
import com.erp.apparel.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class DashFrag extends Fragment {

    private PieChart chart,progesschartView;
    LinearLayout m_styleinfo_ll,m_processlight_ll,m_business_ll,m_otds,m_scorecard_ll,m_produtionreport_ll,m_preorder_ll,m_spo_ll;

    HomeActivity homeActivity;
    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<DashChartModel> chartmodels;
   // ImageView m_navigation_icon;

    TextView m_totalstyle,m_projection,m_onorder,m_delayed,m_ongoing,m_upcoming;
    ArrayList<HomeModel> homeModels;
    Dialog loaderdialog;
    int factorySot = 0;
    int overallSot = 0;
    int cFair =0;
    int airPort =0;
    int seaPort =0;

    final int[] piecolors = new int[]{
            Color.rgb(232,90,89),
            Color.rgb(255,189,2),
            Color.rgb(50,184,37)
    };

    final int[] piecolorschart = new int[]{
            Color.rgb(30,143,221),
            Color.rgb(50,184,37),
            Color.rgb(255,189,2)

    };


    String upComing="";
    String delayed="";
    String onGoing="";

    ArrayList<OtdsChartModel> otdsChartModels;

    String fhoEarly="";
    String  fhoDelayed="";
    String fhoOntime="";
    String pcdEarly="";
    String pcdDelayed="";
    String pcdOntime="";

    ArrayList<ScoreCardModel> scoreCardModels;



    public DashFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_dash, container, false);

        homeActivity=new HomeActivity();

        loaderdialog= DialogManager.getLoaderDialog(getContext());

        Prefs.INSTANCE.setLoginStatus(true);
        barChart=view.findViewById(R.id.bar_chart_HOME);

    //    m_styleinfo_ll=view.findViewById(R.id.styleinfo_ll);
        m_processlight_ll=view.findViewById(R.id.processlight_ll);
        m_business_ll=view.findViewById(R.id.businesssummary_ll);
        m_otds=view.findViewById(R.id.otds_ll);
        m_scorecard_ll=view.findViewById(R.id.scorecard_ll);
      //  m_produtionreport_ll=view.findViewById(R.id.produtionreport_ll);
        m_preorder_ll=view.findViewById(R.id.preordercost_ll);
        m_spo_ll=view.findViewById(R.id.spomgmt_ll);

        m_totalstyle=view.findViewById(R.id.totalsale_HOME);
        m_projection=view.findViewById(R.id.projection_HOME);
        m_onorder=view.findViewById(R.id.onorder_HOME);
        m_delayed=view.findViewById(R.id.delayed_HOME);
        m_ongoing=view.findViewById(R.id.ongoing_HOME);
        m_upcoming=view.findViewById(R.id.upcoming_HOME);

        chart =view.findViewById(R.id.chart);

        chartmodels=new ArrayList<>();

        getDashChart();

        getDashBoard();
    //    progesschartView=view.findViewById(R.id.chartprocess);

      /*  m_navigation_icon=view.findViewById(R.id.navigation_icon);
        m_navigation_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.m_draw.openDrawer(GravityCompat.START);
            }
        });*/


        homeModels=new ArrayList<>();


     //   chart();
      //  progresschart();

        addToPieChart();


        otdsChartModels=new ArrayList<>();

        getBarChart();

        scoreCardModels =new ArrayList<>();

        getScore();

        barEntryArrayList=new ArrayList<>();

        getBarEntries();

        BarDataSet barDataSet=new BarDataSet(barEntryArrayList,"");
        barDataSet.setColors(ColorTemplate.createColors(piecolors));
        barDataSet.setValueTextColor(Color.parseColor("#FDFEFE"));
        Description description=new Description();
        description.setText("");
        barChart.setDescription(description);

        BarData barData =new BarData(barDataSet);
        barChart.setData(barData);

        XAxis xAxis=barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter());
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelRotationAngle(270);
        xAxis.setTextSize(8);
        xAxis.setTextColor(Color.parseColor("#FDFEFE"));
        xAxis.setGranularityEnabled(true);

        YAxis yAxis=barChart.getAxisLeft();
        yAxis.setTextColor(Color.parseColor("#FDFEFE"));

        YAxis yAxis1=barChart.getAxisRight();
        yAxis1.setTextColor(Color.parseColor("#FDFEFE"));

        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(2000);
        barChart.invalidate();


       /* m_styleinfo_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StyleInfo.class);
                startActivity(intent);

            }
        });*/

        m_processlight_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProcessLight.class);
                startActivity(intent);
            }
        });

        m_business_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BusinessSummary.class);
                startActivity(intent);
            }
        });

        m_otds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Otds.class);
                startActivity(intent);
            }
        });

        m_scorecard_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScoreCard.class);
                startActivity(intent);
            }
        });

       /* m_produtionreport_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProdutionReport.class);
                startActivity(intent);
            }
        });*/


        m_preorder_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PreOrder.class);
                startActivity(intent);
            }
        });

        m_spo_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SpoMgmt.class);
                startActivity(intent);
            }
        });



        return  view;
    }


    private void getBarEntries() {

        Log.e("Mylog","my Bar Chart Array "+homeModels.size());


        barEntryArrayList.add(new BarEntry(1f, Prefs.INSTANCE.getDelayed()));
        barEntryArrayList.add(new BarEntry(2f, Prefs.INSTANCE.getOngoing()));
        barEntryArrayList.add(new BarEntry(3f, Prefs.INSTANCE.getUpcoming()));

    }


   /* public void chart() {
        ArrayList<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(10, Color.parseColor("#32B725")).setLabel(""));  //left   green
        pieData.add(new SliceValue(10,Color.parseColor("#FFBF00")).setLabel(""));  // down  yellow
        pieData.add(new SliceValue(10,Color.parseColor("#208BD7")).setLabel(""));  // right  blue

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setHasCenterCircle(true).setCenterText1("").setCenterText1FontSize(10).setCenterText1Color(Color.parseColor("#ff4500"));
        pieChartView.setPieChartData(pieChartData);
    }*/

   /* public void progresschart() {
        ArrayList<SliceValue> pieData = new ArrayList<>();

        pieData.add(new SliceValue(10, Color.parseColor("#32B725")).setLabel(""));  // green
        pieData.add(new SliceValue(10, Color.parseColor("#BB3F3F")).setLabel(""));   // red
        pieData.add(new SliceValue(10, Color.parseColor("#FFBF00")).setLabel(""));   // yellow

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setHasCenterCircle(true).setCenterText1("Process").setCenterText1FontSize(10).setCenterText1Color(Color.parseColor("#99A3A4"));
        pieChartData.setHasCenterCircle(true).setCenterText2("Light System").setCenterText2FontSize(10).setCenterText2Color(Color.parseColor("#99A3A4"));
        progesschartView.setPieChartData(pieChartData);

    }*/


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

                            m_totalstyle.setText(totalStyle);
                            m_delayed.setText(delayed);
                            m_ongoing.setText(onGoing);
                            m_onorder.setText(onOrder);
                            m_projection.setText(projection);
                            m_upcoming.setText(upComing);
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
                Toast.makeText(getActivity(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
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
                Toast.makeText(getActivity(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
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
                Toast.makeText(getActivity(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void getDashChart() {
      chartmodels.add(new DashChartModel("","33"));
      chartmodels.add(new DashChartModel("","33"));
      chartmodels.add(new DashChartModel("","33"));
    }

    private void addToPieChart() {

        ArrayList<PieEntry> pieEntries= new ArrayList<>();

        for (int i=0; i< chartmodels.size(); i++)
        {
            pieEntries.add(new PieEntry(Float.parseFloat(chartmodels.get(i).getBuyerQty()),chartmodels.get(i).getBuyerName()));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries,"");
        dataSet.setColors(ColorTemplate.createColors(piecolorschart));
        dataSet.setValueTextSize(10f);
        dataSet.setValueTextColor(Color.parseColor("#00FFFFFF"));

        PieData data= new PieData(dataSet);

        Log.e("mylog","Dash model size is : "+chartmodels.size());
        chart.setData(data);
        chart.setEntryLabelTextSize(8f);

        Legend legend =chart.getLegend();

        legend.setTextSize(8);
        legend.setWordWrapEnabled(true);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.parseColor("#00FFFFFF"));
        chart.getLegend().setEnabled(false);
        chart.animateXY(2000,2000);
        chart.getDescription().setEnabled(false);
        chart.invalidate();
    }
}