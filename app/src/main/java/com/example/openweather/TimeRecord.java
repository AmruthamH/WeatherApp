package com.example.openweather;

import android.graphics.Bitmap;
import android.media.ImageReader;
import android.widget.ImageView;

import java.io.Serializable;


public class TimeRecord implements Serializable {

    private String  today;
    private String  time2pm;
    private String  imageViewH;
    private String  temphour;
    private String hourlyweatherdesc;
    private Long dateH;


    TimeRecord(String  today, String  time2pm,String  imageViewH,String  temphour,String hourlyweatherdesc, Long dateH){
        this.today = today;
        this.time2pm = time2pm;
        this.imageViewH = imageViewH;
        this.temphour = temphour;
        this.hourlyweatherdesc = hourlyweatherdesc;
        this.dateH=dateH;

    }

    public String getToday(){ return today; }

    public void setToday(String today) {
        this.today = today;
    }

    public String getTime2pm(){ return time2pm; }

    public void setTime2pm(String time2pm) {
        this.time2pm = time2pm;
    }

    public String getImageViewH() { return imageViewH;}

    public void setImageViewH(String imageViewH) {
        this.imageViewH = imageViewH;
    }

    public String getTemphour() {
        return temphour;
    }

    public void setTemphour(String temphour) {
        this.temphour = temphour;
    }

    public String getHourlyweatherdesc() {
        return hourlyweatherdesc;
    }

    public void setHw_weather_desc(String hourlyweatherdesc) {
        this.hourlyweatherdesc = hourlyweatherdesc;
    }

    public Long getDateH() {
        return dateH;
    }

}
