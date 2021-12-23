package com.erp.apparel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.erp.apparel.Interface.CompleteEvents;
import com.erp.apparel.Models.ViewUpcomingModel;
import com.erp.apparel.R;

import java.util.ArrayList;

public class ViewUpcomingFragAdapter extends RecyclerView.Adapter<ViewUpcomingFragAdapter.RecViewHolder> {


    Context context;
    Fragment fragment;
  // private ArrayList<ViewUpcomingModel> demolist;
  private ArrayList<ViewUpcomingModel> demolist;
    CompleteEvents completeEvents;


    public ViewUpcomingFragAdapter(Context context, Fragment fragment, ArrayList<ViewUpcomingModel> demolist) {
        this.context = context;
        this.demolist = demolist;
        this.completeEvents=(CompleteEvents) fragment;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_view_upcoming, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, final int position) {

        ViewUpcomingModel demo= demolist.get(position);

        if (demo!=null) {

            holder.m_style.setText(demolist.get(position).getStyleid());
            holder.m_keyprocess.setText(demolist.get(position).getKeys());
            holder.m_planneddate.setText(demolist.get(position).getPlanned());

            holder.m_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    completeEvents.onUpcomingCompleteClick(demo, position);
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