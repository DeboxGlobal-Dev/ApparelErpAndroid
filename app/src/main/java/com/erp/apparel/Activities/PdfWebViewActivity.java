package com.erp.apparel.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.erp.apparel.R;

public class PdfWebViewActivity extends AppCompatActivity {

    WebView m_webview;
    int pos;
    Button m_submit;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_web_view);

        m_webview = findViewById(R.id.webview_WB);

      /*  ArrayList<HotelModel> numbersList = (ArrayList<HotelModel>) getIntent().getSerializableExtra("voucher");
        pos = getIntent().getIntExtra("position", 0);*/

        name= getIntent().getStringExtra("url");
        //    name = numbersList.get(0).getVoucherUrl();
        Log.e("mylog","name of webview of from Pre Order Adapter "+name);

        m_submit=findViewById(R.id.submit_BTN);

        if(!name.equals(""))
        {
            WebSettings ws=m_webview.getSettings();
            ws.setDomStorageEnabled(true);
            ws.setJavaScriptEnabled(true);

            ws.setUseWideViewPort(true);
            ws.setLoadWithOverviewMode(true);
            m_webview.setWebViewClient(new MyWebViewClient());
            m_webview.requestFocus();
              m_webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+name);
         //   m_webview.loadUrl(name);
        }
        else {
            Toast.makeText(PdfWebViewActivity.this,"No Document Available Now ", Toast.LENGTH_SHORT).show();
        }


        m_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.equals(""))
                {
                    Toast.makeText(PdfWebViewActivity.this,"No Document Available Now ", Toast.LENGTH_SHORT).show();
                }
                else {
                    DownloadPDFfromURL();
                    Toast.makeText(PdfWebViewActivity.this, "Downloaded ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(PdfWebViewActivity.this, "Oh No! " + description, Toast.LENGTH_SHORT).show();
        }
    }

    public void DownloadPDFfromURL(){
      /*  ArrayList<HotelModel> numbersList = (ArrayList<HotelModel>) getIntent().getSerializableExtra("voucher");
        pos = getIntent().getIntExtra("position", 0);*/
        //  String name=numbersList.get(pos).getHotel().get(pos).getVoucherUrl();

        String name= getIntent().getStringExtra("url");
        //  String name=numbersList.get(0).getVoucherUrl();
        //  String name="http://deboxglobal.co.in/travcrm-latestinbound/upload/1590758373hotelsvoucher.pdf";
        String url="preorder.pdf";
        new DownloaderPdf()
                .execute(name,url);
    }

}