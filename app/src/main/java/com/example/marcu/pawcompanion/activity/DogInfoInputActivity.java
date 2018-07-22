package com.example.marcu.pawcompanion.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.marcu.pawcompanion.adapter.BreedSpinnerAdapter;
import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.data.Breed;
import com.example.marcu.pawcompanion.repository.BreedRepo;
import com.example.marcu.pawcompanion.data.Dog;

import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        weightEditText.setText(String.valueOf(selectedDog.getWeight()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");
        birthdayTextView.setText(selectedDog.getBirthDate().format(formatter));
        walkTimeTextView.setText(selectedDog.getFirstWalkTime().toString());
        mealTimeTextView.setText(selectedDog.getFirstMealTime().toString());
        setImageView();
    }

    private void setImageViewClickListener(){
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, ACCESS_PHOTO_LIB);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACCESS_PHOTO_LIB){
            if(resultCode == RESULT_OK){
                imageUri = data.getData();
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
                    month = currentDate.getMonthValue();
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
            return false;
        }

        if(birthdayTextView.getText().toString().equalsIgnoreCase("select date")){
            return false;
        }

        if(StringUtils.isBlank(weightEditText.getText())){
            return false;
        }

        if(walkTimeTextView.getText().toString().equalsIgnoreCase("select time")){
            return false;
        }

        if(mealTimeTextView.getText().toString().equalsIgnoreCase("select time")){
            return false;
        }

        return true;
    }

    private void setImageView(){
        String imageUriString = selectedDog.getImageUriString();

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
