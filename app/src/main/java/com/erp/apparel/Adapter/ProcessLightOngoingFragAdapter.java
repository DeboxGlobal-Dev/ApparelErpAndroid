package com.erp.apparel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.erp.apparel.Interface.ViewEvents;
import com.erp.apparel.Models.ProcessLightOngoingModel;
import com.erp.apparel.R;

import java.util.ArrayList;

public class ProcessLightOngoingFragAdapter extends RecyclerView.Adapter<ProcessLightOngoingFragAdapter.RecViewHolder> {


    Context context;
    Fragment fragment;
   private ArrayList<ProcessLightOngoingModel> demolist;
    ViewEvents viewEvents;

    public ProcessLightOngoingFragAdapter(Context context, Fragment fragment, ArrayList<ProcessLightOngoingModel> demolist) {
        this.context = context;
        this.demolist = demolist;
        this.viewEvents=(ViewEvents) fragment;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_processlightongoing, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, final int position) {

        holder.m_resperson.setText(demolist.get(position).getResponsiblePerson());
        holder.m_count.setText(demolist.get(position).getCount());

        holder.m_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewEvents.onOngoingClick(demolist.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return demolist.size();
    }

    public void filterList(ArrayList<ProcessLightOngoingModel> filterlist) {

        demolist=filterlist;
        notifyDataSetChanged();
    }


    class RecViewHolder extends RecyclerView.ViewHolder {

       TextView m_resperson,m_count,m_view;

        public RecViewHolder(@NonNull final View itemView) {

            super(itemView);
            m_resperson=itemView.findViewById(R.id.res_person_PL);
            m_count=itemView.findViewById(R.id.count_PL);
            m_view=itemView.findViewById(R.id.view_PL);

        }
    }
}