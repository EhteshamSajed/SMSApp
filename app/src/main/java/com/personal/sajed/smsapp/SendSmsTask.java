package com.personal.sajed.smsapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Created by Sajed on 15-Oct-16.
 */
public class SendSmsTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
        String result= "";
        URL url;
        HttpURLConnection httpURLConnection = null;

        try{
            url = new URL(urls[0]);

            //httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection = (HttpURLConnection)url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStreamReader.read();

            while (data != -1) {

                char current = (char) data;

                result += current;

                data = inputStreamReader.read();
            }

            return result;

        }catch (Exception e){
            e.printStackTrace();

            return "Failed";
        }
    }

    @Override

    protected void onPostExecute(String result){
        super.onPostExecute(result);

        Log.i("urlResponse ",result);
    }
}
