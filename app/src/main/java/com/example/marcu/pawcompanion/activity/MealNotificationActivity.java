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
    TextView nameEditText;
    Button okButton;
    Button remindAgainButton;
    Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_notification);

        Bundle bundle = (getIntent().getExtras().getBundle("bundle"));
        if(bundle != null){
            dog = (Dog)bundle.getSerializable("dogData");
            Log.d(TAG, "DOG DATA:" + dog.toString() );
        }else {
            Log.d(TAG, "DOG DATA EMPTY:");
        }

        findViews();
        setDogInfo();

        Log.d(TAG, "onCreate: MealNotificationActivity initiated");
    }

    public void findViews(){
        nameEditText = (TextView) findViewById(R.id.nameEditText);
        okButton = (Button) findViewById(R.id.okButton);
        remindAgainButton = (Button) findViewById(R.id.remindAgainButton);
    }
    public void setDogInfo(){
        if (dog != null) {
            nameEditText.setText(dog.getName());
        }
    }
}
