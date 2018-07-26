package com.example.marcu.pawcompanion.activity;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.notification.NotificationMngr;
import org.joda.time.LocalTime;
import java.io.FileNotFoundException;

public class MealNotificationActivity extends AppCompatActivity {

    private static final String TAG = "MealNotificationActivity";
    TextView nameEditText;
    private ImageView imageView;
    TextView portionTextView;
    Button okButton;
    Button remindAgainButton;
    Dog dog;

    @SuppressLint("LongLogTag")
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

    private void findViews(){
        nameEditText = (TextView) findViewById(R.id.nameEditText);
        imageView = (ImageView) findViewById(R.id.imageView);
        portionTextView = (TextView) findViewById(R.id.portionTextView);
        okButton = (Button) findViewById(R.id.okButton);
        remindAgainButton = (Button) findViewById(R.id.remindAgainButton);
    }

    private void setDogInfo(){
        if (dog != null) {
            nameEditText.setText(dog.getName());
            setImageView(dog.getImageUriString());
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
                NotificationMngr notificationMngr = new NotificationMngr(getApplicationContext());
                LocalTime firstMealTime = dog.getFirstMealTime();
                dog.setFirstMealTime(LocalTime.now().plusMinutes(10).toString());
                notificationMngr.setMealNotification(dog);
                dog.setFirstMealTime(firstMealTime.toString());
                finish();
            }
        });
    }

    @SuppressLint("LongLogTag")
    private void setImageView(String imageUriString){

        if(imageUriString != null){
            Uri imageUri = Uri.parse(imageUriString);

            //to minimize memory -  outofmemoryerror
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 2;
            Bitmap bitmap;

            try {
                //Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, options);
                imageView.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                //Todo: what's the best practice to handle exceptions in android
                Log.d(TAG, "FileNotFoundException");
            } catch (SecurityException s){
                Toast.makeText(getApplicationContext(), "Permission denied: Photo Library", Toast.LENGTH_SHORT);
                s.printStackTrace();
            }
        }
    }
}
