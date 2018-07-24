package com.example.marcu.pawcompanion.sharedPrefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.marcu.pawcompanion.data.Dog;
import com.fatboyindustrial.gsonjodatime.Converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by marcu on 3/16/2018.
 */

public class SPreferences {

    private static final String DOG_INFO = "DOG_INFO";
    private static final String PREFS_KEY = "shared prefs key";
    private Context context;
    private SharedPreferences prefs;
    private ArrayList<Dog> dogData;

    public SPreferences(Context context) {
        this.context = context;
    }

    public ArrayList<Dog> load(){
        prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        //Gson gson = new Gson();
        final Gson gson = Converters.registerLocalDate(Converters
                .registerLocalTime(new GsonBuilder())).create();

        String json = prefs.getString(DOG_INFO, null);
        //generic arrayList dog
        Type dogListType = new TypeToken<ArrayList<Dog>>() {}.getType();
        this.dogData = gson.fromJson(json, dogListType);

        //dogData cannot be null
        if(dogData == null){
            dogData = new ArrayList<Dog>();
        }

        Log.d(TAG, "load: SHAREDPREFERENCES LOADED! " + dogData.toString());
        return this.dogData;
    }

    public void save(ArrayList<Dog> dogList){
        prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        final Gson gson = Converters.registerLocalDate(Converters
                .registerLocalTime(new GsonBuilder())).create();
        String dogListJson = gson.toJson(dogList);
        editor.putString(DOG_INFO, dogListJson);
        editor.apply();

        Log.d(TAG, "save: SHAREDPREFERENCES SAVED! " + dogList.toString());
    }
}
