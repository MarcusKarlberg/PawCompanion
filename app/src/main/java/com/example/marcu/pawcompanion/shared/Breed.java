package com.example.marcu.pawcompanion.shared;

/**
 * Created by marcu on 3/18/2018.
 */

public class Breed {
    private String breedName;

    //Todo: intergrate these variables
    private int activityLevel;
    private int sizeLevel;

    public Breed(String breedName) {
        this.breedName = breedName;
    }

    public String getBreedName() {
        return breedName;
    }

    @Override
    public String toString() {
        return "Breed{" +
                "breedName='" + breedName + '\'' +
                '}';
    }
}
