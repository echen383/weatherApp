package com.example.emily.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {

    private EditText etCity;
    private Button btnSubmit;
    private TextView tvCurrentCity;
    private TextView tvCurrentTemp;
    private TextView tvHigh;
    private TextView tvSlash;
    private TextView tvLow;
    private ImageView ivForecastPic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        etCity = (EditText) findViewById(R.id.etCity);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        tvCurrentCity = (TextView) findViewById(R.id.tvCurrentCity);
        tvCurrentTemp = (TextView) findViewById(R.id.tvCurrentTemp);
        tvHigh = (TextView) findViewById(R.id.tvHigh);
        tvSlash = (TextView) findViewById(R.id.tvSlash);
        tvLow = (TextView) findViewById(R.id.tvLow);
        ivForecastPic = (ImageView) findViewById(R.id.ivForecastPic);
        
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(WeatherActivity.this, etCity.getText(), Toast.LENGTH_LONG).show();
                GetWeather getWeather = new GetWeather(WeatherActivity.this);
                getWeather.execute(etCity.getText().toString());
            }
        });
        
    }

    public void updateWeather(String json) {
        //update tvCurrentTemp text
        String city = null;
        String tempF = null;
        String tempHi = null;
        String tempLo = null;
        String forecast = null;

        try {
            city = new JSONObject(json)
                    .getJSONObject("data")
                    .getJSONArray("request")
                    .getJSONObject(0)
                    .getString("query");

            tempF = new JSONObject(json)
                    .getJSONObject("data")
                    .getJSONArray("current_condition")
                    .getJSONObject(0)
                    .getString("temp_F");

            tempHi = new JSONObject(json)
                    .getJSONObject("data")
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("maxtempF");

            tempLo = new JSONObject(json)
                    .getJSONObject("data")
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("mintempF");

            forecast = new JSONObject(json)
                    .getJSONObject("data")
                    .getJSONArray("current_condition")
                    .getJSONObject(0)
                    .getJSONArray("weatherDesc")
                    .getJSONObject(0)
                    .getString("value");


            tvCurrentCity.setText(city);
            tvCurrentTemp.setText(tempF);
            tvHigh.setText(tempHi);
            tvLow.setText(tempLo);
            updateCurrentTempColor(Integer.parseInt(tempF));
            updateForecastPic(forecast);


        } catch (JSONException e) {
            Toast.makeText(this, "Hey! There was an error!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private void updateCurrentTempColor(int temp) {
        int color;

        if (temp < 0) {
            color = getResources().getColor(R.color.primary_indigo);
        } else if (temp < 10) {
            color = getResources().getColor(R.color.primary_blue);
        } else if (temp < 20) {
            color = getResources().getColor(R.color.primary_light_blue);
        } else if (temp < 30) {
            color = getResources().getColor(R.color.primary_teal);
        } else if (temp < 40) {
            color = getResources().getColor(R.color.primary_green);
        } else if (temp < 50) {
            color = getResources().getColor(R.color.primary_light_green);
        } else if (temp < 60) {
            color = getResources().getColor(R.color.primary_lime);
        } else if (temp < 70) {
            color = getResources().getColor(R.color.primary_yellow);
        } else if (temp < 80) {
            color = getResources().getColor(R.color.primary_amber);
        } else if (temp < 90) {
            color = getResources().getColor(R.color.primary_orange);
        } else {
            color = getResources().getColor(R.color.primary_red);
        }

        tvCurrentTemp.setTextColor(color);
    }

    private void updateForecastPic(String forecast) {
        if (forecast.equals("Sunny") || forecast.equals("Clear"))
            ivForecastPic.setImageResource(R.drawable.sunny);
        else if (forecast.equals("Overcast") || forecast.equals("Cloudy"))
            ivForecastPic.setImageResource(R.drawable.cloudy);
        else if (forecast.contains("thunder"))
            ivForecastPic.setImageResource(R.drawable.storm);
        else if (forecast.contains("snow") || forecast.contains("sleet") || forecast.contains("ice"))
            ivForecastPic.setImageResource(R.drawable.snow);
        else if (forecast.contains("rain") || forecast.contains("mist"))
            ivForecastPic.setImageResource(R.drawable.rain);
        else
            ivForecastPic.setImageResource(R.drawable.partlycloudy);

    }

}
