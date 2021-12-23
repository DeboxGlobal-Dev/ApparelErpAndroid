package com.erp.apparel.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erp.apparel.Activities.StyleInfo;
import com.erp.apparel.Models.StyleInfoModel;
import com.erp.apparel.R;

import java.util.ArrayList;
import java.util.Locale;

public class StyleInfoAdapter extends RecyclerView.Adapter<StyleInfoAdapter.RecViewHolder> {


    Context context;
   private ArrayList<StyleInfoModel> demolist;

    public StyleInfoAdapter(Context context, ArrayList<StyleInfoModel> demolist) {
        this.context = context;
        this.demolist = demolist;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_styleinfo, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, final int position) {

        holder.m_style_ID.setText(demolist.get(position).getStyleId());
        holder.m_status.setText(demolist.get(position).getStatus());

       String tna= demolist.get(position).getTnaProgress();

        int tnaProgress= Integer.parseInt(tna);


        holder.seekBar_yellow.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);
        holder.seekBar_blue.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.blue), PorterDuff.Mode.MULTIPLY);

        holder.seekBar_blue.setProgress(tnaProgress);
        holder.m_seek_TV.setText(tnaProgress+"%");

        holder.seekBar_blue.setEnabled(false);


       /* holder.seekBar_blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //   Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
                //      prog=progress;
                  holder.m_seek_TV.setText("" + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //    Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //   Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });*/



        String priority =demolist.get(position).getPriority();

        if(priority.equals("Low"))
        {
          /*  holder.m_low.setBackgroundColor(Color.parseColor("#D91E0B"));
            holder.m_medium.setBackgroundColor(Color.parseColor("#99A3A4"));
            holder.m_high.setBackgroundColor(Color.parseColor("#99A3A4"));*/
            holder.m_low.setBackgroundResource(R.drawable.red_background);
            holder.m_high.setBackgroundResource(R.drawable.graydark_background);
            holder.m_medium.setBackgroundResource(R.drawable.graydark_background);
        }
        else if(priority.equals("Medium")){
            /*holder.m_low.setBackgroundColor(Color.parseColor("#99A3A4"));
            holder.m_medium.setBackgroundColor(Color.parseColor("#07900D"));
            holder.m_high.setBackgroundColor(Color.parseColor("#99A3A4"));*/
            holder.m_low.setBackgroundResource(R.drawable.graydark_background);
            holder.m_high.setBackgroundResource(R.drawable.graydark_background);
            holder.m_medium.setBackgroundResource(R.drawable.green_background);
        }
        else {
            /*holder.m_low.setBackgroundColor(Color.parseColor("#99A3A4"));
            holder.m_medium.setBackgroundColor(Color.parseColor("#99A3A4"));
            holder.m_high.setBackgroundColor(Color.parseColor("#FAD209"));*/
            holder.m_low.setBackgroundResource(R.drawable.graydark_background);
            holder.m_high.setBackgroundResource(R.drawable.yellow_background);
            holder.m_medium.setBackgroundResource(R.drawable.graydark_background);

        }




    }

    @Override
    public int getItemCount() {

        return demolist.size();
    }

    public void filterList(ArrayList<StyleInfoModel> filterlist) {

        demolist=filterlist;
        notifyDataSetChanged();
    }


    class RecViewHolder extends RecyclerView.ViewHolder {

       TextView m_style_ID,m_status,m_seek_TV,m_low,m_medium,m_high;
        SeekBar seekBar_blue,seekBar_yellow;



        public RecViewHolder(@NonNull final View itemView) {

            super(itemView);

            seekBar_blue=itemView.findViewById(R.id.seekBar_blue);
            seekBar_yellow=itemView.findViewById(R.id.seekBar_yellow);

            m_style_ID=itemView.findViewById(R.id.styleid_STYLEIFNO);
            m_seek_TV=itemView.findViewById(R.id.seek_TV_STYLEINFO);
            m_status=itemView.findViewById(R.id.status_STYLEINFO);

            m_low=itemView.findViewById(R.id.low_STYLEIFNO);
            m_medium=itemView.findViewById(R.id.medium_STYLEIFNO);
            m_high=itemView.findViewById(R.id.high_STYLEIFNO);


           /*  String priority =demolist.get(getPosition()).getPriority();


        if(priority.equals("Low"))
        {
            m_low.setBackgroundColor(Color.parseColor("#F0F00E"));
            notifyDataSetChanged();
        }
        else if(priority.equals("Medium")){
            m_medium.setBackgroundColor(Color.parseColor("#07900D"));
            notifyDataSetChanged();
        }
        else {
            m_high.setBackgroundColor(Color.parseColor("#A93226"));
            notifyDataSetChanged();
        }*/

        }
    }
}