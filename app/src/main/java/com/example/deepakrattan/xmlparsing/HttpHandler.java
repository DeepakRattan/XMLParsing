package com.example.deepakrattan.xmlparsing;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by deepak.rattan on 5/5/2017.
 */

public class HttpHandler {
    String LOG_TAG = "HttpHandler";


    //CallServer() method will connect to the server by using HttpURLConnection class
    // and return the data in the form of inputstream.

    public InputStream CallServer(String remoteURL) {
        InputStream inStm = null;

        try {
            URL url = new URL(remoteURL);
            HttpURLConnection cc = (HttpURLConnection) url.openConnection();
            cc.setReadTimeout(5000);
            cc.setConnectTimeout(5000);
            cc.setRequestMethod("GET");
            cc.setDoInput(true);
            int response = cc.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                inStm = cc.getInputStream();
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in CallServer", e);
        }
        return inStm;

    }

    //StreamToString() method will convert inputstream into string format.
    public String StreamToString(InputStream stream) {


        InputStreamReader isr = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder response = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in StreamToString", e);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in StreamToString", e);
        } finally {

            try {
                stream.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error in StreamToString", e);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error in StreamToString", e);
            }
        }
        return response.toString();

    }

}