package com.erp.apparel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.erp.apparel.Adapter.PreOrderAdapter;
import com.erp.apparel.Adapter.ProcessLightAdapter;
import com.erp.apparel.Adapter.ViewOngoingAdapter;
import com.erp.apparel.AppConstant.AppNetworkConstants;
import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.Interface.PdfEvents;
import com.erp.apparel.Models.BusinessModel;
import com.erp.apparel.Models.PreOrderModel;
import com.erp.apparel.Models.PreOrderResponseBean;
import com.erp.apparel.Models.ViewOngoingResponseBean;
import com.erp.apparel.R;

import java.util.ArrayList;

public class PreOrder extends AppCompatActivity implements PdfEvents {

    ArrayList<PreOrderModel> preOrderModels;
    PreOrderAdapter preOrderAdapter;
    RecyclerView m_rec;
    LinearLayoutManager layoutManager;
    Dialog loaderdialog;
    EditText m_search;
    ImageView m_cancel_search;

    private static final int PERMISSION_STORAGE_CODE=1000;
    String pdfnewurl="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder);
        loaderdialog= DialogManager.getLoaderDialog(this);

        m_search=findViewById(R.id.search_TV);
        m_cancel_search=findViewById(R.id.cancel_search);

        m_rec=findViewById(R.id.preorder_REC);

        preOrderModels= new ArrayList<>();

        getPreOrders();

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

        preOrderAdapter = new PreOrderAdapter(PreOrder.this, preOrderModels);
        m_rec.setAdapter(preOrderAdapter);


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
                       preOrderAdapter.notifyDataSetChanged();
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
                Toast.makeText(PreOrder.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void filter(String text) {
        ArrayList<PreOrderModel> filterlist=new ArrayList<>();
        for(PreOrderModel item : preOrderModels)
        {
            if(item.getStyleId().toLowerCase().contains(text.toLowerCase())) {
                filterlist.add(item);
            }
        }
        preOrderAdapter.filterList(filterlist);
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
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
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

            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);

            Toast.makeText(PreOrder.this, "Downloading...", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(PreOrder.this, "No Documents Available Now", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
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
                    Toast.makeText(PreOrder.this,"Permission denied... ",Toast.LENGTH_SHORT).show();
                    }
            }
        }
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