package com.example.openweather;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private boolean fahrenheit = false;
    private String latitude="41.8675766";
    private boolean rain =true;
    private String longitude="-87.616232";
    private String city;
    //private String current_city="Chicago,Illinois";
    private SharedPreferences.Editor editor;
    private final ArrayList<DayRecord> dayArrayList=new ArrayList<>();
    private final ArrayList<TimeRecord> timeArrayList = new ArrayList<>();
    private Weather weather;
    boolean hasNetworkConnection=false;
    private RecyclerView recycleView;
    private HourlyAdapter hAdapter;
    private SwipeRefreshLayout refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycleView = findViewById(R.id.houlryRecyclerview);
        hAdapter = new HourlyAdapter(timeArrayList,this);
        recycleView.setAdapter(hAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        Log.d(TAG, "onCreate: "+ hAdapter);
        SharedPreferences shared = getPreferences(Context.MODE_PRIVATE);
        editor = shared.edit();
        fahrenheit=shared.getBoolean("FAHREN",false);
        latitude=shared.getString("LAT",latitude);
        longitude=shared.getString("LONG",longitude);
        if (!shared.contains("FAHRENHEIT")) {
            editor.putBoolean("FAHREN", false);
            editor.putString("LAT",latitude);
            editor.putString("LONG",longitude);
            editor.apply();
        }


        TextView city_main = findViewById(R.id.city_main);
        city = city_main.getText().toString().trim().replaceAll(", ", ",");
        if(hasNetworkConnection(this)){
            hasNetworkConnection=true;
            editor.putBoolean("FAHREN", false);
            editor.putString("LAT",latitude);
            editor.putString("LONG",longitude);
            editor.apply();

            runThread(city,latitude,longitude,this.fahrenheit);
        }
        else{

            setInternetConnection();
        }

        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(()->{
            refresh.setRefreshing(false);
            if (hasNetworkConnection(this)) {
                hasNetworkConnection = true;
                editor.putBoolean("FAHREN", false);
                editor.putString("LAT",latitude);
                editor.putString("LONG",longitude);
                editor.apply();
                runThread(city,latitude,longitude,fahrenheit);
            } else {
                hasNetworkConnection = false;
                //city="";
                setInternetConnection();
                Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setInternetConnection() {
        hasNetworkConnection = false;

        TextView d = findViewById(R.id.datentime);
        d.setText("No internet connection");

        ImageView icon=findViewById(R.id.imageView);
        icon.setImageResource(android.R.color.transparent);

        TextView city=findViewById(R.id.city_main);
        city.setText("");

        TextView temp = findViewById(R.id.temp);
        temp.setText("");

        TextView wind = findViewById(R.id.wind);
        wind.setText("");

        TextView humid = findViewById(R.id.humidity);
        humid.setText("");

        TextView uvinde = findViewById(R.id.uvindex);
        uvinde.setText("");

        TextView cloud = findViewById(R.id.brokenclouds);
        cloud.setText("");

        TextView visible = findViewById(R.id.visibility);
        visible.setText("");

        TextView feels = findViewById(R.id.feelslike);
        feels.setText("");

        TextView hourly1 = findViewById(R.id.hourly1);
        hourly1.setText("");

        TextView hourly2 = findViewById(R.id.hourly2);
        hourly2.setText("");

        TextView hourly3 = findViewById(R.id.hourly3);
        hourly3.setText("");

        TextView hourly4 = findViewById(R.id.hourly4);
        hourly4.setText("");

        TextView sunri = findViewById(R.id.sunrise);
        sunri.setText("");

        TextView sunse = findViewById(R.id.sunset);
        sunse.setText("");

        TextView hourlytime1=findViewById(R.id.hourlytime1);
        hourlytime1.setText("");

        TextView hourlytime2=findViewById(R.id.hourlytime2);
        hourlytime2.setText("");

        TextView hourlytime3=findViewById(R.id.hourlytime3);
        hourlytime3.setText("");

        TextView hourlytime4=findViewById(R.id.hourlytime4);
        hourlytime4.setText("");

        timeArrayList.clear();
        hAdapter.notifyDataSetChanged();

    }

    private boolean hasNetworkConnection(MainActivity mainActivity) {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }


    private void runThread(String city, String  latitude, String longitude, boolean fahrenheit){
        OneClass oc = new OneClass(this, city,fahrenheit,latitude,longitude);
        new Thread(oc).start();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.toggle:
                if(hasNetworkConnection){
                    Toast.makeText(this, "Toggle menu selected", Toast.LENGTH_SHORT).show();
                    fahrenheit = !fahrenheit;
                    if (fahrenheit == false) {
                        item.setIcon(getResources().getDrawable(R.drawable.units_f));
                        editor.putBoolean("FAHREN", false);
                        editor.apply();
                    } else {
                        item.setIcon(getResources().getDrawable(R.drawable.units_c));
                        editor.putBoolean("FAHREN", true);
                        editor.apply();

                    }
                    runThread(city, latitude, longitude, fahrenheit);
                }
                else{
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

                return true;
            case R.id.dailyforecast:
                if(hasNetworkConnection) {
                    Toast.makeText(this, "Daily forecast selected", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, DailyActivity.class);
                    intent.putExtra("temp", dayArrayList);
                    intent.putExtra("location", city);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

                return true;
            case R.id.location:
                if(hasNetworkConnection){
                    Toast.makeText(this, "New Location", Toast.LENGTH_SHORT).show();
                    LayoutInflater inflater = LayoutInflater.from(this);
                    @SuppressLint("InflateParams")
                    final View view = inflater.inflate(R.layout.activity_alert, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("For US locations, enter as 'City' or 'City State'" + "For international locations enter 'City,Country'  ");
                    builder.setTitle("Enter Location");
                    builder.setView(view);
                    builder.setPositiveButton("OK", (dialog, id) -> {
                        EditText et1 = view.findViewById(R.id.alert);
                        String locationName=et1.getText().toString();
                        try{

                            if(!locationName.isEmpty() && locationName!=null && !locationName.equals("")){
                                double[] latlon = getLatLon(this,locationName);
                                String currentLocationFromLatLon = getLocationName(MainActivity.this, locationName);
                                if(!locationName.isEmpty() && locationName!=null && latlon.length>0 && latlon!=null){
                                    latitude=Double.toString(latlon[0]);
                                    longitude=Double.toString(latlon[1]);
                                    editor.putBoolean("FAHREN", false);
                                    editor.putString("LAT",latitude);
                                    editor.putString("LONG",longitude);
                                    //editor.putString("city",city);
                                    editor.apply();
                                    runThread(city,latitude,longitude,fahrenheit);
                                    Toast.makeText(MainActivity.this,"OK",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Please enter the correct location",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(MainActivity.this,"Please enter the correct location",Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(MainActivity.this,"Please enter the correct location",Toast.LENGTH_SHORT).show();

                        }catch(Exception e){
                            Toast.makeText(this, "Invalid City", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //This works fine
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private String getLocationName(Context context,String userProvidedLocation){
        Geocoder geocoder = new Geocoder(context);
        try{
            List<Address> address = geocoder.getFromLocationName(userProvidedLocation, 1);
            String country = address.get(0).getCountryCode();
            String p1 = "";
            String p2 = "";

            if (address == null || address.isEmpty()) { // Nothing returned!
                return null;
            }
            if (country.equals("US")) {
                p1 = address.get(0).getLocality();
                p2 = address.get(0).getAdminArea();
            }else {
                p1 = address.get(0).getLocality();
                if (p1 == null)
                    p1 = address.get(0).getSubAdminArea();
                p2 = address.get(0).getCountryName();
            }
            String locale = p1 + ", " + p2;
            return locale;

        }
        catch (IOException e){
            // Failure to get an Address object
            return null;
        }
    }


    public void handleError(String s) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Data Problem").setMessage(s).setPositiveButton("OK", (dialogInterface, i) -> {}).create().show();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "The back button pressed", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    private String getDirection(double degrees) { if (degrees >= 337.5 || degrees < 22.5)
        return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X"; // We'll use 'X' as the default if we get a bad value
    }



    private static double[] getLatLon(Context context,String userProvidedLocation) {
        Geocoder geocoder = new Geocoder(context); // Here, “this” is an Activity
        try {
            List<Address> address = geocoder.getFromLocationName(userProvidedLocation, 1);
            if (address == null || address.isEmpty()) { // Nothing returned!
                return null;
            }
            double lati = address.get(0).getLatitude();
            double longi = address.get(0).getLongitude();
            return new double[]{lati, longi};
        } catch (IOException e) {
            return null;
        }
    }

    public void updateData(Weather weather) {
        if (weather == null) {
            Toast.makeText(this, "Please Enter a Valid City Name", Toast.LENGTH_SHORT).show();
            return;
        }
        this.weather = weather;
        hasNetworkConnection = true;

        TextView city = findViewById(R.id.city_main);
        String latlon = String.format("%s, %s", weather.getLatitude(), weather.getLongitude());
        city.setText(getLocationName(this,latlon));
        this.city=getLocationName(this,latlon);

        TextView temp = findViewById(R.id.temp);
        temp.setText(String.format("%s° " + (fahrenheit ? "C" : "F"), weather.getTemp()));

        TextView date = findViewById(R.id.datentime);
        date.setText(String.format("%s", weather.getDatentime()));

        TextView wind = findViewById(R.id.wind);

        Double wind_degrees =  Double.parseDouble(weather.getWind());
        String wind_gust = weather.getWindGust();
        if(wind_gust.equals("")) {
            wind.setText(String.format("Winds: %s at %s  " + (fahrenheit ? "mps" : "mph"), getDirection(wind_degrees), weather.getWindSpeed()));
        }
        else{
            wind.setText(String.format("Winds: %s at %s " + (fahrenheit ? "mps" : "mph")+ " gusting to %s " +(fahrenheit ? "mps" : "mph"), getDirection(wind_degrees), weather.getWindSpeed(),weather.getWindGust()));
        }

        TextView humid = findViewById(R.id.humidity);
        humid.setText(String.format("Humidity: %s %%", weather.getHumidity()));

        ImageView image = findViewById(R.id.imageView);
        int iconResId = this.getResources().getIdentifier(weather.getWeather_icon(), "drawable", this.getPackageName());
        image.setImageResource(iconResId);

        TextView uvinde = findViewById(R.id.uvindex);
        uvinde.setText(String.format("UV Index: %s", weather.getUvindex()));

        TextView cloud = findViewById(R.id.brokenclouds);
        cloud.setText(String.format(Locale.getDefault(), "Broken clouds(%.0f%%) clouds", Double.parseDouble(weather.getBrokenclouds())));

        TextView visible = findViewById(R.id.visibility);
        visible.setText(String.format("Visibility: %s mi", weather.getVisibility()));

        TextView feels = findViewById(R.id.feelslike);
        feels.setText(String.format("Feels Like: %s°" + (fahrenheit ? "C" : "F"), weather.getFeelslike()));

        TextView hourly1 = findViewById(R.id.hourly1);
        hourly1.setText(String.format("%s°" + (fahrenheit ? "C" : "F"), weather.getHourly1()));

        TextView hourly2 = findViewById(R.id.hourly2);
        hourly2.setText(String.format("%s°" + (fahrenheit ? "C" : "F"), weather.getHourly2()));

        TextView hourly3 = findViewById(R.id.hourly3);
        hourly3.setText(String.format("%s°" + (fahrenheit ? "C" : "F"), weather.getHourly3()));

        TextView hourly4 = findViewById(R.id.hourly4);
        hourly4.setText(String.format("%s°" + (fahrenheit ? "C" : "F"), weather.getHourly4()));

        TextView sunri = findViewById(R.id.sunrise);
        sunri.setText(String.format(Locale.getDefault(), "Sunrise: %s", weather.getSunrise()));

        TextView sunse = findViewById(R.id.sunset);
        sunse.setText(String.format(Locale.getDefault(), "Sunset: %s", weather.getSunset()));

        TextView hourlytime1=findViewById(R.id.hourlytime1);
        hourlytime1.setText("8 am");

        TextView hourlytime2=findViewById(R.id.hourlytime2);
        hourlytime2.setText("1 pm");

        TextView hourlytime3=findViewById(R.id.hourlytime3);
        hourlytime3.setText("5 pm");

        TextView hourlytime4=findViewById(R.id.hourlytime4);
        hourlytime4.setText("11 pm");


        if(timeArrayList.size() == 0)
            timeArrayList.addAll(weather.getTimeRecordArrayList());
        else{
            timeArrayList.clear();
            timeArrayList.addAll(weather.getTimeRecordArrayList());
        }
        hAdapter.notifyDataSetChanged();

        if(dayArrayList.size() == 0)
            dayArrayList.addAll(weather.getDayRecordList());
        else{
            dayArrayList.clear();
            dayArrayList.addAll(weather.getDayRecordList());
        }

        TextView current_rain_snow = findViewById(R.id.rainorsnow);
        if(!weather.getRainorsnow().isEmpty()) {
            current_rain_snow.setText(String.format((rain ? "Rain" : "Snow") + " last hour: %s in", weather.getRainorsnow()));
        } else{
            current_rain_snow.setText("");
        }

    }

    @Override
    public void onClick(View v) {
        int position = recycleView.getChildLayoutPosition(v);
        DayRecord dr = dayArrayList.get(position);
        Intent data = new Intent(this, DailyActivity.class);
        data.putExtra("dailyData", dr);
        data.putExtra("position", position);
        startActivity(data);

    }

    public void openCal(long msecs) {
        try {
            Uri.Builder calBuilder = CalendarContract.CONTENT_URI.buildUpon();
            calBuilder.appendPath("time");
            ContentUris.appendId(calBuilder, msecs*1000);
            Intent calIntent = new Intent(Intent.ACTION_VIEW).setData(calBuilder.build());
            startActivity(calIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong)", Toast.LENGTH_SHORT).show();
        }
    }


}