package com.erp.apparel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erp.apparel.Models.BusinessModel;
import com.erp.apparel.Models.OtdsModel;
import com.erp.apparel.R;

import java.util.ArrayList;

public class OtdsAdapter extends RecyclerView.Adapter<OtdsAdapter.RecViewHolder> {


    Context context;
   private ArrayList<OtdsModel> demolist;

    public OtdsAdapter(Context context, ArrayList<OtdsModel> demolist) {
        this.context = context;
        this.demolist = demolist;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_otds, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, final int position) {

        holder.m_style.setText(demolist.get(position).getStyle());
        holder.m_po.setText(demolist.get(position).getPO());
        holder.m_brandsot.setText(demolist.get(position).getBrandSOT());
        holder.m_factorysot.setText(demolist.get(position).getFactorySOT());
    }

    @Override
    public int getItemCount() {

        return demolist.size();
    }

    public void filterList(ArrayList<OtdsModel> filterlist) {
        demolist=filterlist;
        notifyDataSetChanged();
    }


    class RecViewHolder extends RecyclerView.ViewHolder {

       TextView m_style,m_po,m_brandsot,m_factorysot;

        public RecViewHolder(@NonNull final View itemView) {

            super(itemView);
            m_style=itemView.findViewById(R.id.styleno_OTDS);
            m_po=itemView.findViewById(R.id.pono_OTDS);
            m_brandsot=itemView.findViewById(R.id.brandsot_OTDS);
            m_factorysot=itemView.findViewById(R.id.facotrysot_OTDS);
        }
    }
}