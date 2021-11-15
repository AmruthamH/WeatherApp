package com.example.openweather;

import android.graphics.Bitmap;

import java.io.Serializable;


public class DayRecord implements Serializable {
    private String datetimedrv;
    private String highlowdrv;
    private String hourly1drv;
    private String hourly2drv;
    private String hourly3drv;
    private String hourly4drv;
    private String weatherIddrv;
    private String brokencloudsdrv;
    private String imageViewdrv;
    private  String precipdrv;
    private String uvindexdrv;

    public DayRecord(String datetimedrv, String highlowdrv, String hourly1drv, String hourly2drv, String hourly3drv, String hourly4drv, String weatherIddrv, String brokencloudsdrv, String imageViewdrv, String precipdrv, String uvindexdrv) {
        this.datetimedrv = datetimedrv;
        this.highlowdrv = highlowdrv;
        this.hourly1drv = hourly1drv;
        this.hourly2drv = hourly2drv;
        this.hourly3drv = hourly3drv;
        this.hourly4drv = hourly4drv;
        this.weatherIddrv = weatherIddrv;
        this.brokencloudsdrv = brokencloudsdrv;
        this.imageViewdrv = imageViewdrv;
        this.precipdrv = precipdrv;
        this.uvindexdrv = uvindexdrv;
    }

    public String getDatetimedrv() {
        return datetimedrv;
    }

    public void setDw_daydate(String datetimedrv){
        this.datetimedrv = datetimedrv;
    }

    public String getHighlowdrv() {
        return highlowdrv;
    }

    public void setDatetimedrv(String highlowdrv) {
        this.highlowdrv = highlowdrv;
    }

    public String getBrokencloudsdrv() {
        return brokencloudsdrv;
    }

    public void setBrokencloudsdrv(String brokencloudsdrv) {
        this.brokencloudsdrv = brokencloudsdrv;
    }

    public String getUvindexdrv() {
        return uvindexdrv;
    }

    public void setUvindexdrv(String uvindexdrv) {
        this.uvindexdrv = uvindexdrv;
    }

    public String getHourly1drv() {
        return hourly1drv;
    }

    public void setHourly1drv(String hourly1drv) {
        this.hourly1drv = hourly1drv;
    }

    public String getHourly2drv() {
        return hourly2drv;
    }

    public void setHourly2drv(String hourly2drv) {
        this.hourly2drv = hourly2drv;
    }

    public String getHourly3drv() {
        return hourly3drv;
    }

    public void setHourly3drv(String hourly3drv) {
        this.hourly3drv = hourly3drv;
    }

    public String getHourly4drv() {
        return hourly4drv;
    }

    public void setHourly4drv(String hourly4drv) {
        this.hourly4drv = hourly4drv;
    }

    public String getWeatherIddrv() {
        return weatherIddrv;
    }

    public void setWeatherIddrv(String weatherIddrv) {
        this.weatherIddrv = weatherIddrv;
    }

    public String getPrecipdrv() {
        return precipdrv;
    }

    public void setPrecipdrv(String precipdrv) {
        this.precipdrv = precipdrv;
    }

    public String getImageViewdrv() {
        return imageViewdrv;
    }

    public void setImageViewdrv(String imageViewdrv) {
        this.imageViewdrv = imageViewdrv;
    }

}

