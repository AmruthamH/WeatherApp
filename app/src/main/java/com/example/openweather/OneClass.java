package com.example.openweather;

import static java.time.LocalDateTime.ofEpochSecond;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class OneClass implements Runnable{

    private static final String TAG = "WeatherDownloadRunnable";

    private final MainActivity mainActivity;
    private final String city;
    private final String latitude;
    private final String longitude;
    private final boolean fahrenheit;

    private static final String weatherURL = "https://api.openweathermap.org/data/2.5/onecall?";//lat=41.8675766&lon=-87.616232&appid=c7c7fb5b6f95f4dc7dcea144c805e686&units=imperial&lang=en&exclude=minutely";
    private static final String yourAPIKey = "c7c7fb5b6f95f4dc7dcea144c805e686";


    public OneClass(MainActivity mainActivity,String city,boolean fahrenheit,String latitude,String longitude ) {
        this.mainActivity = mainActivity;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fahrenheit = fahrenheit;
    }

    @Override
    public void run() {

        Uri.Builder buildURL = Uri.parse(weatherURL).buildUpon();

        buildURL.appendQueryParameter("lat", latitude);
        buildURL.appendQueryParameter("lon", longitude);
        buildURL.appendQueryParameter("units", (fahrenheit ? "metric" : "imperial"));
        buildURL.appendQueryParameter("appid", yourAPIKey);
        buildURL.appendQueryParameter("lang", "en");
        buildURL.appendQueryParameter("exclude","minutely");

        String urlToUse = buildURL.build().toString();
        Log.d(TAG, "doInBackground: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try{
            URL url = new URL(urlToUse);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();

            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                InputStream is = connection.getErrorStream();
                BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                handleError(sb.toString());
                return;
            }

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            Log.d(TAG, "doInBackground: " + sb.toString());
        }
        catch (Exception e){
            Log.e(TAG, "doInBackground: ", e);
            handleResults(null);
            return;
        }
        handleResults(sb.toString());
    }

    public void handleError (String s){
        String msg = "Error: ";
        try {
            JSONObject jObjMain = new JSONObject(s);
            msg += jObjMain.getString("message");
        } catch (JSONException e) {
            msg += e.getMessage();
        }

        String finalMsg = String.format("%s (%s , %s)", msg, latitude,longitude);
        mainActivity.runOnUiThread(() -> mainActivity.handleError(finalMsg));
    }

    public void handleResults(final String jsonString) {
        final Weather w = parseJSON1(jsonString);
        mainActivity.runOnUiThread(() -> mainActivity.updateData(w));
    }

    public static String getDateTime(long dt, long timeZone, String desired_pattern) {
        LocalDateTime ldt = ofEpochSecond(dt + timeZone, 0, ZoneOffset.UTC);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(desired_pattern, Locale.getDefault());
        String formattedTimeString = ldt.format(dtf);
        return formattedTimeString;
    }

    private Weather parseJSON1(String s){
        try{
            //json reader
            JSONObject jObjMain = new JSONObject(s);
            String latitude = jObjMain.getString("lat");
            String longitude = jObjMain.getString("lon");

            //convert lat lon into city and country and pass those values in -> Main activity

            String timezone = jObjMain.getString("timezone");
            long timezone_offset = jObjMain.getLong("timezone_offset");


            // "current" section
            JSONObject jCurrent = jObjMain.getJSONObject("current");

            //convert current_dt into string format
            long current_date = jCurrent.getLong("dt");
            String dt = getDateTime(current_date,timezone_offset, "EEE MMM dd h:mm a, yyyy");

            //current_sunrise and current_sunset into string format
            long current_sunrise_long = jCurrent.getLong("sunrise");
            String sunrise = getDateTime(current_sunrise_long,timezone_offset, "hh:mm a");
            long current_sunset_long = jCurrent.getLong("sunset");
            String sunset = getDateTime(current_sunset_long,timezone_offset, "hh:mm a");

            Integer current_temp_int =  (int)(Math.round(jCurrent.getInt("temp")));
            String temp = current_temp_int.toString();
            Integer feelsLike_int =  (int)(Math.round(jCurrent.getInt("feels_like")));
            String feels_like = feelsLike_int.toString();
            String pressure = jCurrent.getString("pressure");
            String humidity = jCurrent.getString("humidity");
            String uv_idx = jCurrent.getString("uvi");
            String clouds = jCurrent.getString("clouds"); //75% clouds in main activity
            long visi = jCurrent.getLong("visibility");
            String visibility=String.format(String.valueOf(visi/1000));
            //String visibility = jCurrent.getString("visibility");

            String wind = jCurrent.getString("wind_deg");

            String wind_speed = jCurrent.getString("wind_speed"); //convert wind speed to mph

            String wind_gust;
            if(jCurrent.has("wind_gust"))
            {
                wind_gust = jCurrent.getString("wind_gust");
            }
            else{
                wind_gust = "";
            }

            //weather
            JSONArray weather = jCurrent.getJSONArray("weather");
            JSONObject jWeather = (JSONObject) weather.get(0);
            Integer weather_id = jWeather.getInt("id");
            String weather_main = jWeather.getString("main");
            String weather_desc = jWeather.getString("description");

            String weather_icon = "_"+jWeather.getString("icon");

            String rain_snow = "";
            if(jCurrent.has("rain"))
            {
                JSONObject jRain = jCurrent.getJSONObject("rain");
                String current_rain = jRain.getString("1h");
                rain_snow =  current_rain;
            }

            if(jCurrent.has("snow")){
                // snow - part of current
                JSONObject jSnow = jCurrent.getJSONObject("snow");
                String current_snow = jSnow.getString("1h");
                rain_snow = current_snow;
            }

            JSONArray daily = jObjMain.getJSONArray("daily");
            JSONObject jDaily = (JSONObject) daily.get(0);
            JSONObject jDailyTemp = jDaily.getJSONObject("temp");
            Integer hourly1_int =  (int)(Math.round(jDailyTemp.getInt("morn")));
            String hourly1 = hourly1_int.toString();
            Integer hourly2_int =  (int)(Math.round(jDailyTemp.getInt("day")));
            String hourly2 = hourly2_int.toString();
            Integer hourly3_int =  (int)(Math.round(jDailyTemp.getInt("eve")));
            String hourly3 = hourly3_int.toString();
            Integer hourly4_int =  (int)(Math.round(jDailyTemp.getInt("night")));
            String hourly4 = hourly4_int.toString();


            //hourly:

            ArrayList<TimeRecord> timelist = new ArrayList<>();
            // json reader

            JSONArray hourly = jObjMain.getJSONArray("hourly");
            for (int i = 0; i < hourly.length(); i++) {
                JSONObject jHourly = (JSONObject) hourly.get(i); //next 48 hours of data

                long h_dt = jHourly.getLong("dt");
                String h_day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date(h_dt * 1000));

                String current_dt_compare = getDateTime(current_date,timezone_offset, "dd");
                if(h_day.equals(current_dt_compare)){
                    h_day = "Today";
                }

                else{
                    h_day = getDateTime(h_dt,timezone_offset, "EEEE");
                }

                String h_time = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date(h_dt * 1000));

                Integer hourtemp_int =  (int)(Math.round(jHourly.getInt("temp")));
                String h_temp_string = hourtemp_int.toString();
                String h_temp = String.format("%s°" + (fahrenheit ? "C" : "F"), h_temp_string);

                JSONArray h_weather = jHourly.getJSONArray("weather");
                JSONObject jH_Weather = (JSONObject) h_weather.get(0);
                String h_weather_desc = jH_Weather.getString("description");

                //call the funcn which compares icon with icon_id from drawable folder and then pass it as a bitmap
                String h_weather_icon = "_"+jH_Weather.getString("icon");

                timelist.add(new TimeRecord(h_day, h_time, h_weather_icon, h_temp, h_weather_desc,h_dt));
            }

            ArrayList<DayRecord> dailyList = new ArrayList<>();
            for (int i = 0; i < daily.length(); i++) {
                JSONObject jDaily_all = (JSONObject) daily.get(i);
                long d_dt = jDaily_all.getLong("dt");
                String d_dt_all = new SimpleDateFormat("EEEE, MM/dd", Locale.getDefault()).format(new Date(d_dt * 1000));;

                JSONObject jDailyTemp_all = jDaily_all.getJSONObject("temp");


                Integer max_int =  (int)(Math.round(jDailyTemp_all.getInt("max")));
                String max_string = max_int.toString();
                String d_max_temp = String.format("%s°" + (fahrenheit ? "C" : "F"), max_string);

                Integer min_int =  (int)(Math.round(jDailyTemp_all.getInt("min")));
                String min_string = min_int.toString();
                String d_min_temp = String.format("%s°" + (fahrenheit ? "C" : "F"), min_string);

                String d_minmax_temp = d_max_temp +"/"+d_min_temp;

                Integer hourly1drv_int =  (int)(Math.round(jDailyTemp_all.getInt("morn")));
                String hourly1drv_string = hourly1drv_int.toString();
                String hourly1drv = String.format("%s°" + (fahrenheit ? "C" : "F"), hourly1drv_string);

                Integer hourly2drv_int =  (int)(Math.round(jDailyTemp_all.getInt("day")));
                String hourly2drv_string = hourly2drv_int.toString();
                String hourly2drv = String.format("%s°" + (fahrenheit ? "C" : "F"), hourly2drv_string);

                Integer hourly3drv_int =  (int)(Math.round(jDailyTemp_all.getInt("eve")));
                String hourly3drv_string = hourly3drv_int.toString();
                String hourly3drv = String.format("%s°" + (fahrenheit ? "C" : "F"), hourly3drv_string);

                Integer hourly4drv_int =  (int)(Math.round(jDailyTemp_all.getInt("night")));
                String hourly4drv_string = hourly4drv_int.toString();
                String hourly4drv = String.format("%s°" + (fahrenheit ? "C" : "F"), hourly4drv_string);


                JSONArray d_weather = jDaily_all.getJSONArray("weather");
                JSONObject jD_Weather = (JSONObject) d_weather.get(0);
                Integer id = jD_Weather.getInt("id");
                String d_weather_id = String.valueOf(id);
                String d_weather_desc = jD_Weather.getString("description");

                //Convert weather icon into a Bitmap
                String d_weather_icon = "_"+jD_Weather.getString("icon");

                //precipitation
                String precip_string = jDaily_all.getString("pop");
                String d_pop =String.format("(%s%% precip.)", precip_string);

                String uvindex_string = jDaily_all.getString("uvi");
                String d_uv_idx =String.format("UV Index: %s", uvindex_string);

                dailyList.add(new DayRecord(d_dt_all,d_minmax_temp,hourly1drv,hourly2drv,hourly3drv,
                            hourly4drv,d_weather_id,d_weather_desc,d_weather_icon,d_pop,d_uv_idx));
            }

            return new Weather(latitude,longitude,dt,temp,
                        feels_like,weather_desc,wind,wind_speed,wind_gust,humidity,
                        uv_idx, rain_snow,visibility,
                        sunrise,sunset,clouds,hourly1,hourly2,hourly3,hourly4,weather_icon, timelist,dailyList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

