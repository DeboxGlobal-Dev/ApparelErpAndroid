package com.erp.apparel.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.R;

public class AboutUs extends AppCompatActivity {

    WebView m_webview;
    Dialog loaderdialog;
    ImageView m_navigation_icon;
    HomeActivity homeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        homeActivity=new HomeActivity();
        loaderdialog= DialogManager.getLoaderDialog(this);
        m_webview = findViewById(R.id.webview_WB);
        m_navigation_icon=findViewById(R.id.navigation_icon);


      /*  m_navigation_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.m_draw.openDrawer(GravityCompat.START);
                Toast.makeText(AboutUs.this,"is click",Toast.LENGTH_SHORT).show();
            }
        });
*/
        WebSettings ws = m_webview.getSettings();
        ws.setDomStorageEnabled(true);
        ws.setJavaScriptEnabled(true);

        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        m_webview.requestFocus();
        m_webview.loadUrl("file:///android_asset/about.html");
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