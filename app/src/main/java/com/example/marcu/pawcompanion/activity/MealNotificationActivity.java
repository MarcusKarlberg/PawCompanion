package com.example.marcu.pawcompanion.activity;

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

public class MealNotificationActivity extends AppCompatActivity {

    private static final String TAG = "MealNotificationActivity";
    TextView nameEditText;
    TextView portionTextView;
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
        setOkButtonClickListener();
        setRemindMeAgainButtonClickListener();
    }

    public void findViews(){
        nameEditText = (TextView) findViewById(R.id.nameEditText);
        portionTextView = (TextView) findViewById(R.id.portionTextView);
        okButton = (Button) findViewById(R.id.okButton);
        remindAgainButton = (Button) findViewById(R.id.remindAgainButton);
    }

    public void setDogInfo(){
        if (dog != null) {
            nameEditText.setText(dog.getName());
        }
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
}
