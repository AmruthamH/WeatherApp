package com.example.openweather;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

public class HourlyViewHolder extends RecyclerView.ViewHolder {


    public TextView time2pm;
    public ImageView imageViewH;
    public TextView temphour;
    public TextView hourlyweatherdesc;
    public TextView today;

    public HourlyViewHolder(@NonNull View itemView) {
        super(itemView);

        today = itemView.findViewById(R.id.today);
        time2pm = itemView.findViewById(R.id.time2pm);
        imageViewH = itemView.findViewById(R.id.imageViewH);
        temphour = itemView.findViewById(R.id.temphour);
        hourlyweatherdesc = itemView.findViewById(R.id.hourlyweatherdesc);
    }
}
