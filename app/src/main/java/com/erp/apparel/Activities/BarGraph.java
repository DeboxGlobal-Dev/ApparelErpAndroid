package com.erp.apparel.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.erp.apparel.R;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.IndicatorStayLayout;
import com.warkiz.widget.IndicatorType;
import com.warkiz.widget.TickMarkType;

import java.util.HashMap;
import java.util.Map;


public class BarGraph extends AppCompatActivity {

    SeekBar seekBar;
    EditText m_no;
    Button m_btn;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);

        m_no=findViewById(R.id.edit_TV_BG);
        m_btn=findViewById(R.id.submit_TV_BG);

        m_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = m_no.getText().toString();

                cartCount();
            }
        });

    }

    public void cartCount() {


        String url = "https://munafamandi.com/api/login.php?Mobile="+number;

        final RequestQueue queue = Volley.newRequestQueue(this);

        Log.e("mylog", "your UIT Login URL is :" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //  loaderdialog.dismiss();
                Log.e("mylog", "your UIT Login Response are :" + response);
                try {
                    queue.getCache().clear();

                    Intent intent=new Intent(BarGraph.this,RegistrationActivity.class);
                    startActivity(intent);


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("MyLog", "onResponse: Error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   loaderdialog.dismiss();
                Toast.makeText(BarGraph.this, "No Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                  params.put("Mobile",number);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
        queue.getCache().clear();
    }


}