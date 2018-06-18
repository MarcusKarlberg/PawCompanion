package com.example.marcu.pawcompanion.repository;

import com.example.marcu.pawcompanion.data.Breed;

import java.util.*;

/**
 * Created by marcu on 3/18/2018.
 */

public class BreedRepo {
    private ArrayList<Breed> breeds = new ArrayList<>();

    public BreedRepo() {
        breeds.add(new Breed("Mini Schnauzer"));
        breeds.add(new Breed("Bull Dog"));
        breeds.add(new Breed("Great Dane"));
    }
    public ArrayList<Breed> getAllBreeds(){
        return this.breeds;
    }


}
