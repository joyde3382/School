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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button connectionCheckButton, weatherButton, JSONButton;
    TextView weatherDisplay;
    JSONObject data = null;
    Toast toast;
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
            protected Void doInBackground(Void... params) {

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=4f5d88ccc4aac74201b6c050bf87b1e8";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Toast("Recieved response string");
                                try {
                                    data = new JSONObject(response);
                                } catch (Exception e) {
                                    System.out.println("Exception " + e.getMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast("That didn't work!");
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);


                return null;
            }
        }.execute();
    }
//                try {
//                    URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=4f5d88ccc4aac74201b6c050bf87b1e8");
//
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//                    BufferedReader reader =
//                            new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//                    StringBuffer json = new StringBuffer(1024);
//                    String tmp = "";
//
//                    while ((tmp = reader.readLine()) != null)
//                        json.append(tmp).append("\n");
//                    reader.close();
//
//                    data = new JSONObject(json.toString());
//
//                    if (data.getInt("cod") != 200) {
//                        System.out.println("Cancelled");
//                        return null;
//                    }
//
//
//                } catch (Exception e) {
//
//                    System.out.println("Exception " + e.getMessage());
//                    return null;
//                }



//                return null;
//            }
//        }.execute();

        // return data;
//    }

    // simple toast function
    public void Toast(String message){

    if(toast != null){
        toast.cancel();
    }
    toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
    toast.show();
    }
}
