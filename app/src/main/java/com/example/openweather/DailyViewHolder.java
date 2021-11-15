package com.example.openweather;

import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

public class DailyViewHolder extends RecyclerView.ViewHolder {

    public TextView datetimedrv;
    public TextView highlowdrv;
    public TextView time1drv;
    public TextView time2drv;
    public TextView time3drv;
    public TextView time4drv;
    public TextView brokencloudsdrv;
    public TextView precipdrv;
    public TextView uvindexdrv;
    public TextView hourly1drv;
    public TextView hourly2drv;
    public TextView hourly3drv;
    public TextView hourly4drv;
    public ImageView imageViewdrv;


    public DailyViewHolder(@NonNull View itemView) {
        super(itemView);

        datetimedrv = itemView.findViewById(R.id.datetimedrv);
        highlowdrv = itemView.findViewById(R.id.highlowdrv);
        brokencloudsdrv = itemView.findViewById(R.id.brokencloudsdrv);
        precipdrv = itemView.findViewById(R.id.precipdrv);
        uvindexdrv = itemView.findViewById(R.id.uvindexdrv);
        hourly1drv = itemView.findViewById(R.id.hourly1drv);
        hourly2drv = itemView.findViewById(R.id.hourly2drv);
        hourly3drv = itemView.findViewById(R.id.hourly3drv);
        hourly4drv = itemView.findViewById(R.id.hourly4drv);
        time1drv = itemView.findViewById(R.id.time1drv);
        time2drv = itemView.findViewById(R.id.time2drv);
        time3drv = itemView.findViewById(R.id.time3drv);
        time4drv = itemView.findViewById(R.id.time4drv);
        imageViewdrv = itemView.findViewById(R.id.imageViewdrv);

    }
}
