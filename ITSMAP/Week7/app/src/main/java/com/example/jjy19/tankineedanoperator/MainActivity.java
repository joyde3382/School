package com.example.jjy19.tankineedanoperator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button connectionCheckButton, weatherButton, JSONButton;
    TextView weatherDisplay;
    JSONObject data = null;
    private String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        weatherDisplay = findViewById(R.id.weatherView);

        JSONButton = findViewById(R.id.JSONBtn);
        JSONButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJSON();
            }
        });

        weatherButton = findViewById(R.id.weatherBtn);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJSON("Randers,dk");
            }
        });
        connectionCheckButton = findViewById(R.id.checkStatusBtn);
        connectionCheckButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkConnectionStatus();
        }
        });

    }


     public void checkConnectionStatus(){
         ConnectivityManager connectMan = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE );

         NetworkInfo wifiInfo = connectMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
         NetworkInfo dataInfo = connectMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

         if(wifiInfo.isConnectedOrConnecting()) {
             Toast("Device is connected to Wifi");
         }
         else if(dataInfo.isConnectedOrConnecting()) {
             Toast("Device is connected to Data");
         }


     }

     public void parseJSON() {
         try {
             JSONObject mainObject = data.getJSONObject("main");

             double tempString = Double.parseDouble(mainObject.getString("temp"));

             weatherDisplay.setText("The current temperature is: " + (tempString-273.15));

         } catch (final JSONException e) {
             Log.e(TAG, "Json parsing error: " + e.getMessage());
         }
     }

    @SuppressLint("StaticFieldLeak")
    public void getJSON(final String city) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=4f5d88ccc4aac74201b6c050bf87b1e8");

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";

                    while ((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONObject(json.toString());

                    if (data.getInt("cod") != 200) {
                        System.out.println("Cancelled");
                        return null;
                    }


                } catch (Exception e) {

                    System.out.println("Exception " + e.getMessage());
                    return null;
                }



                return null;
            }

            @Override
            protected void onPostExecute(Void Void) {
                if (data != null) {
                    Toast("Displaying weather");
                    Log.d("my weather received", data.toString());
                }

            }
        }.execute();

        // return data;
    }

    // simple toast function
    public void Toast(String message){
    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
    toast.show();
    }
}
