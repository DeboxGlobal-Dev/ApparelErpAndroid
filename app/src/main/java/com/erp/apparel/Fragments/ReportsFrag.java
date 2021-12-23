package com.erp.apparel.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.erp.apparel.Activities.BusinessSummary;
import com.erp.apparel.Activities.Otds;
import com.erp.apparel.Activities.ScoreCard;
import com.erp.apparel.Adapter.BusinessAdapter;
import com.erp.apparel.Adapter.OtdsAdapter;
import com.erp.apparel.Adapter.ProcessLightFragAdapter;
import com.erp.apparel.Adapter.ProcessLightOngoingFragAdapter;
import com.erp.apparel.Adapter.ProcessLightUpcmoingFragAdapter;
import com.erp.apparel.Adapter.StyleInfoAdapter;
import com.erp.apparel.Adapter.ViewDelayedFragAdapter;
import com.erp.apparel.Adapter.ViewOngoingFragAdapter;
import com.erp.apparel.Adapter.ViewUpcomingFragAdapter;
import com.erp.apparel.AppConstant.AppNetworkConstants;
import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.Data.Prefs;
import com.erp.apparel.Interface.CompleteEvents;
import com.erp.apparel.Interface.PdfEvents;
import com.erp.apparel.Interface.ViewEvents;
import com.erp.apparel.Models.BusinessModel;
import com.erp.apparel.Models.BusinessResponseBean;
import com.erp.apparel.Models.HomeModel;
import com.erp.apparel.Models.HomeResponseBean;
import com.erp.apparel.Models.OtdsChartModel;
import com.erp.apparel.Models.OtdsModel;
import com.erp.apparel.Models.OtdsResponseBean;
import com.erp.apparel.Models.ProcessLightModel;
import com.erp.apparel.Models.ProcessLightOngoingModel;
import com.erp.apparel.Models.ProcessLightOngoingResponseBean;
import com.erp.apparel.Models.ProcessLightResponseBean;
import com.erp.apparel.Models.ProcessLightUpcomingModel;
import com.erp.apparel.Models.ProcessLightUpcomingResponseBean;
import com.erp.apparel.Models.ScoreCardModel;
import com.erp.apparel.Models.ScoreCardResponseBean;
import com.erp.apparel.Models.StyleInfoModel;
import com.erp.apparel.Models.StyleInfoResponseBean;
import com.erp.apparel.Models.ViewDelayedModel;
import com.erp.apparel.Models.ViewDelayedResponseBean;
import com.erp.apparel.Models.ViewOngoingModel;
import com.erp.apparel.Models.ViewOngoingResponseBean;
import com.erp.apparel.Models.ViewUpcomingModel;
import com.erp.apparel.Models.ViewUpcomingResponseBean;
import com.erp.apparel.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONObject;

import java.util.ArrayList;


public class ReportsFrag extends Fragment implements ViewEvents, CompleteEvents {

    BarChart barChart_quality, barChart_wip,barChart_sam;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<BarEntry> barEntries_wip;
    ArrayList<BarEntry> barEntries_sam;

    final int[] piecolors = new int[]{
            Color.rgb(0,116,189),
            Color.rgb(1,181,230),
    };

    final int[] piecolorswip = new int[]{
            Color.rgb(255,113,63),
            Color.rgb(25,171,2),
    };

    final int[] piecolorssam = new int[]{
            Color.rgb(255,113,63),
    };

     TextView m_bs, m_otds, m_scorecard, m_productionreport, m_processlightsystem, m_styleinfo;

    RecyclerView m_rec;
    LinearLayoutManager layoutManager;
    Dialog loaderdialog;

    private static final int PERMISSION_STORAGE_CODE=1000;
    String pdfnewurl="";

    LinearLayout m_processlightheader_ll,m_scoredata_ll,m_styleinfo_ll,m_productiondata_ll;

    TextView m_delayed,m_ongoing,m_upcoming,m_stick,m_bluestick_si;
    ArrayList<HomeModel> homeModels;
    LinearLayout m_delayed_ll,m_upcoming_ll,m_ongoing_ll,m_bstitle, m_otdstitle,m_scorecardtitle,m_prtitle;
    // RelativeLayout m_filter_ll;
    View m_view;

    String upcoming_id="";
    String ongoing_id="";
    String delayed_id="";
    String taskiddelayed="";
    String styledelayed="";

    String taskidongoing="";
    String styleongoing="";

    String taskidupcome="";
    String styleupcome="";
    String completeId="1";

    String fhoEarly="";
    String  fhoDelayed="";
    String fhoOntime="";
    String pcdEarly="";
    String pcdDelayed="";
    String pcdOntime="";

    ArrayList<ProcessLightModel> processLightModels;
    ArrayList<ProcessLightOngoingModel> processLightOngoingModels;
    ArrayList<ProcessLightUpcomingModel> processLightUpcomingModels;

    ArrayList<ViewDelayedModel> viewDelayedModels;
    ArrayList<ViewUpcomingModel> viewUpcomingModels;
    ArrayList<ViewOngoingModel> viewOngoingModels;

    ArrayList<BusinessModel> businessModels;

    ArrayList<OtdsModel> otdsModels;

    ArrayList<ScoreCardModel> scoreCardModels;

    ArrayList<StyleInfoModel> styleInfo;


    ProcessLightFragAdapter processLightFragAdapter;
    ProcessLightOngoingFragAdapter processLightOngoingFragAdapter;
    ProcessLightUpcmoingFragAdapter processLightUpcmoingFragAdapter;

    ViewDelayedFragAdapter viewDelayedFragAdapter;
    ViewUpcomingFragAdapter viewUpcomingFragAdapter;
    ViewOngoingFragAdapter viewOngoingFragAdapter;

    BusinessAdapter businessAdapter;

    OtdsAdapter otdsAdapter;

    StyleInfoAdapter adapter;

    public ReportsFrag() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_reports, container, false);

        loaderdialog= DialogManager.getLoaderDialog(getContext());

        m_processlightheader_ll=view.findViewById(R.id.processlight_header_ll);

        m_delayed=view.findViewById(R.id.delayed_PL);
        m_upcoming=view.findViewById(R.id.upcoming_PL);
        m_ongoing=view.findViewById(R.id.onoing_PL);

        m_delayed_ll=view.findViewById(R.id.delayed_ll);
        m_upcoming_ll=view.findViewById(R.id.upcoming_ll);
        m_ongoing_ll=view.findViewById(R.id.ongoing_ll);
        //   m_updation_ll=view.findViewById(R.id.taskupde_PL);
        m_view=view.findViewById(R.id.view_PL);
        m_stick=view.findViewById(R.id.stick_TV);
        m_scoredata_ll=view.findViewById(R.id.scoredata_ll);
        m_productiondata_ll=view.findViewById(R.id.produtiondata_ll);

        m_rec=view.findViewById(R.id.rec_APPROVE);


        m_bs= view.findViewById(R.id.bs_APPROVE);
        m_otds= view.findViewById(R.id.otds_APPROVE);
        m_scorecard= view.findViewById(R.id.scorecard_APPROVE);
        m_processlightsystem= view.findViewById(R.id.processlightsystem_APPROVE);
        m_productionreport= view.findViewById(R.id.productionreport_APPROVE);
        m_styleinfo= view.findViewById(R.id.styleinfo_APPROVE);
        m_bluestick_si=view.findViewById(R.id.bluetick_SI_REPORT);
        m_styleinfo_ll=view.findViewById(R.id.styleinfo_ll_REPORT);

        barChart_quality=view.findViewById(R.id.quality_chart_PR);
        barChart_wip=view.findViewById(R.id.wip_chart_PR);
        barChart_sam=view.findViewById(R.id.sam_chart_PR);

        m_bstitle=view.findViewById(R.id.bstitle_APPROVE);
        m_otdstitle=view.findViewById(R.id.otdstitle_APPROVE);
        m_scorecardtitle=view.findViewById(R.id.scorecardtitle_APPROVE);
        m_prtitle=view.findViewById(R.id.prtitle_APPROVE);

        homeModels=new ArrayList<>();

        processLightModels=new ArrayList<>();
        processLightOngoingModels=new ArrayList<>();
        processLightUpcomingModels=new ArrayList<>();

        viewDelayedModels=new ArrayList<>();
        viewOngoingModels=new ArrayList<>();
        viewUpcomingModels =new ArrayList<>();

        businessModels=new ArrayList<>();

        otdsModels=new ArrayList<>();

        scoreCardModels=new ArrayList<>();

        styleInfo=new ArrayList<>();

        barEntryArrayList=new ArrayList<>();

        barEntries_wip=new ArrayList<>();

        barEntries_sam=new ArrayList<>();

        m_bstitle.setVisibility(View.VISIBLE);
        m_otdstitle.setVisibility(View.GONE);
        m_scorecardtitle.setVisibility(View.GONE);
        m_prtitle.setVisibility(View.GONE);

        m_bs.setTextColor(Color.WHITE);
        m_bs.setBackgroundResource(R.drawable.blue_sqr_background);

        m_processlightheader_ll.setVisibility(View.GONE);
        m_stick.setVisibility(View.GONE);

        getBusiness();

        m_bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_bs.setTextColor(Color.WHITE);
                m_bs.setBackgroundResource(R.drawable.blue_sqr_background);

                m_otds.setTextColor(Color.BLACK);
                m_otds.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_scorecard.setTextColor(Color.BLACK);
                m_scorecard.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_productionreport.setTextColor(Color.BLACK);
                m_productionreport.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightsystem.setTextColor(Color.BLACK);
                m_processlightsystem.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_bstitle.setVisibility(View.VISIBLE);
                m_otdstitle.setVisibility(View.GONE);
                m_scorecardtitle.setVisibility(View.GONE);
                m_prtitle.setVisibility(View.GONE);

                m_styleinfo.setTextColor(Color.BLACK);
                m_styleinfo.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightheader_ll.setVisibility(View.GONE);
                m_stick.setVisibility(View.GONE);
                m_rec.setVisibility(View.VISIBLE);

                m_productiondata_ll.setVisibility(View.GONE);

                m_styleinfo_ll.setVisibility(View.GONE);
                m_bluestick_si.setVisibility(View.GONE);

                getBusiness();
            }
        });

        m_otds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_otds.setTextColor(Color.WHITE);
                m_otds.setBackgroundResource(R.drawable.blue_sqr_background);

                m_bs.setTextColor(Color.BLACK);
                m_bs.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_scorecard.setTextColor(Color.BLACK);
                m_scorecard.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_productionreport.setTextColor(Color.BLACK);
                m_productionreport.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightsystem.setTextColor(Color.BLACK);
                m_processlightsystem.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_bstitle.setVisibility(View.GONE);
                m_otdstitle.setVisibility(View.VISIBLE);
                m_scorecardtitle.setVisibility(View.GONE);
                m_prtitle.setVisibility(View.GONE);

                m_styleinfo.setTextColor(Color.BLACK);
                m_styleinfo.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightheader_ll.setVisibility(View.GONE);
                m_stick.setVisibility(View.GONE);
                m_rec.setVisibility(View.VISIBLE);
                m_productiondata_ll.setVisibility(View.GONE);

                m_styleinfo_ll.setVisibility(View.GONE);
                m_bluestick_si.setVisibility(View.GONE);
                getOTDS();

            }
        });

        m_scorecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_scorecard.setTextColor(Color.WHITE);
                m_scorecard.setBackgroundResource(R.drawable.blue_sqr_background);

                m_otds.setTextColor(Color.BLACK);
                m_otds.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_bs.setTextColor(Color.BLACK);
                m_bs.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_productionreport.setTextColor(Color.BLACK);
                m_productionreport.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightsystem.setTextColor(Color.BLACK);
                m_processlightsystem.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_bstitle.setVisibility(View.GONE);
                m_otdstitle.setVisibility(View.GONE);
                m_scorecardtitle.setVisibility(View.VISIBLE);
                m_prtitle.setVisibility(View.GONE);

                m_styleinfo.setTextColor(Color.BLACK);
                m_styleinfo.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightheader_ll.setVisibility(View.GONE);
                m_stick.setVisibility(View.GONE);

                m_scoredata_ll.setVisibility(View.VISIBLE);
                m_rec.setVisibility(View.GONE);
                m_productiondata_ll.setVisibility(View.GONE);

                m_styleinfo_ll.setVisibility(View.GONE);
                m_bluestick_si.setVisibility(View.GONE);
                getScore();

            }
        });

        m_productionreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_productionreport.setTextColor(Color.WHITE);
                m_productionreport.setBackgroundResource(R.drawable.blue_sqr_background);

                m_otds.setTextColor(Color.BLACK);
                m_otds.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_scorecard.setTextColor(Color.BLACK);
                m_scorecard.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_bs.setTextColor(Color.BLACK);
                m_bs.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightsystem.setTextColor(Color.BLACK);
                m_processlightsystem.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_bstitle.setVisibility(View.GONE);
                m_otdstitle.setVisibility(View.GONE);
                m_scorecardtitle.setVisibility(View.GONE);
                m_prtitle.setVisibility(View.VISIBLE);

                m_styleinfo.setTextColor(Color.BLACK);
                m_styleinfo.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightheader_ll.setVisibility(View.GONE);
                m_stick.setVisibility(View.GONE);

                m_scoredata_ll.setVisibility(View.GONE);
                m_rec.setVisibility(View.GONE);
                m_productiondata_ll.setVisibility(View.VISIBLE);

                m_styleinfo_ll.setVisibility(View.GONE);
                m_bluestick_si.setVisibility(View.GONE);

                getBarEntries();
                getBarEntrieswip();
                getBarEntriesSam();

                BarDataSet barDataSet=new BarDataSet(barEntryArrayList,"");
                barDataSet.setColors(ColorTemplate.createColors(piecolors));
                Description description=new Description();
                description.setText("");
                barChart_quality.setDescription(description);

                BarData barData =new BarData(barDataSet);
                barChart_quality.setData(barData);


                XAxis xAxis=barChart_quality.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter());
                xAxis.setGranularity(1f);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
                // xAxis.setLabelRotationAngle(270);
                xAxis.setTextSize(10);
                xAxis.setGranularityEnabled(true);

                barChart_quality.getLegend().setEnabled(false);
                barChart_quality.getDescription().setEnabled(false);
                barChart_quality.animateY(2000);
                barChart_quality.invalidate();



                BarDataSet barDataSet1=new BarDataSet(barEntries_wip,"");
                barDataSet1.setColors(ColorTemplate.createColors(piecolorswip));

                barChart_wip.setDescription(description);

                BarData barData1 =new BarData(barDataSet1);
                barChart_wip.setData(barData1);


                XAxis xAxis1=barChart_wip.getXAxis();
                xAxis1.setValueFormatter(new IndexAxisValueFormatter());
                xAxis1.setGranularity(1f);
                xAxis1.setDrawGridLines(false);
                xAxis1.setDrawAxisLine(false);
                // xAxis.setLabelRotationAngle(270);
                xAxis1.setTextSize(10);
                xAxis1.setGranularityEnabled(true);

                barChart_wip.getLegend().setEnabled(false);
                barChart_wip.getDescription().setEnabled(false);
                barChart_wip.animateY(2000);
                barChart_wip.invalidate();



                BarDataSet barDataSet2=new BarDataSet(barEntries_sam,"");
                barDataSet2.setColors(ColorTemplate.createColors(piecolorssam));

                barChart_sam.setDescription(description);

                BarData barData2 =new BarData(barDataSet2);
                barChart_sam.setData(barData2);


                XAxis xAxis2=barChart_sam.getXAxis();
                xAxis2.setValueFormatter(new IndexAxisValueFormatter());
                xAxis2.setGranularity(1f);
                xAxis2.setDrawGridLines(false);
                xAxis2.setDrawAxisLine(false);
                // xAxis.setLabelRotationAngle(270);
                xAxis2.setTextSize(10);
                xAxis2.setGranularityEnabled(true);

                barChart_sam.getLegend().setEnabled(false);
                barChart_sam.getDescription().setEnabled(false);
                barChart_sam.animateY(2000);
                barChart_sam.invalidate();


            }
        });

        m_processlightsystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_processlightsystem.setTextColor(Color.WHITE);
                m_processlightsystem.setBackgroundResource(R.drawable.blue_sqr_background);

                m_otds.setTextColor(Color.BLACK);
                m_otds.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_scorecard.setTextColor(Color.BLACK);
                m_scorecard.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_productionreport.setTextColor(Color.BLACK);
                m_productionreport.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_bs.setTextColor(Color.BLACK);
                m_bs.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_styleinfo.setTextColor(Color.BLACK);
                m_styleinfo.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightheader_ll.setVisibility(View.VISIBLE);
                m_stick.setVisibility(View.VISIBLE);

                m_bstitle.setVisibility(View.GONE);
                m_otdstitle.setVisibility(View.GONE);
                m_scorecardtitle.setVisibility(View.GONE);
                m_prtitle.setVisibility(View.GONE);

             //   m_preordercosttitle.setVisibility(View.GONE);
            //    m_spomgmttitle.setVisibility(View.GONE);

                //    m_updation_ll.setVisibility(View.GONE);
                //   m_filter_ll.setVisibility(View.VISIBLE);
                m_scoredata_ll.setVisibility(View.GONE);
                m_rec.setVisibility(View.VISIBLE);
                m_rec.setBackgroundColor(Color.parseColor("#FDFEFE"));
                getDashBoard();
                getProcess();


                m_delayed.setBackgroundResource(R.drawable.whitered_background);
                m_upcoming.setBackgroundResource(R.drawable.whitegray_background);
                m_ongoing.setBackgroundResource(R.drawable.whitegray_background);

                m_stick.setBackgroundResource(R.drawable.red_background);

                m_styleinfo_ll.setVisibility(View.GONE);
                m_bluestick_si.setVisibility(View.GONE);

            }
        });

        m_styleinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_styleinfo.setTextColor(Color.WHITE);
                m_styleinfo.setBackgroundResource(R.drawable.blue_sqr_background);

                m_otds.setTextColor(Color.BLACK);
                m_otds.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_scorecard.setTextColor(Color.BLACK);
                m_scorecard.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_productionreport.setTextColor(Color.BLACK);
                m_productionreport.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightsystem.setTextColor(Color.BLACK);
                m_processlightsystem.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_bs.setTextColor(Color.BLACK);
                m_bs.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightheader_ll.setVisibility(View.GONE);
                m_stick.setVisibility(View.GONE);
                m_scoredata_ll.setVisibility(View.GONE);
                m_rec.setVisibility(View.VISIBLE);

                m_styleinfo_ll.setVisibility(View.VISIBLE);
                m_bluestick_si.setVisibility(View.VISIBLE);

                m_bstitle.setVisibility(View.GONE);
                m_otdstitle.setVisibility(View.GONE);
                m_scorecardtitle.setVisibility(View.GONE);
                m_prtitle.setVisibility(View.GONE);

                getStyleInfo();

            }
        });

        m_delayed_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProcess();

                m_delayed.setBackgroundResource(R.drawable.whitered_background);
                m_upcoming.setBackgroundResource(R.drawable.whitegray_background);
                m_ongoing.setBackgroundResource(R.drawable.whitegray_background);

                m_stick.setBackgroundResource(R.drawable.red_background);

              /*  m_search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {

                        filterDelayed(s.toString());


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
                });*/

            }
        });

        m_upcoming_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProcessUpcoming();

                m_delayed.setBackgroundResource(R.drawable.whitegray_background);
                m_upcoming.setBackgroundResource(R.drawable.whitegreen_background);
                m_ongoing.setBackgroundResource(R.drawable.whitegray_background);

                m_stick.setBackgroundResource(R.drawable.green_background);

               /* m_search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {

                        filterUpcoming(s.toString());

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
                });*/

            }
        });

        m_ongoing_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProcessOngoing();

                m_delayed.setBackgroundResource(R.drawable.whitegray_background);
                m_upcoming.setBackgroundResource(R.drawable.whitegray_background);
                m_ongoing.setBackgroundResource(R.drawable.whiteyellow_background);

                m_stick.setBackgroundResource(R.drawable.yellow_background);

              /*  m_search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {

                        filterOngoing(s.toString());

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
                });*/

            }
        });

        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        m_rec.setLayoutManager(layoutManager);

       return view;
    }

    public void getProcess(){

        loaderdialog.show();

        String url = AppNetworkConstants.PROCESSLIGHTSYSTEM;

        Log.e("mylog", "MY Process Light System url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your Process Light System response is :" + response);

                try {
                    ProcessLightResponseBean responseBean = ProcessLightResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            processLightModels.clear();
                            processLightModels.addAll(responseBean.TotalRecord);

                        }
                        //  m_updation_ll.setVisibility(View.GONE);
                        //  m_filter_ll.setVisibility(View.VISIBLE);
                        m_rec.setBackgroundColor(Color.parseColor("#FDFEFE"));
                        processLightFragAdapter = new ProcessLightFragAdapter(getContext(),ReportsFrag.this, processLightModels);
                        m_rec.setAdapter(processLightFragAdapter);
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
                Toast.makeText(getContext(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void getProcessOngoing(){

        loaderdialog.show();

        String url = AppNetworkConstants.PROCESSLIGHTSYSTEMONGOING;

        Log.e("mylog", "MY Process Light System ongoing url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your Process Light System ongoing response is :" + response);

                try {
                    ProcessLightOngoingResponseBean responseBean = ProcessLightOngoingResponseBean.fromJson(response);
                    if (responseBean != null) {
                        if (responseBean.Status.equals("0")) {
                            processLightOngoingModels.clear();
                            processLightOngoingModels.addAll(responseBean.TotalRecord);

                        }
                        //    m_updation_ll.setVisibility(View.GONE);
                        //   m_filter_ll.setVisibility(View.VISIBLE);
                        m_rec.setBackgroundColor(Color.parseColor("#FDFEFE"));
                        processLightOngoingFragAdapter = new ProcessLightOngoingFragAdapter(getContext(),ReportsFrag.this, processLightOngoingModels);
                        m_rec.setAdapter(processLightOngoingFragAdapter);
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
                Toast.makeText(getContext(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void getProcessUpcoming(){

        loaderdialog.show();

        String url = AppNetworkConstants.PROCESSLIGHTSYSTEMUPCOMING;

        Log.e("mylog", "MY Process Light System upcoming url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your Process Light System upcoming response is :" + response);

                try {
                    ProcessLightUpcomingResponseBean responseBean = ProcessLightUpcomingResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            processLightUpcomingModels.clear();
                            processLightUpcomingModels.addAll(responseBean.TotalRecord);

                        }
                        //  m_updation_ll.setVisibility(View.GONE);
                        //   m_filter_ll.setVisibility(View.VISIBLE);
                        m_rec.setBackgroundColor(Color.parseColor("#FDFEFE"));
                        processLightUpcmoingFragAdapter = new ProcessLightUpcmoingFragAdapter(getContext(),ReportsFrag.this, processLightUpcomingModels);
                        m_rec.setAdapter(processLightUpcmoingFragAdapter);
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
                Toast.makeText(getContext(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


    public void getViewDelayeds(){

        loaderdialog.show();

        String url = AppNetworkConstants.VIEWDELAYED +"id="+delayed_id;

        Log.e("mylog", "MY view delayed url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your view delayed response is :" + response);

                try {
                    ViewDelayedResponseBean responseBean = ViewDelayedResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            viewDelayedModels.clear();
                            viewDelayedModels.addAll(responseBean.TotalRecord);

                            if(viewDelayedModels.size()==0)
                            {
                                Toast.makeText(getContext(),"No Data Available Now",Toast.LENGTH_SHORT).show();
                            }

                        }
                        //    m_updation_ll.setVisibility(View.VISIBLE);
                        //      m_filter_ll.setVisibility(View.GONE);

                        m_rec.setBackgroundColor(Color.parseColor("#EDEDED"));

                        viewDelayedFragAdapter = new ViewDelayedFragAdapter(getContext(), ReportsFrag.this ,viewDelayedModels);
                        m_rec.setAdapter(viewDelayedFragAdapter);

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
                Toast.makeText(getContext(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void getViewOngoings(){

        loaderdialog.show();

        String url = AppNetworkConstants.VIEWONGOING +"id="+ongoing_id;

        Log.e("mylog", "MY view going url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your view going response is :" + response);

                try {
                    ViewOngoingResponseBean responseBean = ViewOngoingResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            viewOngoingModels.clear();
                            viewOngoingModels.addAll(responseBean.TotalRecord);

                            if(viewOngoingModels.size()==0)
                            {
                                Toast.makeText(getContext(),"No Data Available Now",Toast.LENGTH_SHORT).show();
                            }

                        }
                        //   m_updation_ll.setVisibility(View.VISIBLE);
                        //     m_filter_ll.setVisibility(View.GONE);

                        m_rec.setBackgroundColor(Color.parseColor("#EDEDED"));

                        viewOngoingFragAdapter = new ViewOngoingFragAdapter(getContext(),ReportsFrag.this, viewOngoingModels);
                        m_rec.setAdapter(viewOngoingFragAdapter);
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
                Toast.makeText(getContext(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void getViewUpcomings(){

        loaderdialog.show();

        String url = AppNetworkConstants.VIEWUPCOMING +"id="+upcoming_id;

        Log.e("mylog", "MY view upcoming url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your view upcoming response is :" + response);

                try {
                    ViewUpcomingResponseBean responseBean = ViewUpcomingResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            viewUpcomingModels.clear();
                            viewUpcomingModels.addAll(responseBean.TotalRecord);

                            if (viewUpcomingModels.size() == 0) {
                                Toast.makeText(getContext(), "No Data Available Now", Toast.LENGTH_SHORT).show();
                            }
                        }

                        //     m_updation_ll.setVisibility(View.VISIBLE);
                        //  m_filter_ll.setVisibility(View.GONE);

                        m_rec.setBackgroundColor(Color.parseColor("#EDEDED"));


                        viewUpcomingFragAdapter = new ViewUpcomingFragAdapter(getContext(), ReportsFrag.this, viewUpcomingModels);
                        m_rec.setAdapter(viewUpcomingFragAdapter);

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
                Toast.makeText(getContext(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


    private void delayedCompleted() {

        loaderdialog.show();

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            String URL = AppNetworkConstants.DELAYEDCOMPLETED;

            Log.e("mylog","Url is : "+URL);
            Log.e("mylog","Delayed task id is : "+taskiddelayed);
            Log.e("mylog","Delayed style is : "+styledelayed);
            Log.e("mylog","Delayed complete id is : "+completeId);

            JsonObjectRequest dataRequest = new JsonObjectRequest(Request.Method.POST,URL,
                    new JSONObject().put("taskId",taskiddelayed).put("style",styledelayed).put("completeId",completeId),
                    response ->
                    {
                        loaderdialog.dismiss();
                        try {
                            Log.e("mylog","delayed completed response is : "+response);
                            getViewDelayeds();
                            String message= response.getString("Message");


                            Log.e("my log"," id is :" +message);
                            Toast.makeText(getContext(), message,Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        loaderdialog.dismiss();
                        Log.e("my log"," Invalid Credentials :");
                        Toast.makeText(getContext(), "Invalid Credentials ",Toast.LENGTH_SHORT).show();
                    });
            dataRequest.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(dataRequest);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void onTimeCompleted() {

        loaderdialog.show();

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            String URL = AppNetworkConstants.ONTIMECOMPLETED;

            Log.e("mylog","Url is : "+URL);
            Log.e("mylog","On Time task id is : "+taskidongoing);
            Log.e("mylog","On Time style is : "+styleongoing);
            Log.e("mylog","On Time complete id is : "+completeId);

            JsonObjectRequest dataRequest = new JsonObjectRequest(Request.Method.POST,URL,
                    new JSONObject().put("taskId",taskidongoing).put("style",styleongoing).put("completeId",completeId),
                    response ->
                    {
                        loaderdialog.dismiss();
                        try {
                            Log.e("mylog","On Time completed response is : "+response);
                            getViewOngoings();
                            String message= response.getString("Message");


                            Log.e("my log"," id is :" +message);
                            Toast.makeText(getContext(), message,Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        loaderdialog.dismiss();
                        Log.e("my log"," Invalid Credentials :");
                        Toast.makeText(getContext(), "Invalid Credentials ",Toast.LENGTH_SHORT).show();
                    });
            dataRequest.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(dataRequest);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void upComingCompleted() {

        loaderdialog.show();

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            String URL = AppNetworkConstants.UPCOMINGCOMPLETED;

            Log.e("mylog","Url is : "+URL);
            Log.e("mylog","Upcoming task id is : "+taskidupcome);
            Log.e("mylog","Upcoming style is : "+styleupcome);
            Log.e("mylog","Upcoming complete id is : "+completeId);

            JsonObjectRequest dataRequest = new JsonObjectRequest(Request.Method.POST,URL,
                    new JSONObject().put("taskId",taskidupcome).put("style",styleupcome).put("completeId",completeId),
                    response ->
                    {
                        loaderdialog.dismiss();
                        try {
                            Log.e("mylog","Upcoming completed response is : "+response);
                            getViewUpcomings();
                            String message= response.getString("Message");


                            Log.e("my log"," id is :" +message);
                            Toast.makeText(getContext(), message,Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        loaderdialog.dismiss();
                        Log.e("my log"," Invalid Credentials :");
                        Toast.makeText(getContext(), "Invalid Credentials ",Toast.LENGTH_SHORT).show();
                    });
            dataRequest.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(dataRequest);

        }catch (Exception e){
            e.printStackTrace();
        }
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

                            if(onGoing.length()!=1)
                            {
                                m_ongoing.setText(onGoing);
                                Log.e("mylog"," message are" +m_upcoming);
                            }else {
                                m_ongoing.setText("0"+onGoing);
                                Log.e("mylog"," message are" +m_upcoming);
                            }

                            if(delayed.length()!=1)
                            {
                                m_delayed.setText(delayed);
                            }else {
                                m_delayed.setText("0"+delayed);
                            }

                            if(upComing.length()!=1)
                            {
                                m_upcoming.setText(upComing);
                            }else {
                                m_upcoming.setText("0"+upComing);
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
                Toast.makeText(getContext(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                        m_rec.setBackgroundColor(Color.parseColor("#FDFEFE"));
                        businessAdapter = new BusinessAdapter(getContext(), businessModels);
                        m_rec.setAdapter(businessAdapter);
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
                Toast.makeText(getContext(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                        otdsAdapter = new OtdsAdapter(getContext(), otdsModels);
                        m_rec.setBackgroundColor(Color.parseColor("#FDFEFE"));
                        m_rec.setAdapter(otdsAdapter);
                        //otdsAdapter.notifyDataSetChanged();
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
                Toast.makeText(getContext(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                Toast.makeText(getContext(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
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
                        adapter = new StyleInfoAdapter(getActivity(), styleInfo);
                        m_rec.setBackgroundColor(Color.parseColor("#FDFEFE"));
                        m_rec.setAdapter(adapter);
                     //   adapter.notifyDataSetChanged();
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
                Toast.makeText(getContext(), "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void getBarEntries() {

        Log.e("Mylog","my Bar Chart Array ");
        //  Log.e("Mylog","my facorry sot "+factorySot);

        barEntryArrayList.add(new BarEntry(1f, 50));
        barEntryArrayList.add(new BarEntry(2f, 30));
        barEntryArrayList.add(new BarEntry(3f, 25));
        barEntryArrayList.add(new BarEntry(4f, 10));
        barEntryArrayList.add(new BarEntry(5f, 25));
        barEntryArrayList.add(new BarEntry(6f, 30));
        barEntryArrayList.add(new BarEntry(7f, 25));
        barEntryArrayList.add(new BarEntry(8f, 10));
        barEntryArrayList.add(new BarEntry(9f, 15));
        barEntryArrayList.add(new BarEntry(10f, 20));


    }

    private void getBarEntrieswip() {

        Log.e("Mylog","my Bar Chart Array wip ");
        //  Log.e("Mylog","my facorry sot "+factorySot);

        barEntries_wip.add(new BarEntry(1f, 50));
        barEntries_wip.add(new BarEntry(2f, 30));
        barEntries_wip.add(new BarEntry(3f, 25));
        barEntries_wip.add(new BarEntry(4f, 10));
        barEntries_wip.add(new BarEntry(5f, 25));
        barEntries_wip.add(new BarEntry(6f, 30));
        barEntries_wip.add(new BarEntry(7f, 25));
        barEntries_wip.add(new BarEntry(8f, 10));
        barEntries_wip.add(new BarEntry(9f, 15));
        barEntries_wip.add(new BarEntry(10f, 20));


    }

    private void getBarEntriesSam() {

        Log.e("Mylog","my Bar Chart Array sam ");
        //  Log.e("Mylog","my facorry sot "+factorySot);

        barEntries_sam.add(new BarEntry(1f, 50));
        barEntries_sam.add(new BarEntry(2f, 30));
        barEntries_sam.add(new BarEntry(3f, 25));
        barEntries_sam.add(new BarEntry(4f, 10));
        barEntries_sam.add(new BarEntry(5f, 25));
        barEntries_sam.add(new BarEntry(6f, 30));
        barEntries_sam.add(new BarEntry(7f, 25));
        barEntries_sam.add(new BarEntry(8f, 10));
        barEntries_sam.add(new BarEntry(9f, 15));
        barEntries_sam.add(new BarEntry(10f, 20));


    }


    @Override
    public void onDelayedClick(ProcessLightModel demo, int positon) {
        delayed_id=demo.getId();
        getViewDelayeds();
    }

    @Override
    public void onOngoingClick(ProcessLightOngoingModel demo, int positon) {
        ongoing_id=demo.getNameid();
        getViewOngoings();
    }

    @Override
    public void onUpcomingClick(ProcessLightUpcomingModel demo, int positon) {
        upcoming_id=demo.getNameid();
        getViewUpcomings();
    }

    @Override
    public void onDelayedCompleteClick(ViewDelayedModel demo, int positon) {
        taskiddelayed=demo.getTaskid();
        styledelayed=demo.getStyle();
        delayedCompleted();
    }

    @Override
    public void onOnTimeCompleteClick(ViewOngoingModel demo, int positon) {
        taskidongoing=demo.getTaskid();
        styleongoing=demo.getStyle();
        onTimeCompleted();
    }

    @Override
    public void onUpcomingCompleteClick(ViewUpcomingModel demo, int positon) {
        taskidupcome=demo.getTaskid();
        styleupcome=demo.getStyle();
        upComingCompleted();
    }

}