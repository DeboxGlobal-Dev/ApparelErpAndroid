package com.erp.apparel.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.erp.apparel.Data.DialogManager;
import com.erp.apparel.Data.Prefs;
import com.erp.apparel.Models.ScoreCardModel;
import com.erp.apparel.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ProdutionReport extends AppCompatActivity {

    BarChart barChart_quality, barChart_wip,barChart_sam;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<BarEntry> barEntries_wip;
    ArrayList<BarEntry> barEntries_sam;
    Dialog loaderdialog;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodution_report);

        loaderdialog= DialogManager.getLoaderDialog(this);

        barChart_quality=findViewById(R.id.quality_chart_PR);
        barChart_wip=findViewById(R.id.wip_chart_PR);
        barChart_sam=findViewById(R.id.sam_chart_PR);


        barEntryArrayList=new ArrayList<>();
        getBarEntries();

        barEntries_wip=new ArrayList<>();
        getBarEntrieswip();

        barEntries_sam=new ArrayList<>();
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
    public void onBackPressed() {
        super.onBackPressed();
        loaderdialog.show();
        Intent i=new Intent(this,HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}