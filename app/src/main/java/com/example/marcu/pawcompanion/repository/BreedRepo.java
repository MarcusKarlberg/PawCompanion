package com.example.marcu.pawcompanion.repository;


import com.example.marcu.pawcompanion.data.Breed;

import java.util.*;

/**
 * Created by marcu on 3/18/2018.
 */

public final class BreedRepo {

    private List<Breed> breeds = new LinkedList<>();
    private List<Breed> filteredBreeds = new LinkedList<>();

    // Todo: Add all breeds and assign a 5 level system for activity level and size level.
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
        breeds.add(new Breed(0,"French Bulldog",2 ,2 ));
        breeds.add(new Breed(12, "German Shepherd", 0, 0));
        breeds.add(new Breed(13,"Golden Retriever", 0, 0));
        breeds.add(new Breed(1, "Greate Dane",5 ,5 ));
        breeds.add(new Breed(15,"Havanese",0 ,0 ));
        breeds.add(new Breed(16,"Labrador", 0, 0));
        breeds.add(new Breed(17,"Mastiffs",0 ,0 ));
        breeds.add(new Breed(2, "Miniature Schnauzer",4 ,2 ));
        breeds.add(new Breed(19,"Pointer, short haired",0 ,0 ));
        breeds.add(new Breed(20,"Pomeranian",0 ,0 ));
        breeds.add(new Breed(21,"Poodle",0 ,0 ));
        breeds.add(new Breed(3,"Pug",3 ,0 ));
        breeds.add(new Breed(23, "Rottweiler",0 ,0 ));
        breeds.add(new Breed(24, "Shetland Sheepdog",0 ,0 ));
        breeds.add(new Breed(25,"Shih Tzu",0 ,0 ));
        breeds.add(new Breed(4,"Siberian Husky",5 ,3 ));
        breeds.add(new Breed(27,"Spaniel, Cocker",0 ,0 ));
        breeds.add(new Breed(28,"Spaniel, English Springer",0 ,0 ));
        breeds.add(new Breed(29,"Vizslas",0 ,0 ));
        breeds.add(new Breed(30,"Yorkshire Terrier",0 ,0 ));
    }

    public List<Breed> getAllBreeds(){
        return this.filteredBreeds;
    }

    public void setAll(List<Breed> breeds) {
        this.filteredBreeds = breeds;
    }

    public Breed get(int id){
        return filteredBreeds.get(id);
    }

    public int getIndexOf(int position) {
        return this.breeds.indexOf(this.filteredBreeds.get(position));
    }

    public void update(int id, Breed breed) {
        this.breeds.set(id, breed);
        this.filteredBreeds = this.breeds;
    }

    public List<Breed> resetFilteredList() {
        this.filteredBreeds = this.breeds;
        return this.getAllBreeds();
    }

}
