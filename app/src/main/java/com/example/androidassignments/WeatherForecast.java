package com.example.androidassignments;

import static com.example.androidassignments.ChatWindow.ACTIVITY_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecast extends AppCompatActivity {

    private ImageView weatherIcon;
    private TextView currentTempTextView;
    private TextView minTempTextView;
    private TextView maxTempTextView;
    private ProgressBar progressBar;
    private Spinner citySpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weatherIcon = findViewById(R.id.weather_icon);
        currentTempTextView = findViewById(R.id.current_temperature);
        minTempTextView = findViewById(R.id.min_temperature);
        maxTempTextView = findViewById(R.id.max_temperature);
        progressBar = findViewById(R.id.progressBar);
        //windSpeedTextView = findViewById(R.id.);
        citySpinner = findViewById(R.id.citySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cityArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String city = (String) parent.getItemAtPosition(position);
                //Log.i(TAG, "onItemSelected: " + city);
                ForecastQuery query = new ForecastQuery(city);
                query.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //set progressbar to visible
        progressBar.setVisibility(View.VISIBLE);

        //start the asyncTask
        //new ForecastQuery().execute("null");

    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String minTemperature;
        private String maxTemperature;
        private String currentTemperature;
        private Bitmap weatherIconBitmap;
        private String windSpeed;
        protected String city ;


        ForecastQuery(String city) {
            this.city = city;
        }
//        @Override
//        protected String doInBackground(String... params) {
//            // Your code to perform background operations, like fetching weather data
//            // For example: Download data from params[0] which is a URL
//            return null; // Replace with the result you want to return
//        }



        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Optional: Update your UI with progress here
        }

//        @Override
//        protected void onPostExecute(String result) {
//            // Update the UI with the fetched data
//            currentTempTextView.setText(currentTemperature);
//            minTempTextView.setText(minTemperature);
//            maxTempTextView.setText(maxTemperature);
//            weatherIcon.setImageBitmap(weatherIconBitmap);
//
//            // Hide the progress bar
//            progressBar.setVisibility(View.GONE);
//        }

        @Override
        protected void onPostExecute(String a) {
            progressBar.setVisibility(View.INVISIBLE);
            weatherIcon.setImageBitmap(weatherIconBitmap);
            currentTempTextView.setText(currentTemperature + "C\u00b0");
            minTempTextView.setText(minTemperature + "C\u00b0");
            maxTempTextView.setText(maxTemperature + "C\u00b0");
            //win.setText(windSpeed + "km/h");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(
                        "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=0497e5af70a543be40e3675415e66036&mode=xml&units=metric");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream in = conn.getInputStream();
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);
                    int type;
                    //While you're not at the end of the document:
                    while ((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT) {
                    //Are you currently at a Start Tag?
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("temperature")) {
                                currentTemperature = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                minTemperature = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                maxTemperature = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            } else if (parser.getName().equals("weather")) {
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + ".png";
                                Log.i(ACTIVITY_NAME, "Looking for file: " + fileName);
                                if (fileExistance(fileName)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(fileName);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i(ACTIVITY_NAME, "Found the file locally");
                                    weatherIconBitmap = BitmapFactory.decodeStream(fis);
                                } else {
                                    String iconUrl = "https://openweathermap.org/img/w/" + fileName;
                                    weatherIconBitmap = getImage(new URL(iconUrl));
                                    FileOutputStream outputStream =
                                            openFileOutput(fileName, Context.MODE_PRIVATE);
                                    weatherIconBitmap.compress(Bitmap.CompressFormat.PNG,
                                            80, outputStream);
                                    Log.i(ACTIVITY_NAME,
                                            "Downloaded the file from the Internet");
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            } else if (parser.getName().equals("wind")) {
                                parser.nextTag();
                                if (parser.getName().equals("speed")) {
                                    windSpeed = parser.getAttributeValue(null, "value");
                                }
                            }
                        }
                        parser.next();
                    }
                } finally {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return "";
        }




        public boolean fileExistance(String fname) {
            File file = getFileStreamPath(fname); return file.exists();
        }

        public Bitmap getImage(URL url) {
            HttpsURLConnection connection = null; try {
                connection = (HttpsURLConnection) url.openConnection(); connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream()); } else
                    return null;
            } catch (Exception e) {
                return null; }
        }

//        @Override
//        protected void onPostExecute(String a) {
//            progressBar.setVisibility(View.INVISIBLE);
//            weatherIcon.setImageBitmap(weatherIconBitmap);
//            currentTempTextView.setText(currentTemperature + "C\u00b0");
//            minTempTextView.setText(minTemperature + "C\u00b0");
//            maxTempTextView.setText(maxTemperature + "C\u00b0");
//            //win.setText(windSpeed + "km/h");
//        }


    }
}