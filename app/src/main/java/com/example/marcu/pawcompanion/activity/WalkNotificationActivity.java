package com.example.marcu.pawcompanion.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.sharedPrefs.SPreferences;

import java.util.ArrayList;

public class WalkNotificationActivity extends AppCompatActivity {

    private static final String TAG = "WalkNotificationActivity";

    private TextView dogNameTextView;
    private Button okButton;
    private Button remindAgainButton;
    private Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_notification);
        Log.d(TAG, "onCreate: WALKNOTIFICATION INITIATED!");

        Bundle bundle = (getIntent().getExtras().getBundle("bundle"));
        if(bundle != null){
            dog = (Dog)bundle.getSerializable("dogData");
            Log.d(TAG, "DOG DATA:" + dog.toString() );
        }else {
            Log.d(TAG, "DOG DATA EMPTY:");
        }

        findViews();
        setDogInfo();
    }
    public void findViews(){
        dogNameTextView = (TextView) findViewById(R.id.dogNameEditText);
        okButton = (Button) findViewById(R.id.okButton);
        remindAgainButton = (Button) findViewById(R.id.remindAgainButton);
    }
    public void setDogInfo(){
        if (dog != null) {
            dogNameTextView.setText(dog.getName());
        }
    }
}
