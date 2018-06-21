package com.example.marcu.pawcompanion.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.marcu.pawcompanion.adapter.BreedSpinnerAdapter;
import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.data.Breed;
import com.example.marcu.pawcompanion.repository.BreedRepo;
import com.example.marcu.pawcompanion.data.Dog;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.String.format;

public class DogInfoInputActivity extends AppCompatActivity{
    private static final String TAG = "DogInfoInputActivity";

    private EditText nameEditText, weightEditText;
    private Spinner breedSpinner;
    private Button saveDogButton;

    private TextView birthdayTextView, walkTimeTextView, mealTimeTextView;
    private DatePickerDialog.OnDateSetListener birthdayDateSetListener;
    private TimePickerDialog.OnTimeSetListener walkTimeSetListener, mealTimeSetListener;

    private ArrayList<Breed> breedList;
    private BreedRepo breedRepo;
    private BreedSpinnerAdapter spinnerAdapter;
    private Breed selectedBreed;
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

        spinnerAdapter = new BreedSpinnerAdapter(this, breedList);
        breedSpinner.setAdapter(spinnerAdapter);

        Intent intent = getIntent();
        selectedDog = (Dog) intent.getSerializableExtra("selectedDog");
        Log.d(TAG, "DOG TO BE UPDATED 2: " + selectedDog);

        setSpinnerListener();
        setBirthdayClickListener();
        setBirthdatDateListener();
        setWalkTimeClickListener();
        setWalkTimeListener();
        setMealTimeClickListener();
        setmealTimeListener();
        setAddNewCompanionButtonClickListener();

        if(selectedDog != null){
            setDogInfo();
        }
    }

    private void findViews(){
        nameEditText = (EditText) findViewById(R.id.callEditText);
        birthdayTextView = (TextView) findViewById(R.id.birthdayTextView);
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        walkTimeTextView = (TextView) findViewById(R.id.walkTimeTextView);
        mealTimeTextView = (TextView) findViewById(R.id.mealTimeTextView);
        breedSpinner = (Spinner) findViewById(R.id.breedSpinner);
        saveDogButton = (Button) findViewById(R.id.addCompanionButton);
    }

    private void setDogInfo(){
        nameEditText.setText(selectedDog.getName());
        breedSpinner.setSelection(selectedDog.getBreed().getId());
        weightEditText.setText(String.valueOf(selectedDog.getWeight()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");
        birthdayTextView.setText(selectedDog.getBirthDate().format(formatter));
        walkTimeTextView.setText(selectedDog.getFirstWalkTime().toString());
        mealTimeTextView.setText(selectedDog.getFirstMealTime().toString());
    }

    private void setSpinnerListener(){
        breedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedBreed = (Breed) adapterView.getItemAtPosition(position);
                Log.d(TAG, "onItemSelected: ITEM SELECTED: " + selectedBreed.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
}
