package com.example.assignment5app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Region> regionArrayList;
    ArrayAdapter<Region> regionArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regionArrayList = new ArrayList<>();
        regionArrayAdapter = new ArrayAdapter<>(this,R.layout.mytextview,R.id.myTextView,regionArrayList);


        final ListView myListView = findViewById(R.id.myListView);
        myListView.setAdapter(regionArrayAdapter);

        Button myButton = findViewById(R.id.targetButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int x, long id) {
                // Det item som klickats på skriver ut den förbestämda information funktionen info() håller
                String showInfo = regionArrayList.get(x).info();
                Snackbar.make(myListView, showInfo, Snackbar.LENGTH_LONG).show();
            }
        });

        new JsonTask().execute("https://wwwlab.iit.his.se/b18jacha/projekt.json");

    }

    @SuppressLint("StaticFieldLeak")
    private class JsonTask extends AsyncTask<String, String, String> {

        private HttpURLConnection connection = null;
        private BufferedReader reader = null;

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !isCancelled()) {
                    builder.append(line).append("\n");
                }
                return builder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                JSONArray myJSONArray = new JSONArray(json);
                for (int i = 0; i < myJSONArray.length(); i++) {
                    // JSON-objekt som Java
                    JSONObject myJSONObject = myJSONArray.getJSONObject(i);

                    // Hämtar ut beståndsdelarna
                    String name = myJSONObject.getString("name");
                    String category = myJSONObject.getString("category");
                    int size = myJSONObject.getInt("size");

                    // Skickar beståndsdelarna till konstruktorn
                    Region newRegion = new Region(name, category, size);
                    regionArrayList.add(newRegion);
                }
            } catch (JSONException e) {
                Log.e("brom","E:"+e.getMessage());
            }
            Log.d("TAG", json);
            regionArrayAdapter.notifyDataSetChanged();
        }
    }

}