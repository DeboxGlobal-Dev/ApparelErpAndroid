package com.erp.apparel.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.erp.apparel.Activities.PreOrder;
import com.erp.apparel.Activities.ProcessLight;
import com.erp.apparel.Activities.SpoMgmt;
import com.erp.apparel.Adapter.PreOrderAdapter;
import com.erp.apparel.Adapter.PreOrderFragAdapter;
import com.erp.apparel.Adapter.ProcessLightAdapter;
import com.erp.apparel.Adapter.ProcessLightFragAdapter;
import com.erp.apparel.Adapter.ProcessLightOngoingAdapter;
import com.erp.apparel.Adapter.ProcessLightOngoingFragAdapter;
import com.erp.apparel.Adapter.ProcessLightUpcmoingAdapter;
import com.erp.apparel.Adapter.ProcessLightUpcmoingFragAdapter;
import com.erp.apparel.Adapter.SpoAdapter;
import com.erp.apparel.Adapter.ViewDelayedAdapter;
import com.erp.apparel.Adapter.ViewDelayedFragAdapter;
import com.erp.apparel.Adapter.ViewOngoingAdapter;
import com.erp.apparel.Adapter.ViewOngoingFragAdapter;
import com.erp.apparel.Adapter.ViewUpcomingAdapter;
import com.erp.apparel.Adapter.ViewUpcomingFragAdapter;
import com.erp.apparel.AppConstant.AppNetworkConstants;
import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.Interface.CompleteEvents;
import com.erp.apparel.Interface.PdfEvents;
import com.erp.apparel.Interface.ViewEvents;
import com.erp.apparel.Models.HomeModel;
import com.erp.apparel.Models.HomeResponseBean;
import com.erp.apparel.Models.PreOrderModel;
import com.erp.apparel.Models.PreOrderResponseBean;
import com.erp.apparel.Models.ProcessLightModel;
import com.erp.apparel.Models.ProcessLightOngoingModel;
import com.erp.apparel.Models.ProcessLightOngoingResponseBean;
import com.erp.apparel.Models.ProcessLightResponseBean;
import com.erp.apparel.Models.ProcessLightUpcomingModel;
import com.erp.apparel.Models.ProcessLightUpcomingResponseBean;
import com.erp.apparel.Models.SpoModel;
import com.erp.apparel.Models.SpoResponseBean;
import com.erp.apparel.Models.ViewDelayedModel;
import com.erp.apparel.Models.ViewDelayedResponseBean;
import com.erp.apparel.Models.ViewOngoingModel;
import com.erp.apparel.Models.ViewOngoingResponseBean;
import com.erp.apparel.Models.ViewUpcomingModel;
import com.erp.apparel.Models.ViewUpcomingResponseBean;
import com.erp.apparel.R;

import org.json.JSONObject;

import java.util.ArrayList;


public class ApprovalsFrag extends Fragment implements PdfEvents, ViewEvents, CompleteEvents {

    ArrayList<PreOrderModel> preOrderModels;
    PreOrderFragAdapter preOrderFragAdapter;

    ArrayList<SpoModel> spoModels;
    SpoAdapter spoAdapter;

    RecyclerView m_rec;
    LinearLayoutManager layoutManager;
    Dialog loaderdialog;

    private static final int PERMISSION_STORAGE_CODE=1000;
    String pdfnewurl="";

    LinearLayout m_processlightheader_ll;

    TextView m_delayed,m_ongoing,m_upcoming,m_stick;
    ArrayList<HomeModel> homeModels;
    LinearLayout m_delayed_ll,m_upcoming_ll,m_ongoing_ll,m_preordercosttitle, m_spomgmttitle;
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

    ArrayList<ProcessLightModel> processLightModels;
    ArrayList<ProcessLightOngoingModel> processLightOngoingModels;
    ArrayList<ProcessLightUpcomingModel> processLightUpcomingModels;

    ArrayList<ViewDelayedModel> viewDelayedModels;
    ArrayList<ViewUpcomingModel> viewUpcomingModels;
    ArrayList<ViewOngoingModel> viewOngoingModels;


    ProcessLightFragAdapter processLightFragAdapter;
    ProcessLightOngoingFragAdapter processLightOngoingFragAdapter;
    ProcessLightUpcmoingFragAdapter processLightUpcmoingFragAdapter;

    ViewDelayedFragAdapter viewDelayedFragAdapter;
    ViewUpcomingFragAdapter viewUpcomingFragAdapter;
    ViewOngoingFragAdapter viewOngoingFragAdapter;

    TextView m_preordercost, m_spomgmt, m_processlightsystem;

    public ApprovalsFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_approvals, container, false);

        loaderdialog= DialogManager.getLoaderDialog(getContext());

        m_preordercost=view.findViewById(R.id.preordercost_APPROVE);
        m_processlightsystem=view.findViewById(R.id.processlightsystem_APPROVE);
        m_spomgmt=view.findViewById(R.id.spomgmt_APPROVE);

        m_processlightheader_ll=view.findViewById(R.id.processlight_header_ll);

        m_spomgmttitle=view.findViewById(R.id.spomgmttitle_APPROVE);
        m_preordercosttitle=view.findViewById(R.id.preordertitle_APPROVE);

        m_delayed=view.findViewById(R.id.delayed_PL);
        m_upcoming=view.findViewById(R.id.upcoming_PL);
        m_ongoing=view.findViewById(R.id.onoing_PL);

        m_delayed_ll=view.findViewById(R.id.delayed_ll);
        m_upcoming_ll=view.findViewById(R.id.upcoming_ll);
        m_ongoing_ll=view.findViewById(R.id.ongoing_ll);
     //   m_updation_ll=view.findViewById(R.id.taskupde_PL);
        m_view=view.findViewById(R.id.view_PL);
        m_stick=view.findViewById(R.id.stick_TV);

        m_rec=view.findViewById(R.id.rec_APPROVE);

        preOrderModels= new ArrayList<>();

        spoModels= new ArrayList<>();

        homeModels=new ArrayList<>();

        processLightModels=new ArrayList<>();
        processLightOngoingModels=new ArrayList<>();
        processLightUpcomingModels=new ArrayList<>();

        viewDelayedModels=new ArrayList<>();
        viewOngoingModels=new ArrayList<>();
        viewUpcomingModels =new ArrayList<>();

        m_preordercost.setTextColor(Color.WHITE);
        m_preordercost.setBackgroundResource(R.drawable.blue_sqr_background);

        m_preordercosttitle.setVisibility(View.VISIBLE);
        m_spomgmttitle.setVisibility(View.GONE);

        m_processlightheader_ll.setVisibility(View.GONE);
        m_stick.setVisibility(View.GONE);

        getPreOrders();


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


        m_preordercost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_preordercost.setTextColor(Color.WHITE);
                m_preordercost.setBackgroundResource(R.drawable.blue_sqr_background);

                m_processlightsystem.setTextColor(Color.BLACK);
                m_processlightsystem.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_spomgmt.setTextColor(Color.BLACK);
                m_spomgmt.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_preordercosttitle.setVisibility(View.VISIBLE);
                m_spomgmttitle.setVisibility(View.GONE);

                m_processlightheader_ll.setVisibility(View.GONE);
                m_stick.setVisibility(View.GONE);

                getPreOrders();
            }
        });

        m_spomgmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_preordercost.setTextColor(Color.BLACK);
                m_preordercost.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightsystem.setTextColor(Color.BLACK);
                m_processlightsystem.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_spomgmt.setTextColor(Color.WHITE);
                m_spomgmt.setBackgroundResource(R.drawable.blue_sqr_background);

                m_processlightheader_ll.setVisibility(View.GONE);
                m_stick.setVisibility(View.GONE);

                m_preordercosttitle.setVisibility(View.GONE);
                m_spomgmttitle.setVisibility(View.VISIBLE);

                getSpo();
            }
        });

        m_processlightsystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_preordercost.setTextColor(Color.BLACK);
                m_preordercost.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightsystem.setTextColor(Color.WHITE);
                m_processlightsystem.setBackgroundResource(R.drawable.blue_sqr_background);

                m_spomgmt.setTextColor(Color.BLACK);
                m_spomgmt.setBackgroundResource(R.drawable.yellow_sqr_background);

                m_processlightheader_ll.setVisibility(View.VISIBLE);
                m_stick.setVisibility(View.VISIBLE);

                m_preordercosttitle.setVisibility(View.GONE);
                m_spomgmttitle.setVisibility(View.GONE);

            //    m_updation_ll.setVisibility(View.GONE);
             //   m_filter_ll.setVisibility(View.VISIBLE);
                m_rec.setBackgroundColor(Color.parseColor("#FDFEFE"));
                getDashBoard();
                getProcess();


                m_delayed.setBackgroundResource(R.drawable.whitered_background);
                m_upcoming.setBackgroundResource(R.drawable.whitegray_background);
                m_ongoing.setBackgroundResource(R.drawable.whitegray_background);

                m_stick.setBackgroundResource(R.drawable.red_background);
            }

        });

        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        m_rec.setLayoutManager(layoutManager);

        return view;
    }

    public void getPreOrders(){

        loaderdialog.show();

        String url = AppNetworkConstants.PREORDER;

        Log.e("mylog", "MY pre order cost url are: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderdialog.dismiss();
                Log.e("mylog", "your pre order cost response is :" + response);

                try {
                    PreOrderResponseBean responseBean = PreOrderResponseBean.fromJson(response);
                    if (responseBean != null) {

                        if (responseBean.Status.equals("0")) {
                            preOrderModels.clear();
                            preOrderModels.addAll(responseBean.TotalRecord);

                        }

                        preOrderFragAdapter = new PreOrderFragAdapter(getContext(),ApprovalsFrag.this,preOrderModels);
                        m_rec.setAdapter(preOrderFragAdapter);
                      //  preOrderAdapter.notifyDataSetChanged();
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

    @Override
    public void onViewClick(PreOrderModel demo, int positon) {

        pdfnewurl ="https://apparelerp.in/apparelerp/tcpdf/examples/generatecostsheet.php?pageurl=https://apparelerp.in/apparelerp/printcostsheet.php?id="+demo.getLinkid();

       /*if(!pdfurl.equals("")) {
           Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfurl));
           startActivity(intent);
         //  Toast.makeText(PreOrder.this, "Downloaded" + pdfurl, Toast.LENGTH_SHORT).show();
       }
       else {
           Toast.makeText(PreOrder.this, "No Documents Available Now" + pdfurl, Toast.LENGTH_SHORT).show();
       }*/

       /* if (!pdfurl.equals(""))
        {
            Intent intent = new Intent(PreOrder.this, PdfWebViewActivity.class);
            intent.putExtra("url",pdfurl);
            startActivity(intent);
        }*/

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if(getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
            {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,PERMISSION_STORAGE_CODE);
            }
            else {
                startDownloading();
            }
        }
        else {
            startDownloading();
        }
    }

    private void startDownloading() {

        if(!pdfnewurl.equals("")) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfnewurl));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setTitle("Download");
            request.setDescription("Downloading file...");
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "" + System.currentTimeMillis());

            DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);

            Toast.makeText(getContext(), "Downloading...", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "No Documents Available Now", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case PERMISSION_STORAGE_CODE:
            {
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    startDownloading();
                }
                else
                {
                    Toast.makeText(getContext(),"Permission denied... ",Toast.LENGTH_SHORT).show();
                }
            }
        }
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
                       // spoAdapter.notifyDataSetChanged();
                        spoAdapter = new SpoAdapter(getContext(), spoModels);
                        m_rec.setAdapter(spoAdapter);
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
                        processLightFragAdapter = new ProcessLightFragAdapter(getContext(),ApprovalsFrag.this, processLightModels);
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
                        processLightOngoingFragAdapter = new ProcessLightOngoingFragAdapter(getContext(),ApprovalsFrag.this, processLightOngoingModels);
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
                        processLightUpcmoingFragAdapter = new ProcessLightUpcmoingFragAdapter(getContext(),ApprovalsFrag.this, processLightUpcomingModels);
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

                        viewDelayedFragAdapter = new ViewDelayedFragAdapter(getContext(), ApprovalsFrag.this ,viewDelayedModels);
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

                        viewOngoingFragAdapter = new ViewOngoingFragAdapter(getContext(),ApprovalsFrag.this, viewOngoingModels);
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


                        viewUpcomingFragAdapter = new ViewUpcomingFragAdapter(getContext(), ApprovalsFrag.this, viewUpcomingModels);
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