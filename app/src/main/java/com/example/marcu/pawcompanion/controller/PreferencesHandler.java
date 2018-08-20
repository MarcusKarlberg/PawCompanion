package com.example.marcu.pawcompanion.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.marcu.pawcompanion.activity.MainActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Dog;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PreferencesHandler extends Handler implements ActionHandlerContract.ActionHandler{

    private static final String DOG_INFO = "dog info";
    private static final String PREFS_KEY_DOG = "shared prefs key dog";
    private static final String DISCLAIMER_INFO = "disclaimer info";
    private static final String PREFS_KEY_DISCLAIMER = "shared prefs key disclaimer";

    public ActionHandlerContract.ActionHandler nextHandler;

    public PreferencesHandler(MainActivity activity) {
        super(activity);
    }

    @Override
    public void setNextHandler(ActionHandlerContract.ActionHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handle(HandlerType handlerType, Action action) {
        if(handlerType == HandlerType.PREFERENCES){
            updatePreferences(action);
        } else {
            nextHandler.handle(handlerType, action);
        }
    }

    private void updatePreferences(Action action){
        switch (action){
            case SAVE_DOGS_PREFERENCES:
                saveDog();
            break;
            case LOAD_DOGS_PREFERENCES:
                loadDogs();
            break;
        }
        getMainRootActionHandler().invokeAction(HandlerType.VIEW, Action.REFRESH_MAIN_VIEW);
    }

    private void saveDog(){
        List<Dog> dogs = getDogRepo().getAllDogs();
        SharedPreferences sharedPreferences = getMainActivity().getSharedPreferences(PREFS_KEY_DOG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        final Gson gson = Converters.registerLocalDate(Converters
                .registerLocalTime(new GsonBuilder())).create();

        String dogListJson = gson.toJson(dogs);
        editor.putString(DOG_INFO, dogListJson);
        editor.apply();

        Log.d(TAG, "save: SHAREDPREFERENCES SAVED! " + dogs.toString());
    }

    private void loadDogs(){
        SharedPreferences sharedPreferences = getMainActivity()
                .getSharedPreferences(PREFS_KEY_DOG, Context.MODE_PRIVATE);

        final Gson gson = Converters.registerLocalDate(Converters
                .registerLocalTime(new GsonBuilder())).create();

        String json = sharedPreferences.getString(DOG_INFO, null);

        Type dogListType = new TypeToken<ArrayList<Dog>>() {}.getType();
        List<Dog> dogs = gson.fromJson(json, dogListType);

        if(dogs == null){
            dogs = new LinkedList<>();
        }

        Log.d(TAG, "load: SHAREDPREFERENCES LOADED! " + dogs.toString());

        getDogRepo().load(dogs);
    }
}
