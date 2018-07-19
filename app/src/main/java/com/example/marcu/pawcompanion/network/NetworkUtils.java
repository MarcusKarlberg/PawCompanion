package com.example.marcu.pawcompanion.network;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String BASE_URL = "http://192.168.0.108:8080/breed";

    public static URL buildUrl(){
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            //Todo: figure out how to handle this exception properly
            e.printStackTrace();
        }

        return url;
    }

    public static String getHttpUrlResponse(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try{
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
