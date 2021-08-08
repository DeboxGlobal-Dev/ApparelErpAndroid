package com.erp.apparel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.erp.apparel.AppConstant.AppNetworkConstants;
import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.Data.Prefs;
import com.erp.apparel.Models.HomeModel;
import com.erp.apparel.Models.HomeResponseBean;
import com.erp.apparel.Models.LoginResponseBean;
import com.erp.apparel.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    private PieChartView pieChartView,progesschartView;
    String[] time = {"01 May,2021", "02 May,2021", "03 May,2021", "04 May,2021", "05May,2021"};
    LinearLayout m_home_ll, m_logout_ll;
    DrawerLayout m_draw;
    ImageView m_navigation_icon,m_debox;
    TextView m_firstname;
    TextView m_totalstyle,m_projection,m_onorder,m_delayed,m_ongoing,m_upcoming;
    ArrayList<HomeModel> homeModels;
    Dialog loaderdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loaderdialog= DialogManager.getLoaderDialog(this);

        Prefs.INSTANCE.setLoginStatus(true);

        navigationView = findViewById(R.id.nav_view);
        m_draw = findViewById(R.id.drawlayout);
        m_navigation_icon=findViewById(R.id.navigation_icon);

        m_totalstyle=findViewById(R.id.totalsale_HOME);
        m_projection=findViewById(R.id.projection_HOME);
        m_onorder=findViewById(R.id.onorder_HOME);
        m_delayed=findViewById(R.id.delayed_HOME);
        m_ongoing=findViewById(R.id.ongoing_HOME);
        m_upcoming=findViewById(R.id.upcoming_HOME);


        pieChartView =findViewById(R.id.chart);
        progesschartView=findViewById(R.id.chartprocess);

        homeModels=new ArrayList<>();

        getDashBoard();


        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,R.layout.color_spinner_layout,time);
        aa.setDropDownViewResource(R.layout.color_spinner_layout);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        chart();
        progresschart();

        m_navigation_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_draw.openDrawer(GravityCompat.START);
            }
        });

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        View header = navigationView.getHeaderView(0);
        m_firstname=header.findViewById(R.id.firstname_HEADER);
        m_home_ll = header.findViewById(R.id.home_ll_HEADER);
        m_logout_ll = header.findViewById(R.id.logout_ll_HEADER);
        m_debox= header.findViewById(R.id.deoxicon);


        m_firstname.setText(Prefs.INSTANCE.getFirstName());

        m_debox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DeboxPage.class);
                startActivity(intent);
            }
        });

        m_home_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        m_logout_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void chart()
    {
        ArrayList<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(10,Color.parseColor("#F0ED08")).setLabel(""));
        pieData.add(new SliceValue(10, Color.parseColor("#66BB6A")).setLabel(""));
        pieData.add(new SliceValue(10, Color.parseColor("#117DC6")).setLabel(""));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setHasCenterCircle(true).setCenterText1("").setCenterText1FontSize(10).setCenterText1Color(Color.parseColor("#ff4500"));
        pieChartView.setPieChartData(pieChartData);
    }

    public void progresschart()
    {
        ArrayList<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(10, Color.parseColor("#66BB6A")).setLabel(""));
        pieData.add(new SliceValue(10,Color.parseColor("#EF100C")).setLabel(""));
        pieData.add(new SliceValue(10, Color.parseColor("#EF990C")).setLabel(""));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setHasCenterCircle(true).setCenterText1("Process").setCenterText1FontSize(10).setCenterText1Color(Color.parseColor("#99A3A4"));
        pieChartData.setHasCenterCircle(true).setCenterText2("Light System").setCenterText2FontSize(10).setCenterText2Color(Color.parseColor("#99A3A4"));
        progesschartView.setPieChartData(pieChartData);
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        return false;
    }

    private void opendialog() {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to Logout?");
        //  alertDialogBuilder.setTitle("Confirmation Required");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Prefs.INSTANCE.clear();
                        Intent i=new Intent(HomeActivity.this,LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
                //   progressBar.setVisibility(View.GONE);

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
                Toast.makeText(HomeActivity.this, "No Internet...", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}