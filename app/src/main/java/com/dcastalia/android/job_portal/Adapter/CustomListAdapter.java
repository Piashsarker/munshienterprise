package com.dcastalia.android.job_portal.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dcastalia.android.job_portal.Model.Jobs;
import com.dcastalia.android.job_portal.R;

import java.util.List;

/**
 * Created by shahimtiyaj on 12/23/2016.
 */

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Jobs> jobItems;

    public CustomListAdapter(Activity activity, List<Jobs> jobItems) {
        this.activity = activity;
        this.jobItems = jobItems;
    }


    @Override
    public int getCount() {
        return jobItems.size();
    }

    @Override
    public Object getItem(int location) {
        return jobItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled (int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.job_open_list_item, parent,false);


       // TextView availableJob = (TextView) convertView.findViewById(R.id.available_job);
        TextView jobTitle = (TextView) convertView.findViewById(R.id.job_title);
        TextView jobPosition = (TextView) convertView.findViewById(R.id.job_position);
        TextView country = (TextView) convertView.findViewById(R.id.country);
        TextView date = (TextView) convertView.findViewById(R.id.date);


        // getting job opening data for the row
        Jobs job = jobItems.get(position);

        // AvailableJob
//        availableJob.setText(job.getAvailable_job());
        jobTitle.setText(job.getJob_title());
        jobPosition.setText(job.getJob_position());
        country.setText(job.getCountry());
        date.setText(job.getDate());



        return convertView;
    }
}