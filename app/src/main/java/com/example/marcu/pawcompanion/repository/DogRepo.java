package com.example.marcu.pawcompanion.repository;
import com.example.marcu.pawcompanion.data.Dog;

import java.util.*;
/**
 * Created by marcu on 3/14/2018.
 */

public class DogRepo {
    private ArrayList<Dog> dogs = new ArrayList<>();

    public DogRepo() {
        //mock data
//        dogs.add(new Dog("Rigby", new Breed("Mini Schanuzer"), "2017/04/16", 6.0, "08:00", "08:30"));
//        dogs.add(new Dog("Molly", new Breed("Tax"), "2015/08/10", 5.0, "07:00", "07:30"));
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

    public Dog getDogByName(String name){
        Dog dog = null;
        for (Dog d: dogs) {
            if(d.getName().equalsIgnoreCase(name)){
                dog = d;
                break;
            }
        }
        return dog;
        //return dogs.stream().filter(n -> n.getName().equalsIgnoreCase(name)).findFirst();
    }

    public ArrayList<Dog> getAllDogs(){
        return this.dogs;
    }
}
