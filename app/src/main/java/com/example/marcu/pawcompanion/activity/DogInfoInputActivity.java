package com.example.marcu.pawcompanion.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.data.Breed;
import com.example.marcu.pawcompanion.repository.BreedRepo;
import com.example.marcu.pawcompanion.data.Dog;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DogInfoInputActivity extends AppCompatActivity{
    private static final String TAG = "DogInfoInputActivity";
    private static final int ACCESS_PHOTO_LIB = 7686;
    private static final int SELECT_BREED_REQUEST = 2733;

    private EditText nameEditText, weightEditText;
    private Button saveDogButton;
    private ImageView imageView;
    private Uri imageUri;

    private TextView birthdayTextView, walkTimeTextView, mealTimeTextView, breedTextView;
    private DatePickerDialog.OnDateSetListener birthdayDateSetListener;
    private TimePickerDialog.OnTimeSetListener walkTimeSetListener, mealTimeSetListener;

    private ArrayList<Breed> breedList;
    private BreedRepo breedRepo;

    private Breed selectedBreed;
    String selectedBreedstring;

    private Dog selectedDog;

    private LocalDate currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentDate = LocalDate.now();

        setContentView(R.layout.activity_dog_info_input);

        breedRepo = new BreedRepo();
        breedList = breedRepo.getAllBreeds();
        findViews();
        Log.d(TAG, "DogInfoInputActivity:SECOND ACTIVITY ACTIVATED");

        Intent intent = getIntent();
        selectedDog = (Dog) intent.getSerializableExtra("selectedDog");
        Log.d(TAG, "DOG TO BE UPDATED 2: " + selectedDog);

        setBreedClickListener();
        setBirthdayClickListener();
        setBirthdatDateListener();
        setWalkTimeClickListener();
        setWalkTimeListener();
        setMealTimeClickListener();
        setmealTimeListener();
        setAddNewCompanionButtonClickListener();
        setImageViewClickListener();

        if(selectedDog != null){
            setDogInfo();
        }
    }

    private void findViews(){
        nameEditText = (EditText) findViewById(R.id.dogNameEditText);
        birthdayTextView = (TextView) findViewById(R.id.birthdayTextView);
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        walkTimeTextView = (TextView) findViewById(R.id.walkTimeTextView);
        mealTimeTextView = (TextView) findViewById(R.id.mealTimeTextView);
        breedTextView = (TextView) findViewById(R.id.breedTextView);
        saveDogButton = (Button) findViewById(R.id.saveCompanionButton);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    private void setDogInfo(){
        nameEditText.setText(selectedDog.getName());
        breedTextView.setText(selectedDog.getBreed().getName());
        selectedBreed = selectedDog.getBreed();
        weightEditText.setText(String.valueOf(selectedDog.getWeight()));
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
        birthdayTextView.setText(selectedDog.getBirthDate().toString(formatter));
        walkTimeTextView.setText(selectedDog.getFirstWalkTime().toString("hh:mm"));
        mealTimeTextView.setText(selectedDog.getFirstMealTime().toString("hh:mm"));
        setImageView(selectedDog.getImageUriString());
    }

    private void setImageViewClickListener(){
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(ActivityCompat.checkSelfPermission(DogInfoInputActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(DogInfoInputActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, ACCESS_PHOTO_LIB);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, ACCESS_PHOTO_LIB);
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        if(requestCode == ACCESS_PHOTO_LIB){
            //If request is denied array is empty
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, ACCESS_PHOTO_LIB);
            } else {
                Toast.makeText(DogInfoInputActivity.this, "Couldn't access photo-library", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACCESS_PHOTO_LIB){
            if(resultCode == RESULT_OK){
                imageUri = data.getData();
                setImageView(imageUri.toString());
            }
        }
        if(requestCode == SELECT_BREED_REQUEST){
            if(resultCode == RESULT_OK){
                //Select the breed
                this.selectedBreedstring = data.getStringExtra("selectedBreed");
                breedTextView.setText(this.selectedBreedstring);
                selectedBreed = breedRepo.getBreedByName(selectedBreedstring);
            }
        }
    }

    private void setBreedClickListener(){
        breedTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent pickBreedIntent = new Intent(DogInfoInputActivity.this, SelectBreedActivity.class);
                startActivityForResult(pickBreedIntent, SELECT_BREED_REQUEST);
            }
        });
    }

    private void setBirthdayClickListener(){
        birthdayTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                int year, month, day;

                    year = currentDate.getYear();
                    month = currentDate.getMonthOfYear();
                    day = currentDate.getDayOfMonth();

                DatePickerDialog dialog = new DatePickerDialog(DogInfoInputActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        birthdayDateSetListener,
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    private void setBirthdatDateListener(){
        birthdayDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = String.format("%02d", month) + "/" + String.format("%02d", day) + "/" + year;
                birthdayTextView.setText(date);
            }
        };
    }

    private void setWalkTimeClickListener(){
        walkTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(DogInfoInputActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        walkTimeSetListener, 7,0, true);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    private void setWalkTimeListener(){
        walkTimeSetListener = (new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String time = String.format("%02d", hour) + ":" + String.format("%02d", minute);
                walkTimeTextView.setText(time);
            }
        });
    }

    private void setMealTimeClickListener(){
        mealTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(DogInfoInputActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        mealTimeSetListener, 7,0, true);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    private void setmealTimeListener(){
        mealTimeSetListener = (new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String time = String.format("%02d", hour) + ":" + String.format("%02d", minute);
                mealTimeTextView.setText(time);
            }
        });
    }

    private void setAddNewCompanionButtonClickListener(){
        saveDogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateDogInput()){
                    Log.d(TAG, "Validation TRUE");
                    saveDogInfo(view);
                }
            }
        });
    }

    private void saveDogInfo(View view) {
            Log.d(TAG, "DogInfoInputActivity:SAVE BUTTON PRESSED");
            String name = nameEditText.getText().toString();
            String birthday = birthdayTextView.getText().toString();
            Double weight = Double.parseDouble(weightEditText.getText().toString());
            String firstMealTime = mealTimeTextView.getText().toString();
            String firstWalkTime = walkTimeTextView.getText().toString();

            Intent intent = new Intent();

            if(selectedDog == null){
                Dog dog = new Dog(name, selectedBreed, birthday, weight, firstMealTime, firstWalkTime);
                if(imageUri != null){
                    dog.setImageUriString(imageUri.toString());
                }
                intent.putExtra("dog", dog);
                setResult(RESULT_OK, intent);
                Log.d(TAG, "DogInfoInputActivity: NEW DOG CREATED: " + dog.toString());
                finish();
            }else{
                updateSelectedDog(name, birthday, weight, firstMealTime, firstWalkTime);
                intent.putExtra("dog", selectedDog);
                setResult(RESULT_OK, intent);
                Log.d(TAG, "DogInfoInputActivity: DOG UPDATED: " + selectedDog.toString());
                finish();
            }


            Log.d(TAG, "DogInfoInputActivity: FINISH INITIATED");
    }

    private void updateSelectedDog(String name, String birthday, double weight, String firstMealTime, String firstWalkTime){
        selectedDog.setName(name);
        selectedDog.setBreed(selectedBreed);
        selectedDog.setBirthDate(birthday);
        selectedDog.setWeight(weight);
        selectedDog.setFirstMealTime(firstMealTime);
        selectedDog.setFirstWalkTime(firstWalkTime);

        if(imageUri != null){
            selectedDog.setImageUriString(imageUri.toString());
        }
    }

    private boolean validateDogInput(){
        if(StringUtils.isBlank(nameEditText.getText())){
           showToast("invalid name");
            return false;
        }

        if(birthdayTextView.getText().toString().equalsIgnoreCase("set date")){
            showToast("invalid birthday");
            return false;
        }

        if(StringUtils.isBlank(weightEditText.getText()) || NumberUtils.isDigits(weightEditText.getText().toString()) || Double.parseDouble(weightEditText.getText().toString()) == 0){
            showToast("invalid weight");
            return false;
        }

        if(walkTimeTextView.getText().toString().equalsIgnoreCase("set time")){
            showToast("invalid walktime");
            return false;
        }

        if(mealTimeTextView.getText().toString().equalsIgnoreCase("set time")){
            showToast("invalid mealtime");
            return false;
        }

        return true;
    }

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
            }
        }
    }
    private void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}
