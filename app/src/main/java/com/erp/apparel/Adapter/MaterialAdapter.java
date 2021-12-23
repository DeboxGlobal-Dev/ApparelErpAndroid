package com.erp.apparel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erp.apparel.Models.MaterialModel;
import com.erp.apparel.Models.SpoModel;
import com.erp.apparel.R;

import java.util.ArrayList;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.RecViewHolder> {


    Context context;
   private ArrayList<MaterialModel> demolist;

    public MaterialAdapter(Context context, ArrayList<MaterialModel> demolist) {
        this.context = context;
        this.demolist = demolist;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_material, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, final int position) {

        holder.m_style.setText(demolist.get(position).getStyleId());
        holder.m_material.setText(demolist.get(position).getMaterialid());
        holder.m_value.setText(demolist.get(position).getValue());
    }

    @Override
    public int getItemCount() {

        return demolist.size();
    }

    public void filterlist(ArrayList<MaterialModel> internalfilter) {
        demolist=internalfilter;
        notifyDataSetChanged();
    }


    class RecViewHolder extends RecyclerView.ViewHolder {

       TextView m_style,m_material, m_value,m_order;

        public RecViewHolder(@NonNull final View itemView) {

            super(itemView);
            m_style=itemView.findViewById(R.id.style_SPO);
            m_material=itemView.findViewById(R.id.materialid_SPO);
            m_value=itemView.findViewById(R.id.value_SPO);
            m_order=itemView.findViewById(R.id.orderquantity_SPO);
        }
    }
}