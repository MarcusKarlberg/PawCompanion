package com.example.marcu.pawcompanion.repository;


import com.example.marcu.pawcompanion.data.Breed;

import java.util.*;

/**
 * Created by marcu on 3/18/2018.
 */

public final class BreedRepo {
    private ArrayList<Breed> breeds = new ArrayList<>();

    // Todo: assign a 5 level system for activity level and size level.
    //Todo: add search bar

    public BreedRepo() {

        breeds.add(new Breed(0,"Australian Sheperds",0 ,0 ));
        breeds.add(new Breed(1,"Beagle",0 ,0 ));
        breeds.add(new Breed(2,"Bernese Mountain Dog",0 ,0 ));
        breeds.add(new Breed(3, "Boston Terrier",0 ,0 ));
        breeds.add(new Breed(4, "Boxer",0 ,0 ));
        breeds.add(new Breed(5,"Brittany",0 ,0 ));
        breeds.add(new Breed(6, "Bulldog",0 ,0 ));
        breeds.add(new Breed(7,"Cavalier King Charles",0 ,0 ));
        breeds.add(new Breed(8,"Chihuaua",0 ,0 ));
        breeds.add(new Breed(9,"Dachshound",0 ,0 ));
        breeds.add(new Breed(10,"Doberman Pinscher",0 ,0 ));
        breeds.add(new Breed(11,"French Bulldog",0 ,0 ));
        breeds.add(new Breed(12, "German Shepherd", 0, 0));
        breeds.add(new Breed(13,"Golden Retriever", 0, 0));
        breeds.add(new Breed(14, "Greate Dane",0 ,0 ));
        breeds.add(new Breed(15,"Havanese",0 ,0 ));
        breeds.add(new Breed(16,"Labrador", 0, 0));
        breeds.add(new Breed(17,"Mastiffs",0 ,0 ));
        breeds.add(new Breed(18, "Miniature Schnauzer",0 ,0 ));
        breeds.add(new Breed(19,"Pointer, short haired",0 ,0 ));
        breeds.add(new Breed(20,"Pomeranian",0 ,0 ));
        breeds.add(new Breed(21,"Poodle",0 ,0 ));
        breeds.add(new Breed(22,"Pug",0 ,0 ));
        breeds.add(new Breed(23, "Rottweiler",0 ,0 ));
        breeds.add(new Breed(24, "Shetland Sheepdog",0 ,0 ));
        breeds.add(new Breed(25,"Shih Tzu",0 ,0 ));
        breeds.add(new Breed(26,"Siberian Husky",0 ,0 ));
        breeds.add(new Breed(27,"Spaniel, Cocker",0 ,0 ));
        breeds.add(new Breed(28,"Spaniel, English Springer",0 ,0 ));
        breeds.add(new Breed(29,"Vizslas",0 ,0 ));
        breeds.add(new Breed(30,"Yorkshire Terrier",0 ,0 ));
    }

    public ArrayList<Breed> getAllBreeds(){
        return this.breeds;
    }
}
