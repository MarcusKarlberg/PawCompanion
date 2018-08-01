package com.example.marcu.pawcompanion.repository;
import android.util.Log;

import com.example.marcu.pawcompanion.data.Dog;

import java.util.*;

import static android.content.ContentValues.TAG;

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

    public void load(List<Dog> dogsFromSharedPrefs){
        for (Dog d: dogsFromSharedPrefs){
            addDog(d);
        }
    }

    public  void removeDog(Dog dog){
        this.dogs.remove(dog);
    }

    public void updateDog(Dog dogToUpdate) {
        Dog dog = getByDogId(dogToUpdate.getId());
        int index = dogs.indexOf(dog);
        dogs.set(index, dogToUpdate);
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

    private Dog getByDogId(long id){
        List<Dog> dogs = getAllDogs();
        Dog dog = null;
        for (Dog d: dogs){
            if(id == d.getId()){
                dog = d;
            }
        }
        return dog;
    }
}
