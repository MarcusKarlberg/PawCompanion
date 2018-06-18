package com.example.marcu.pawcompanion.shared;
import java.util.*;
/**
 * Created by marcu on 3/14/2018.
 */

public class DogRepo {
    private List<Dog> dogs = new ArrayList<>();

    public DogRepo() {
        //mock data
        dogs.add(new Dog("Rigby"));
        dogs.add(new Dog("Molly"));
        dogs.add(new Dog("Rainbow"));
        dogs.add(new Dog("Deisel"));
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

    public List<Dog> getAllDogs(){
        return this.dogs;
    }
}
