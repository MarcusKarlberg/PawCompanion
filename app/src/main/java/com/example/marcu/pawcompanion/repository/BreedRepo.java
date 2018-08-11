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
        breeds.add(new Breed(1,"Australian Sheperd",5 ,3 ));
        breeds.add(new Breed(2,"Affenpinscher",3,1));
        breeds.add(new Breed(3,"Afghan Hound",4,4));
        breeds.add(new Breed(4,"Airedale Terrier",5,3));
        breeds.add(new Breed(5,"Akita",4,4));
        breeds.add(new Breed(6,"Alaskan Malamute",5,4));
        breeds.add(new Breed(7,"American English Coonhound",5,3));
        breeds.add(new Breed(8,"American Eskimo Dog",4,2));
        breeds.add(new Breed(9,"American Foxhound",5,3));
        breeds.add(new Breed(10,"American Hairless Terrier",4,1));
        breeds.add(new Breed(12,"American Staffordshire Terrier",3,3));
        breeds.add(new Breed(13,"American Water Spaniel",5,3));
        breeds.add(new Breed(14,"Anatolian Shepherd Dog",3,5));
        breeds.add(new Breed(15,"Appenzeller Sennenhund",5,3));
        breeds.add(new Breed(16,"Australian Cattle Dog",5,3));
        breeds.add(new Breed(17,"Australian Kelpie",5,3));
        breeds.add(new Breed(19,"Australian Terrier",4,1));
        breeds.add(new Breed(20,"Azawakh",4,3));
        breeds.add(new Breed(21,"Barbet",3,3));
        breeds.add(new Breed(22,"Basenji",5,2));
        breeds.add(new Breed(24,"Basset Hound",2,2));
        breeds.add(new Breed(26,"Beagle",5 ,2 ));
        breeds.add(new Breed(27,"Bearded Collie",4,3));
        breeds.add(new Breed(29,"Bedlington Terrier",4,3));
        breeds.add(new Breed(31,"Belgian Malinois",5,4));
        breeds.add(new Breed(32,"Belgian Sheepdog",5,4));
        breeds.add(new Breed(33,"Belgian Tervuren",4,4));
        breeds.add(new Breed(35,"Berger Picard",5,4));
        breeds.add(new Breed(36,"Bernese Mountain Dog",4 ,5 ));
        breeds.add(new Breed(37,"Bichon Frise",4,2));
        breeds.add(new Breed(39,"Black and Tan Coonhound",5,3));
        breeds.add(new Breed(40,"Black Russian Terrier",4,5));
        breeds.add(new Breed(41,"Bloodhound",4,4));
        breeds.add(new Breed(42,"Bluetick Coonhound",5,3));
        breeds.add(new Breed(43,"Boerboel",4,5));
        breeds.add(new Breed(44,"Bolognese",3,1));
        breeds.add(new Breed(45,"Border Collie",5,3));
        breeds.add(new Breed(46,"Borzoi",2,5));
        breeds.add(new Breed(47,"Boston Terrier",4,2));
        breeds.add(new Breed(48,"Bouvier des Flandres",4,4));
        breeds.add(new Breed(49,"Boxer",5,3));
        breeds.add(new Breed(50,"Boykin Spaniel",5,3));
        breeds.add(new Breed(51,"Bracco Italiano",4,4));
        breeds.add(new Breed(54,"Briard",4,4));
        breeds.add(new Breed(55,"Brittany",5,3));
        breeds.add(new Breed(57,"Brussels Griffon",4,1));
        breeds.add(new Breed(58,"Bull Terrier",5,2));
        breeds.add(new Breed(59, "Bulldog",3,2));
        breeds.add(new Breed(60,"Bullmastiff",4,5));

        //Todo: get all dog breeds on 'C'
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));
//        breeds.add(new Breed(60,"",0,0));


        breeds.add(new Breed(61,"Cavalier King Charles",3,2));
        breeds.add(new Breed(62,"Chihuahua",3,1));
        breeds.add(new Breed(63,"Dachshund",3,1));
        breeds.add(new Breed(64,"Doberman Pinscher",4,4));
        breeds.add(new Breed(65,"French Bulldog",2 ,2 ));
        breeds.add(new Breed(66, "German Shepherd", 5, 4));
        breeds.add(new Breed(67,"Golden Retriever", 5, 3));
        breeds.add(new Breed(68, "Greate Dane",5 ,5 ));
        breeds.add(new Breed(69,"Havanese",3,2));
        breeds.add(new Breed(70,"Labrador", 5, 4));
        breeds.add(new Breed(71,"Mastiffs",3,5));
        breeds.add(new Breed(72, "Miniature Schnauzer",4 ,2 ));
        breeds.add(new Breed(73,"Shorthaired Pointer",5,3));
        breeds.add(new Breed(74,"Pomeranian",3,1));
        breeds.add(new Breed(75,"Poodle",4,4));
        breeds.add(new Breed(76,"Pug",2,2));
        breeds.add(new Breed(77, "Rottweiler",4,3));
        breeds.add(new Breed(78, "Shetland Sheepdog",4,1));
        breeds.add(new Breed(79,"Shih Tzu",2,1));
        breeds.add(new Breed(80,"Siberian Husky",5,3));
        breeds.add(new Breed(81,"Cocker Spaniel",3,2));
        breeds.add(new Breed(82,"English Springer Spaniel",5,3));
        breeds.add(new Breed(83,"Vizslas",5,3));
        breeds.add(new Breed(84,"Yorkshire Terrier",4,1));
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
