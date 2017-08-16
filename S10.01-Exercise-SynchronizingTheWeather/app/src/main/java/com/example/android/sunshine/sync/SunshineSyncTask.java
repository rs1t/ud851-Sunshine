package com.example.android.sunshine.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

//  DONE (1) Create a class called SunshineSyncTask
public class SunshineSyncTask {
//  DONE (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
    public synchronized static void syncWeather(Context context) {
//      DONE (3) Within syncWeather, fetch new weather data
        try {
            
            URL weatherRequestUrl = NetworkUtils.getUrl(context);
            
            String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
    
            ContentValues[] weatherValues = OpenWeatherJsonUtils
                    .getWeatherContentValuesFromJson(context, jsonWeatherResponse);
            
//          DONE (4) If we have valid results, delete the old data and insert the new
            if (weatherValues != null && weatherValues.length != 0) {
                ContentResolver contentResolver = context.getContentResolver();
    
                contentResolver.delete(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null);
                
                contentResolver.bulkInsert(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        weatherValues);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}