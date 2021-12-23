package com.erp.apparel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.erp.apparel.Interface.PdfEvents;
import com.erp.apparel.Models.PreOrderModel;
import com.erp.apparel.R;

import java.util.ArrayList;

public class PreOrderFragAdapter extends RecyclerView.Adapter<PreOrderFragAdapter.RecViewHolder> {


    Context context;
    private ArrayList<PreOrderModel> demolist;
    PdfEvents pdfEvents;


    public PreOrderFragAdapter(Context context, Fragment fragment, ArrayList<PreOrderModel> demolist) {
        this.context= context;
        this.demolist = demolist;
        this.pdfEvents=(PdfEvents) fragment;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_preorder, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, final int position) {

        holder.m_style.setText(demolist.get(position).getStyleId());
        holder.m_productcost.setText(demolist.get(position).getProduct());
        holder.m_profitloss.setText(demolist.get(position).getProfit());
        holder.m_effectiveprice.setText(demolist.get(position).getEffective());

        holder.m_pdfview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfEvents.onViewClick(demolist.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return demolist.size();
    }

    public void filterList(ArrayList<PreOrderModel> filterlist) {
        demolist=filterlist;
        notifyDataSetChanged();
    }


    class RecViewHolder extends RecyclerView.ViewHolder {

       TextView m_style,m_productcost,m_effectiveprice, m_profitloss,m_pdfview;

        public RecViewHolder(@NonNull final View itemView) {

            super(itemView);
            m_style=itemView.findViewById(R.id.style_PRE);
            m_effectiveprice=itemView.findViewById(R.id.effectiveprice_PRE);
            m_profitloss=itemView.findViewById(R.id.pandl_PRE);
            m_productcost=itemView.findViewById(R.id.product_PRE);
            m_pdfview=itemView.findViewById(R.id.pdfview_PL);
        }
    }
}