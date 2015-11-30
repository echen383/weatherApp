package com.example.emily.weatherapp;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Emily on 11/11/15.
 */
public class GetWeather extends AsyncTask<String, Void, String> {      //first String=input type, second String=output type when finished

    //add key
    public static final String API_URL = "http://api.worldweatheronline.com/free/v2/weather.ashx?" +
            "q=%s&format=json&num_of_days=5&key=";
    private WeatherActivity weatherActivity;

    public GetWeather(WeatherActivity weatherActivity) {
        this.weatherActivity = weatherActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        //get the weather
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String result = null;

        try {
            //query URL
            String cityName = URLEncoder.encode(params[0], "UTF-8");
            URL url = new URL(String.format(API_URL,cityName));
            connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {          //check if connection is valid
                inputStream = connection.getInputStream();

                int ch;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                while ((ch = inputStream.read()) != -1) {
                    bos.write(ch);
                }

                result = new String(bos.toByteArray(), "UTF-8");
            }

        }

        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                connection.disconnect();
            }
        }

        return result;
    }

    @Override
    //execute after doInBackground finishes
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        //update screen with result
        weatherActivity.updateWeather(result);

    }
}
