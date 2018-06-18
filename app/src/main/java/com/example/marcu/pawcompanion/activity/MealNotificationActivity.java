package com.example.marcu.pawcompanion.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.sharedPrefs.SPreferences;

import java.util.ArrayList;

public class MealNotificationActivity extends AppCompatActivity {

    private static final String TAG = "MealNotificationActivit";
    private Button addDog_btn;
    TextView dogNameTextView;
    Button ok_btn;
    Button remindAgain_btn;
    SPreferences prefs;
    ArrayList<Dog> dogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_notification);
        dogList = prefs.load();

        findViews();
        setDogNameText();

        Log.d(TAG, "onCreate: MealNotificationActivit initiated");
    }

    public void findViews(){
        dogNameTextView = (TextView) findViewById(R.id.dogNameEditText);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        remindAgain_btn = (Button) findViewById(R.id.remindAgain_btn);
    }
    public void setDogNameText(){
        //Todo figure out how the name is chosen - what if there are multiple dogs in the list??
        if(dogList != null){
            dogNameTextView.setText(dogList.get(0).getName());
        }
    }
}
