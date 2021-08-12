package com.erp.apparel.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.erp.apparel.R;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.IndicatorStayLayout;
import com.warkiz.widget.IndicatorType;
import com.warkiz.widget.TickMarkType;


public class BarGraph extends AppCompatActivity {

    SeekBar seekBar;
    TextView m_percentage;
    public String prog="70";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);

        seekBar=(SeekBar)findViewById(R.id.seekBar);
        m_percentage=findViewById(R.id.percent_TV);

        seekBar.setProgress(Integer.parseInt(prog));
        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.MULTIPLY);

        m_percentage.setText(prog);


        m_percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });





        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
             //   Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
          //      prog=progress;
              //  m_percentage.setText("" + 70 + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            //    Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
             //   Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });





    }


}