package com.example.openweather;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyViewHolder> {

    private List<com.example.openweather.DayRecord> dayRecordList;
    private DailyActivity dailyAcitivty;
    private static final String TAG = "DailyAdapter";

    DailyAdapter(List<DayRecord> dayRecordList, DailyActivity da){
        this.dayRecordList = dayRecordList;
        this.dailyAcitivty = da;

    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: DailyViewHolder");

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dailyrecyclerview,parent,false);

        itemView.setOnClickListener(dailyAcitivty);
        return new DailyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {

        DayRecord dayRecord =  dayRecordList.get(position);

        holder.datetimedrv.setText(dayRecord.getDatetimedrv());
        holder.highlowdrv.setText(dayRecord.getHighlowdrv());
        holder.brokencloudsdrv.setText(dayRecord.getBrokencloudsdrv());
        holder.precipdrv.setText(dayRecord.getPrecipdrv());
        holder.uvindexdrv.setText(dayRecord.getUvindexdrv());
        holder.hourly1drv.setText(dayRecord.getHourly1drv());
        holder.hourly2drv.setText(dayRecord.getHourly2drv());
        holder.hourly3drv.setText(dayRecord.getHourly3drv());
        holder.hourly4drv.setText(dayRecord.getHourly4drv());

        int iconResId = dailyAcitivty.getResources().getIdentifier(dayRecord.getImageViewdrv(), "drawable", dailyAcitivty.getPackageName());
        holder.imageViewdrv.setImageResource(iconResId);

    }

    @Override
    public int getItemCount() {
        return dayRecordList.size();
    }
}
