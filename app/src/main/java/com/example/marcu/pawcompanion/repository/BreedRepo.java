package com.example.marcu.pawcompanion.repository;


import com.example.marcu.pawcompanion.data.Breed;

import java.util.*;

/**
 * Created by marcu on 3/18/2018.
 */

public final class BreedRepo {
    private ArrayList<Breed> breeds = new ArrayList<>();

    public BreedRepo() {
        breeds.add(new Breed(0,"Mini Schnauzer", 2, 2));
        breeds.add(new Breed(1, "Bull Dog", 2, 2));
        breeds.add(new Breed(2,"Great Dane", 3, 3));
    }

    public ArrayList<Breed> getAllBreeds(){
        return this.breeds;
    }
}
