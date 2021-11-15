package com.example.openweather;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyViewHolder> {

    private List<TimeRecord> timeRecordList;
    private MainActivity mainActivity;
    private static final String TAG = "HourlyAdapter";

    HourlyAdapter(List<TimeRecord> timeRecordList, MainActivity ma ){
        this.timeRecordList = timeRecordList;
        this.mainActivity =  ma;
    }
    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: HourlyViewHolder");

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_hourly,parent,false);
        itemView.setOnClickListener(mainActivity);
        return new HourlyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyViewHolder holder, int position) {
        TimeRecord timeRecord = timeRecordList.get(position);
        holder.today.setText(timeRecord.getToday());
        holder.time2pm.setText(timeRecord.getTime2pm());

        int iconResId = mainActivity.getResources().getIdentifier(timeRecord.getImageViewH(), "drawable", mainActivity.getPackageName());
        holder.imageViewH.setImageResource(iconResId);

        holder.temphour.setText(timeRecord.getTemphour());
        holder.hourlyweatherdesc.setText(timeRecord.getHourlyweatherdesc());

        holder.itemView.setOnClickListener(v -> {
            ((MainActivity) mainActivity).openCal(timeRecord.getDateH());
        });
    }

    @Override
    public int getItemCount() {
        return timeRecordList.size();
    }
}
