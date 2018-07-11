package com.example.marcu.pawcompanion.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.marcu.pawcompanion.adapter.DogListArrayAdapter;
import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.repository.DogRepo;
import com.example.marcu.pawcompanion.notification.NotificationMngr;
import com.example.marcu.pawcompanion.sharedPrefs.SPreferences;

import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    static final int ADD_DOG_REQUEST = 0;
    static final int UPDATE_DOG_REQUEST = 1;

    private Dog selectedDog;
    private ListView dogListView;
    private Button addDogButton, deleteDogButton;
    DogListArrayAdapter adapter;
    DogRepo dogRepo;
    ArrayList<Dog> dogList;
    SPreferences prefs;
    NotificationMngr notificationMngr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dogRepo = new DogRepo();
        loadSavedDogsFromPrefs();
        findViews();

        adapter = new DogListArrayAdapter(MainActivity.this, R.layout.list_item, dogRepo.getAllDogs());
        dogListView.setAdapter(adapter);

        setListViewOnClickListners();
        setDeleteDogButtonListener();

        notificationMngr = new NotificationMngr(this);
    }

    public void goToDogInfoInputActivity(View view){
        Intent intent = new Intent(this, DogInfoInputActivity.class);
        startActivityForResult(intent, ADD_DOG_REQUEST);
    }

    private void findViews(){
        deleteDogButton = (Button) findViewById(R.id.deleteDogButton);
        addDogButton = (Button) findViewById(R.id.addDog_btn);
        dogListView = (ListView) findViewById(R.id.dogListView);
    }

    private void loadSavedDogsFromPrefs(){
        prefs = new SPreferences(this);
        dogList = prefs.load();
        for (Dog d: dogList){
            dogRepo.addDog(d);
        }
    }

    private void setListViewOnClickListners(){
        dogListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Log.i(TAG, "*** Item:" + pos + "Long pressed ***");

                if(selectedDog != null){
                    Intent intent = new Intent(MainActivity.this, DogInfoInputActivity.class);
                    intent.putExtra("selectedDog", selectedDog);
                    startActivityForResult(intent, UPDATE_DOG_REQUEST);
                }

                return true;
            }
        });

        dogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "*** Item:" + position + "pressed ***");
                selectedDog = (Dog) dogList.get(position);
                Log.i(TAG, "***" + selectedDog.getName() + " pressed ***");
            }
        });
    }

    private void setDeleteDogButtonListener(){
        deleteDogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedDog != null){
                    dogList.remove(selectedDog);

                    notificationMngr.deleteNotifications(selectedDog);
                    //prefs.clearAllData();
                    prefs.save(dogList);
                    dogListView.invalidateViews();
                }
            }
        });
    }

    private void updateDog(Dog updatedDog){
        Optional<Dog> optionalDog = findDogById(updatedDog.getId());
        if(optionalDog.isPresent()){
            Dog dog = optionalDog.get();

            if(!dog.getName().equalsIgnoreCase(updatedDog.getName())){
                dog.setName(updatedDog.getName());
            }
            if(dog.getBreed() != updatedDog.getBreed()){
                dog.setBreed(updatedDog.getBreed());
            }
            if(!dog.getBirthDate().equals(updatedDog.getBirthDate())){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");
                dog.setBirthDate(updatedDog.getBirthDate().format(formatter));
            }
            if(dog.getWeight() != updatedDog.getWeight()){
                dog.setWeight(updatedDog.getWeight());
            }

            if(!dog.getFirstWalkTime().equals(updatedDog.getFirstWalkTime())){
                dog.setFirstWalkTime(updatedDog.getFirstWalkTime().toString());
            }

            if(!dog.getFirstMealTime().equals(updatedDog.getFirstMealTime())){
                dog.setFirstMealTime(updatedDog.getFirstMealTime().toString());
            }

            if(!isBlank(updatedDog.getImageUriString())){
                    dog.setImageUriString(updatedDog.getImageUriString());
            }

            dog.setWalkingDurationPerDay(dog.getBreed().getActivityLevel());
            dog.setWalkingDistancePerDay(dog.getBreed().getActivityLevel());
            dog.setIntervalWalkTime(dog.getBreed().getActivityLevel());
            dog.setIntervalMealTime();

            //Todo: will the old notification be deleted/replaced
            notificationMngr.setWalkNotification(dog);
            notificationMngr.setMealNotification(dog);

            Log.i(TAG, "*** Dog has been update to: ***" + "ID: " + dog.getId() + " - " + dog);
        }
        else {
            Log.i(TAG, "*** OBS! DOG NOT FOUND ***");
        }
    }

    private Optional<Dog> findDogById(long id){
        return dogList.stream().filter(d -> d.getId() == id).findFirst();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_DOG_REQUEST) {

            if (resultCode == RESULT_OK) {
                Dog dog = (Dog) data.getSerializableExtra("dog");

                dogRepo.addDog(dog);
                if(dogRepo.getAllDogs() != null){
                    dogList = dogRepo.getAllDogs();
                    prefs.save(dogList);
                }

                notificationMngr.setWalkNotification(dog);
                notificationMngr.setMealNotification(dog);

                adapter.notifyDataSetChanged();
            }
        }

        if (requestCode == UPDATE_DOG_REQUEST){

            if(resultCode == RESULT_OK){
                Dog dog = (Dog) data.getSerializableExtra("dog");
                Log.i(TAG, "*** Dog info to update: ***" + dog);
                updateDog(dog);
                prefs.save(dogList);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
