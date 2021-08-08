package com.erp.apparel.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.erp.apparel.R;

public class DeboxPage extends AppCompatActivity {

    WebView m_webview;

    String url="https://www.deboxglobal.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debox_page);

        m_webview = findViewById(R.id.webview_Debox);

        WebSettings ws = m_webview.getSettings();
        ws.setDomStorageEnabled(true);
        ws.setJavaScriptEnabled(true);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        m_webview.requestFocus();
        m_webview.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(DeboxPage.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
        }
    }
}