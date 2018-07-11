package com.example.marcu.pawcompanion.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.sharedPrefs.SPreferences;

import java.util.ArrayList;

public class WalkNotificationActivity extends AppCompatActivity {

    private static final String TAG = "WalkNotificationActivity";

    private TextView dogNameTextView;
    private TextView walkingDistanceTextView;
    private TextView walkingDurationTextView;
    private Button okButton;
    private Button remindAgainButton;
    private Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_notification);

        Bundle bundle = (getIntent().getExtras().getBundle("bundle"));
        if(bundle != null){
            dog = (Dog)bundle.getSerializable("dogData");
            Log.d(TAG, "DOG DATA:" + dog.toString() );
        }else {
            Log.d(TAG, "DOG DATA EMPTY:");
        }

        findViews();
        setDogInfo();
        setOkButtonClickListener();
        setRemindMeAgainButtonClickListener();
    }

    private void setOkButtonClickListener(){
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setRemindMeAgainButtonClickListener(){
        remindAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void findViews(){
        dogNameTextView = (TextView) findViewById(R.id.dogNameEditText);
        walkingDistanceTextView = (TextView) findViewById(R.id.walkingDistanceTextView);
        walkingDurationTextView = (TextView) findViewById(R.id.walkingDurationTextView);
        okButton = (Button) findViewById(R.id.okButton);
        remindAgainButton = (Button) findViewById(R.id.remindAgainButton);
    }

    public void setDogInfo(){
        if (dog != null) {
            dogNameTextView.setText(dog.getName());
            walkingDistanceTextView.setText("Distance: " + dog.getWalkingDistancePerDay() + "km");
            walkingDurationTextView.setText("Duration: " + dog.getWalkingDurationPerDay() + "mins");
        }
    }
}
