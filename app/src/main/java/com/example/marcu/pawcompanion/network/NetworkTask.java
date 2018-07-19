package com.example.marcu.pawcompanion.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class NetworkTask extends AsyncTask<URL, Void, String> {

    private static final String TAG = "NetworkTask";

    private String searchResults;
    public NetworkAsyncResponse delegate;

//    public NetworkTask(NetworkAsyncResponse delegate){
//        this.delegate = delegate;
//    }

    @Override
    protected String doInBackground(URL... urls) {
        URL searchUrl = urls[0];
        this.searchResults = null;

        Log.i(TAG, "URL: " + searchUrl);
        try {
            this.searchResults =  NetworkUtils.getHttpUrlResponse(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "URL RESPONSE: " + searchResults );
        return this.searchResults;
    }

    @Override
    protected void onPostExecute(String result){
        delegate.returnResult(result);
        delegate.displayResult();
    }
}
