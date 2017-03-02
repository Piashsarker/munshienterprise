package com.dcastalia.android.job_portal.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dcastalia.android.job_portal.Model.Jobs;
import com.dcastalia.android.job_portal.R;

import java.util.ArrayList;

/**
 * Created by PT on 3/2/2017.
 */

public class MyJobAdapter extends RecyclerView.Adapter<MyJobAdapter.ViewHolder> {

    private Context context ;
    private ArrayList<Jobs> jobsArrayList;
    public MyJobAdapter(Context context , ArrayList<Jobs> jobsArrayList){
        this.jobsArrayList = jobsArrayList ;
        this.context = context;
    }
    @Override
    public MyJobAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_open_list_item,parent,false);
        return  new MyJobAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.jobTitle.setText(jobsArrayList.get(position).getJob_title());
        holder.jobPosition.setText(jobsArrayList.get(position).getJob_position());
        holder.country.setText(jobsArrayList.get(position).getCountry());
        holder.date.setText(jobsArrayList.get(position).getDate());
    }


    @Override
    public int getItemCount() {
        return jobsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle , jobPosition , country , date ;
        RelativeLayout container;
        public ViewHolder(View itemView) {
            super(itemView);
            container = (RelativeLayout) itemView.findViewById(R.id.container);
            jobTitle = (TextView) itemView.findViewById(R.id.job_title);
            jobPosition = (TextView) itemView.findViewById(R.id.job_position);
            country = (TextView) itemView.findViewById(R.id.country);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
