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

    private static final String TAG = "MealNotificationActivity";
    private Button addDog_btn;
    TextView dogNameTextView;
    Button okButton;
    Button remindAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_notification);

        findViews();
        setDogNameText();

        Log.d(TAG, "onCreate: MealNotificationActivity initiated");
    }

    public void findViews(){
        dogNameTextView = (TextView) findViewById(R.id.dogNameEditText);
        okButton = (Button) findViewById(R.id.okButton);
        remindAgainButton = (Button) findViewById(R.id.remindAgainButton);
    }
    public void setDogNameText(){

    }
}
