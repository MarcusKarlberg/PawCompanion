package com.example.marcu.pawcompanion.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class JsonUtils {

    public static ArrayList<Breed> JsonToJavaBreedObjects(String jsonString){
        Gson gson = new Gson();
        ArrayList<Breed> breedData = null;

        Type breedListType = new TypeToken<ArrayList<Breed>>() {}.getType();
        breedData = gson.fromJson(jsonString, breedListType);

        if(breedData == null){
            breedData = new ArrayList<Breed>();
        }

        return breedData;
    }
}
