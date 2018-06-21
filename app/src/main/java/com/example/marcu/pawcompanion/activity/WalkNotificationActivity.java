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

public class WalkNotificationActivity extends AppCompatActivity {

    private static final String TAG = "WalkNotificationActivity";

        private Button addDog_btn;
        TextView dogNameTextView;
        Button okButton;
        Button remindAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_notification);
        Log.d(TAG, "onCreate: WALKNOTIFICATION INITIATED!");

        findViews();
        setDogNameText();
    }
    public void findViews(){
        dogNameTextView = (TextView) findViewById(R.id.dogNameEditText);
        okButton = (Button) findViewById(R.id.okButton);
        remindAgainButton = (Button) findViewById(R.id.remindAgainButton);
    }
    public void setDogNameText(){

    }
}
