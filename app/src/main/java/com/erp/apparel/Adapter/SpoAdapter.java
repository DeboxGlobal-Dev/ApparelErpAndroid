package com.erp.apparel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erp.apparel.Models.PreOrderModel;
import com.erp.apparel.Models.SpoModel;
import com.erp.apparel.R;

import java.util.ArrayList;

public class SpoAdapter extends RecyclerView.Adapter<SpoAdapter.RecViewHolder> {


    Context context;
   private ArrayList<SpoModel> demolist;

   MaterialAdapter materialAdapter;

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public SpoAdapter(Context context, ArrayList<SpoModel> demolist) {
        this.context = context;
        this.demolist = demolist;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_spomgmt, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, final int position) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);

        materialAdapter = new MaterialAdapter(context, demolist.get(position).getMaterialList());
        holder.m_rec_MATERIAL.setLayoutManager(layoutManager);
        holder.m_rec_MATERIAL.setAdapter(materialAdapter);
        holder.m_rec_MATERIAL.setRecycledViewPool(viewPool);

        holder.m_supplier.setText(demolist.get(position).getSupplier());

    }

    @Override
    public int getItemCount() {

        return demolist.size();
    }

    public void filterList(ArrayList<SpoModel> filterlist) {
        demolist=filterlist;
        notifyDataSetChanged();
    }


    class RecViewHolder extends RecyclerView.ViewHolder {

       TextView m_supplier,m_date;
       RecyclerView m_rec_MATERIAL;

        public RecViewHolder(@NonNull final View itemView) {

            super(itemView);

            m_supplier=itemView.findViewById(R.id.suppliername_SPO);
            m_date=itemView.findViewById(R.id.date_SPO);
            m_rec_MATERIAL=itemView.findViewById(R.id.rec_MATERIAL);

        }
    }
}