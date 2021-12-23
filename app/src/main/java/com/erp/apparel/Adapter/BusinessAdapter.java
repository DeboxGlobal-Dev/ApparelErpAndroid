package com.erp.apparel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erp.apparel.Models.BusinessModel;
import com.erp.apparel.Models.ProcessLightModel;
import com.erp.apparel.R;

import java.util.ArrayList;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.RecViewHolder> {


    Context context;
   private ArrayList<BusinessModel> demolist;

    public BusinessAdapter(Context context, ArrayList<BusinessModel> demolist) {
        this.context = context;
        this.demolist = demolist;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_business, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, final int position) {

        holder.m_buyer.setText(demolist.get(position).getBuyer());
        holder.m_brand.setText(demolist.get(position).getBrand());
        holder.m_qty.setText(demolist.get(position).getQty());
    }

    @Override
    public int getItemCount() {

        return demolist.size();
    }

    public void filterList(ArrayList<BusinessModel> filterlist) {
        demolist=filterlist;
        notifyDataSetChanged();
    }


    class RecViewHolder extends RecyclerView.ViewHolder {

       TextView m_buyer,m_brand,m_qty;

        public RecViewHolder(@NonNull final View itemView) {

            super(itemView);
            m_buyer=itemView.findViewById(R.id.buyer_BS);
            m_brand=itemView.findViewById(R.id.brand_BS);
            m_qty=itemView.findViewById(R.id.orderquantity_BS);

        }
    }
}