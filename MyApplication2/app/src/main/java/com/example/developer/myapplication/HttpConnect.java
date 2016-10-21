package com.example.developer.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Developer on 20-Oct-16.
 */
public class HttpConnect {

    URL url;
    HttpURLConnection conn;
    InputStream inputStream;

    public InputStream connect(String api) {

        try {
            url = new URL(api);
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            inputStream = conn.getInputStream();
            //   BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            //   while ((data = br.readLine()) != null){
            //        text += data + "\n";
            //   }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputStream;
    }
}
