package com.erp.apparel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erp.apparel.Interface.CompleteEvents;
import com.erp.apparel.Interface.ViewEvents;
import com.erp.apparel.Models.ProcessLightModel;
import com.erp.apparel.Models.ViewDelayedModel;
import com.erp.apparel.R;

import java.util.ArrayList;

public class ViewDelayedAdapter extends RecyclerView.Adapter<ViewDelayedAdapter.RecViewHolder> {


    Context context;
   private ArrayList<ViewDelayedModel> demolist;
   CompleteEvents completeEvents;


    public ViewDelayedAdapter(Context context, ArrayList<ViewDelayedModel> demolist) {
        this.context = context;
        this.demolist = demolist;
        this.completeEvents=(CompleteEvents) context;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_view_delayed, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, final int position) {

        ViewDelayedModel demo= demolist.get(position);

        if (demo!=null) {

            holder.m_style.setText(demo.getStyleid());
            holder.m_keyprocess.setText(demo.getKeys());
            holder.m_planneddate.setText(demo.getPlanned());

            holder.m_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    completeEvents.onDelayedCompleteClick(demo, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return demolist.size();
    }



    class RecViewHolder extends RecyclerView.ViewHolder {

       TextView m_style,m_keyprocess,m_planneddate,m_complete;

        public RecViewHolder(@NonNull final View itemView) {

            super(itemView);
            m_style=itemView.findViewById(R.id.style_VIEW);
            m_keyprocess=itemView.findViewById(R.id.keyprocess_VIEW);
            m_planneddate=itemView.findViewById(R.id.planneddate_VIEW);
            m_complete=itemView.findViewById(R.id.view_PL);

        }
    }
}