package com.example.marcu.pawcompanion.repository;
import com.example.marcu.pawcompanion.data.Dog;

import java.util.*;
/**
 * Created by marcu on 3/14/2018.
 */

public class DogRepo {
    private ArrayList<Dog> dogs = new ArrayList<>();

    public DogRepo() {
    }

    public void addDog(Dog dog){
        this.dogs.add(dog);
    }

    public  void removeDog(Dog dog){
        this.dogs.remove(dog);
    }

    public Dog getDogByListIndex(int id){
        return dogs.get(id);
    }

    public ArrayList<Dog> getAllDogs(){
        return this.dogs;
    }
}
