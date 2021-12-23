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
import com.erp.apparel.Adapter.ProcessLightAdapter;
import com.erp.apparel.Adapter.ProcessLightOngoingAdapter;
import com.erp.apparel.Adapter.ProcessLightUpcmoingAdapter;
import com.erp.apparel.Adapter.ViewDelayedAdapter;
import com.erp.apparel.Adapter.ViewOngoingAdapter;
import com.erp.apparel.Adapter.ViewUpcomingAdapter;
import com.erp.apparel.AppConstant.AppNetworkConstants;
import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.Data.Prefs;
import com.erp.apparel.Interface.CompleteEvents;
import com.erp.apparel.Interface.ViewEvents;
import com.erp.apparel.Models.HomeModel;
import com.erp.apparel.Models.HomeResponseBean;
import com.erp.apparel.Models.ProcessLightModel;
import com.erp.apparel.Models.ProcessLightOngoingModel;
import com.erp.apparel.Models.ProcessLightOngoingResponseBean;
import com.erp.apparel.Models.ProcessLightResponseBean;
import com.erp.apparel.Models.ProcessLightUpcomingModel;
import com.erp.apparel.Models.ProcessLightUpcomingResponseBean;
import com.erp.apparel.Models.ViewDelayedModel;
import com.erp.apparel.Models.ViewDelayedResponseBean;
import com.erp.apparel.Models.ViewOngoingModel;
import com.erp.apparel.Models.ViewOngoingResponseBean;
import com.erp.apparel.Models.ViewUpcomingModel;
import com.erp.apparel.Models.ViewUpcomingResponseBean;
import com.erp.apparel.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class ProcessLight extends AppCompatActivity implements ViewEvents, CompleteEvents {

    ArrayList<ProcessLightModel> processLightModels;
    ArrayList<ProcessLightOngoingModel> processLightOngoingModels;
    ArrayList<ProcessLightUpcomingModel> processLightUpcomingModels;

    ArrayList<ViewDelayedModel> viewDelayedModels;
    ArrayList<ViewUpcomingModel> viewUpcomingModels;
    ArrayList<ViewOngoingModel> viewOngoingModels;

    Dialog loaderdialog;
    RecyclerView m_rec_pl;
    ProcessLightAdapter processLightAdapter;
    ProcessLightOngoingAdapter processLightOngoingAdapter;
    ProcessLightUpcmoingAdapter processLightUpcmoingAdapter;

    ViewDelayedAdapter viewDelayedAdapter;
    ViewOngoingAdapter viewOngoingAdapter;
    ViewUpcomingAdapter viewUpcomingAdapter;

    LinearLayoutManager layoutManager;
    TextView m_delayed,m_ongoing,m_upcoming,m_stick;
    ArrayList<HomeModel> homeModels;
    LinearLayout m_delayed_ll,m_upcoming_ll,m_ongoing_ll,m_updation_ll;
    RelativeLayout m_filter_ll;
    View m_view;

    EditText m_search;
    ImageView m_cancel_search;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_light);
        m_rec_pl=findViewById(R.id.processlight_REC);
        loaderdialog= DialogManager.getLoaderDialog(this);

        m_search=findViewById(R.id.search_TV);
        m_cancel_search=findViewById(R.id.cancel_search);

        m_filter_ll=findViewById(R.id.filter_ll);

        m_delayed=findViewById(R.id.delayed_PL);
        m_upcoming=findViewById(R.id.upcoming_PL);
        m_ongoing=findViewById(R.id.onoing_PL);

        m_delayed_ll=findViewById(R.id.delayed_ll);
        m_upcoming_ll=findViewById(R.id.upcoming_ll);
        m_ongoing_ll=findViewById(R.id.ongoing_ll);
        m_updation_ll=findViewById(R.id.taskupde_PL);
        m_view=findViewById(R.id.view_PL);
        m_stick=findViewById(R.id.stick_TV);

        homeModels=new ArrayList<>();
        processLightModels=new ArrayList<>();
        processLightOngoingModels =new ArrayList<>();
        processLightUpcomingModels =new ArrayList<>();

        viewDelayedModels=new ArrayList<>();
        viewOngoingModels=new ArrayList<>();
        viewUpcomingModels =new ArrayList<>();

       m_search.addTextChangedListener(new TextWatcher() {
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
        });

        m_cancel_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_search.setText("");
                m_cancel_search.setVisibility(View.GONE);
            }
        });


        m_updation_ll.setVisibility(View.GONE);
        m_filter_ll.setVisibility(View.VISIBLE);
        m_rec_pl.setBackgroundColor(Color.parseColor("#FDFEFE"));
        getDashBoard();

        getProcess();
        m_delayed.setBackgroundResource(R.drawable.whitered_background);
        m_upcoming.setBackgroundResource(R.drawable.whitegray_background);
        m_ongoing.setBackgroundResource(R.drawable.whitegray_background);
        m_view.setBackgroundColor(Color.parseColor("#CB4335"));

        m_delayed_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProcess();

                m_delayed.setBackgroundResource(R.drawable.whitered_background);
                m_upcoming.setBackgroundResource(R.drawable.whitegray_background);
                m_ongoing.setBackgroundResource(R.drawable.whitegray_background);

                m_stick.setBackgroundResource(R.drawable.red_background);

                m_search.addTextChangedListener(new TextWatcher() {
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
                });

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

                m_search.addTextChangedListener(new TextWatcher() {
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
                });

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

                m_search.addTextChangedListener(new TextWatcher() {
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
                });

            }
        });

        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        m_rec_pl.setLayoutManager(layoutManager);

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
                        m_updation_ll.setVisibility(View.GONE);
                        m_filter_ll.setVisibility(View.VISIBLE);
                        m_rec_pl.setBackgroundColor(Color.parseColor("#FDFEFE"));
                        processLightAdapter = new ProcessLightAdapter(ProcessLight.this, processLightModels);
                        m_rec_pl.setAdapter(processLightAdapter);
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
                Toast.makeText(ProcessLight.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                        m_updation_ll.setVisibility(View.GONE);
                        m_filter_ll.setVisibility(View.VISIBLE);
                        m_rec_pl.setBackgroundColor(Color.parseColor("#FDFEFE"));
                        processLightOngoingAdapter = new ProcessLightOngoingAdapter(ProcessLight.this, processLightOngoingModels);
                        m_rec_pl.setAdapter(processLightOngoingAdapter);
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
                Toast.makeText(ProcessLight.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                        m_updation_ll.setVisibility(View.GONE);
                        m_filter_ll.setVisibility(View.VISIBLE);
                        m_rec_pl.setBackgroundColor(Color.parseColor("#FDFEFE"));
                        processLightUpcmoingAdapter = new ProcessLightUpcmoingAdapter(ProcessLight.this, processLightUpcomingModels);
                        m_rec_pl.setAdapter(processLightUpcmoingAdapter);
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
                Toast.makeText(ProcessLight.this, "No Internet...", Toast.LENGTH_SHORT).show();

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

                            if(onGoing.length()!=1)
                            {
                                m_ongoing.setText(onGoing);
                            }else {
                                m_ongoing.setText("0"+onGoing);
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
                Toast.makeText(ProcessLight.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                                Toast.makeText(ProcessLight.this,"No Data Available Now",Toast.LENGTH_SHORT).show();
                            }

                        }
                        m_updation_ll.setVisibility(View.VISIBLE);
                        m_filter_ll.setVisibility(View.GONE);

                        m_rec_pl.setBackgroundColor(Color.parseColor("#EDEDED"));

                        viewDelayedAdapter = new ViewDelayedAdapter(ProcessLight.this, viewDelayedModels);
                        m_rec_pl.setAdapter(viewDelayedAdapter);

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
                Toast.makeText(ProcessLight.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                                Toast.makeText(ProcessLight.this,"No Data Available Now",Toast.LENGTH_SHORT).show();
                            }

                        }
                        m_updation_ll.setVisibility(View.VISIBLE);
                        m_filter_ll.setVisibility(View.GONE);

                        m_rec_pl.setBackgroundColor(Color.parseColor("#EDEDED"));

                        viewOngoingAdapter = new ViewOngoingAdapter(ProcessLight.this, viewOngoingModels);
                        m_rec_pl.setAdapter(viewOngoingAdapter);
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
                Toast.makeText(ProcessLight.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                                Toast.makeText(ProcessLight.this, "No Data Available Now", Toast.LENGTH_SHORT).show();
                            }
                         }

                                m_updation_ll.setVisibility(View.VISIBLE);
                                m_filter_ll.setVisibility(View.GONE);

                                m_rec_pl.setBackgroundColor(Color.parseColor("#EDEDED"));


                                viewUpcomingAdapter = new ViewUpcomingAdapter(ProcessLight.this, viewUpcomingModels);
                                m_rec_pl.setAdapter(viewUpcomingAdapter);

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
                Toast.makeText(ProcessLight.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void delayedCompleted() {

        loaderdialog.show();

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                            Toast.makeText(ProcessLight.this, message,Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        loaderdialog.dismiss();
                        Log.e("my log"," Invalid Credentials :");
                        Toast.makeText(ProcessLight.this, "Invalid Credentials ",Toast.LENGTH_SHORT).show();
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

            RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                            Toast.makeText(ProcessLight.this, message,Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        loaderdialog.dismiss();
                        Log.e("my log"," Invalid Credentials :");
                        Toast.makeText(ProcessLight.this, "Invalid Credentials ",Toast.LENGTH_SHORT).show();
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

            RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                            Toast.makeText(ProcessLight.this, message,Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        loaderdialog.dismiss();
                        Log.e("my log"," Invalid Credentials :");
                        Toast.makeText(ProcessLight.this, "Invalid Credentials ",Toast.LENGTH_SHORT).show();
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


    public void filterDelayed(String text) {
        ArrayList<ProcessLightModel> filterlist=new ArrayList<>();
        for(ProcessLightModel item : processLightModels)
        {
            if(item.getResponsiblePerson().toLowerCase().contains(text.toLowerCase()))
            {
                filterlist.add(item);
            }
        }
        processLightAdapter.filterList(filterlist);
    }

    public void filterUpcoming(String text) {
        ArrayList<ProcessLightUpcomingModel> filterlist=new ArrayList<>();
        for(ProcessLightUpcomingModel item : processLightUpcomingModels)
        {
            if(item.getResponsiblePerson().toLowerCase().contains(text.toLowerCase()))
            {
                filterlist.add(item);
            }
        }
        processLightUpcmoingAdapter.filterList(filterlist);
    }

    public void filterOngoing(String text) {
        ArrayList<ProcessLightOngoingModel> filterlist=new ArrayList<>();
        for(ProcessLightOngoingModel item : processLightOngoingModels)
        {
            if(item.getResponsiblePerson().toLowerCase().contains(text.toLowerCase()))
            {
                filterlist.add(item);
            }
        }
        processLightOngoingAdapter.filterList(filterlist);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        loaderdialog.show();
        Intent i=new Intent(this,HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}