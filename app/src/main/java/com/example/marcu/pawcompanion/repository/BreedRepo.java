package com.example.marcu.pawcompanion.repository;


import com.example.marcu.pawcompanion.data.Breed;

import java.util.*;

/**
 * Created by marcu on 3/18/2018.
 */

public final class BreedRepo {

    private List<Breed> breeds = new LinkedList<>();
    private List<Breed> filteredBreeds = new LinkedList<>();

    //Todo: add at least the 200 most common dog breeds
    public BreedRepo() {
        breeds.add(new Breed(1,"Australian Sheperds",5 ,3 ));
        breeds.add(new Breed(2,"Beagle",5 ,2 ));
        breeds.add(new Breed(3,"Bernese Mountain Dog",4 ,5 ));
        breeds.add(new Breed(4,"Boston Terrier",4,2));
        breeds.add(new Breed(5,"Boxer",5,3));
        breeds.add(new Breed(6,"Brittany",5,3));
        breeds.add(new Breed(7, "Bulldog",3,2));
        breeds.add(new Breed(8,"Cavalier King Charles",3,2));
        breeds.add(new Breed(9,"Chihuahua",3,1));
        breeds.add(new Breed(10,"Dachshund",3,1));
        breeds.add(new Breed(11,"Doberman Pinscher",4,4));
        breeds.add(new Breed(12,"French Bulldog",2 ,2 ));
        breeds.add(new Breed(13, "German Shepherd", 5, 4));
        breeds.add(new Breed(14,"Golden Retriever", 5, 3));
        breeds.add(new Breed(15, "Greate Dane",5 ,5 ));
        breeds.add(new Breed(16,"Havanese",3,2));
        breeds.add(new Breed(17,"Labrador", 5, 4));
        breeds.add(new Breed(18,"Mastiffs",3,5));
        breeds.add(new Breed(19, "Miniature Schnauzer",4 ,2 ));
        breeds.add(new Breed(20,"Shorthaired Pointer",5,3));
        breeds.add(new Breed(21,"Pomeranian",3,1));
        breeds.add(new Breed(22,"Poodle",4,4));
        breeds.add(new Breed(23,"Pug",2,2));
        breeds.add(new Breed(24, "Rottweiler",4,3));
        breeds.add(new Breed(25, "Shetland Sheepdog",4,1));
        breeds.add(new Breed(26,"Shih Tzu",2,1));
        breeds.add(new Breed(27,"Siberian Husky",5,3));
        breeds.add(new Breed(28,"Cocker Spaniel",3,2));
        breeds.add(new Breed(29,"English Springer Spaniel",5,3));
        breeds.add(new Breed(30,"Vizslas",5,3));
        breeds.add(new Breed(31,"Yorkshire Terrier",4,1));
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
