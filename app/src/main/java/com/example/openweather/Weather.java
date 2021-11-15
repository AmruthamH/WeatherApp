package com.example.openweather;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Weather implements Serializable {

    private final String latitude;
    private final String longitude;
    private final String datentime;
    private final String temp;
    private final String feelslike;
    private final String weatherdesc;
    private final String wind;
    private final String windSpeed;
    private final String humidity;
    private final String uvindex;
    private final String rainorsnow;
    private final String windGust;
    private final String visibility;
    private final String sunrise;
    private final String sunset;
    private final String brokenclouds;
    private final String hourly1;
    private final String hourly2;
    private final String hourly3;
    private final String hourly4;
    private final String weather_icon;
    private final List<TimeRecord> timeRecordArrayList;
    private  final List<DayRecord> dayRecordList;

    public Weather(String latitude, String longitude, String datentime, String temp, String feelslike, String weatherdesc, String wind, String windSpeed,String windGust, String humidity, String uvindex, String rainorsnow, String visibility, String sunrise, String sunset, String brokenclouds, String hourly1, String hourly2, String hourly3, String hourly4, String weather_icon,List<TimeRecord> timeRecordArrayList, List<DayRecord> dayRecordList) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.datentime = datentime;
        this.temp = temp;
        this.feelslike = feelslike;
        this.weatherdesc = weatherdesc;
        this.wind = wind;
        this.windSpeed = windSpeed;
        this.windGust = windGust;
        this.humidity = humidity;
        this.uvindex = uvindex;
        this.rainorsnow = rainorsnow;
        this.visibility = visibility;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.brokenclouds = brokenclouds;
        this.hourly1 = hourly1;
        this.hourly2 = hourly2;
        this.hourly3 = hourly3;
        this.hourly4 = hourly4;
        this.weather_icon = weather_icon;
        this.timeRecordArrayList = timeRecordArrayList;
        this.dayRecordList = dayRecordList;
    }


    String getLatitude(){return latitude;}

    String getLongitude(){ return  longitude;}

    String getDatentime(){ return datentime;}

    String getTemp() { return temp;}

    String getFeelslike() { return feelslike;}

    String getWeatherdesc() { return weatherdesc;}

    String getWind() { return wind;}

    String getWindSpeed(){ return windSpeed;}

    String getHumidity(){ return humidity;}

    String getWindGust(){ return windGust; }

    String getUvindex(){ return uvindex;}

    String getRainorsnow(){ return rainorsnow;}

    String getVisibility(){ return visibility;}

    String getHourly1(){ return hourly1;}

    String getHourly2(){ return hourly2;}

    String getHourly3(){ return hourly3;}

    String getHourly4(){ return hourly4;}

    String getSunrise(){ return sunrise;}

    String getSunset(){ return sunset;}

    String getBrokenclouds(){ return  brokenclouds;}

    String getWeather_icon() { return  weather_icon;}

    List<TimeRecord> getTimeRecordArrayList(){ return timeRecordArrayList;}

    List<DayRecord> getDayRecordList(){ return  dayRecordList;}
}
