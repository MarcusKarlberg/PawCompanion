package com.example.marcu.pawcompanion.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.data.Dog;
import java.io.FileNotFoundException;

public class WalkNotificationActivity extends AppCompatActivity {

    private static final String TAG = "WalkNotificationActivity";

    private TextView dogNameTextView;
    private ImageView imageView;
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

    private void findViews(){
        dogNameTextView = (TextView) findViewById(R.id.dogNameEditText);
        imageView = (ImageView) findViewById(R.id.imageView);
        walkingDistanceTextView = (TextView) findViewById(R.id.walkingDistanceTextView);
        walkingDurationTextView = (TextView) findViewById(R.id.walkingDurationTextView);
        okButton = (Button) findViewById(R.id.okButton);
        remindAgainButton = (Button) findViewById(R.id.remindAgainButton);
    }

    private void setDogInfo(){
        if (dog != null) {
            dogNameTextView.setText(dog.getName());
            setImageView();
            walkingDistanceTextView.setText(String.format("Distance %.1f km", getDistancePerWalk(dog.getWalkingDistancePerDay())));
            walkingDurationTextView.setText(String.format("Duration: %d min", getDurationPerWalk(dog.getWalkingDurationPerDay())));
        }
    }

    private double getDistancePerWalk(double distancePerDay) {
        int numberOfWalksPerDay;

        switch (dog.getIntervalWalkTime()){
            case 180: numberOfWalksPerDay = 7;
                break;
            case 240: numberOfWalksPerDay = 5;
                break;
            case 300: numberOfWalksPerDay = 4;
                break;
            case 360: numberOfWalksPerDay = 3;
                break;
            default: numberOfWalksPerDay = 3;
        }
        return distancePerDay/numberOfWalksPerDay;
    }

    private int getDurationPerWalk(double durationPerDay){
        int numberOfWalksPerDay;

        switch (dog.getIntervalWalkTime()){
            case 180: numberOfWalksPerDay = 7;
                break;
            case 240: numberOfWalksPerDay = 5;
                break;
            case 300: numberOfWalksPerDay = 4;
                break;
            case 360: numberOfWalksPerDay = 3;
                break;
            default: numberOfWalksPerDay = 3;
        }
        return (int) Math.round(durationPerDay/numberOfWalksPerDay);
    }

    private void setImageView(){
        String imageUriString = dog.getImageUriString();

        if(imageUriString != null){
            Uri imageUri = Uri.parse(imageUriString);
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                imageView.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                //Todo: what's the best practice to handle exceptions in android
                Log.d(TAG, "FileNotFoundException");
            }
        }
    }

}
