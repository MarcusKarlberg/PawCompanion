package com.example.marcu.pawcompanion.repository;
import com.example.marcu.pawcompanion.data.Dog;

import java.util.*;
/**
 * Created by marcu on 3/14/2018.
 */

public class DogRepository {
    private List<Dog> dogs = new LinkedList<>();

    public DogRepository() {
    }

    public void addDog(Dog dog){
        this.dogs.add(dog);
    }

    public  void removeDog(Dog dog){
        this.dogs.remove(dog);
    }

    public void updateDog(int id, Dog dog) {
        dogs.set(id, dog);
    }

    public Dog getDogByListIndex(int id){
        return dogs.get(id);
    }

    public List<Dog> getAllDogs(){
        return this.dogs;
    }

    public int getIdByPosition(int positionId){
        return this.dogs.indexOf(positionId);
    }

    public Dog getById(int id){
        return dogs.get(id);
    }

    public void remove(Dog dog){
        dogs.remove(dog);
    }

}
