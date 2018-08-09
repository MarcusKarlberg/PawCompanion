package com.example.marcu.pawcompanion.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.activity.DogInfoInputActivity;
import com.example.marcu.pawcompanion.component.EditTextComponent;
import com.example.marcu.pawcompanion.component.TextViewComponent;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Breed;
import com.example.marcu.pawcompanion.data.Dog;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static android.content.ContentValues.TAG;
import static com.example.marcu.pawcompanion.activity.DogInfoInputActivity.CREATE;
import static com.example.marcu.pawcompanion.activity.DogInfoInputActivity.UPDATE;

public class DogInfoInputHandler extends Handler implements ActionHandlerContract.ActionHandler {

    public ActionHandlerContract.ActionHandler nextHandler;
    private EditTextComponent nameEditTex;
    private TextViewComponent breedTextView;
    private TextViewComponent birthdayTextView;
    private EditTextComponent weightEditText;
    private TextViewComponent walkTimeTextView;
    private TextViewComponent mealTimeTextView;

    public DogInfoInputHandler(DogInfoInputActivity activity) {
        super(activity);
    }

    @Override
    public void setNextHandler(ActionHandlerContract.ActionHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handle(HandlerType handlerType, Action action) {
        if(handlerType == HandlerType.MODEL){
            updateModel(action);
        } else {
            nextHandler.handle(handlerType, action);
        }
    }

    private void updateModel(Action action){
        findViews();

        switch (action){
            case SET_BREED:
                Breed breed = getDogInfoInputActivity().getSelectedBreed();
                TextViewComponent breedTextView = getDogInfoInputActivity().findViewById(R.id.info_input_breedTextView);
                breedTextView.setText(breed.getName());
            break;
            case SET_BIRTHDAY:
                setDate();
            break;
            case SET_WALK_TIME:
                setWalkTime();
            break;
            case SET_MEAL_TIME:
                setMealTime();
            break;
            case CREATE_DOG:
                saveDog();
                getDogInfoInputRootActionHandler().invokeAction(HandlerType.VIEW, Action.CLOSE_DOG_INFO_INPUT_VIEW);
            break;
            case SET_DOG_INFO:
                Dog dog = getDogInfoInputActivity().getDog();
                setDogInfo(dog);
                Log.d(TAG, "DOG TO BE UPDATED 2: " + dog);
            break;
        }
    }

    private void setDate(){
        int year, month, day;

        LocalDate currentDate = LocalDate.now();
        year = currentDate.getYear() - 1;
        month = currentDate.getMonthOfYear();
        day = currentDate.getDayOfMonth();

        DatePickerDialog.OnDateSetListener birthdayDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = String.format("%02d", month) + "/" + String.format("%02d", day) + "/" + year;
                birthdayTextView.setText(date);
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(getDogInfoInputActivity(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                birthdayDateSetListener,
                year, month, day);

        dialog.getDatePicker().setMaxDate(DateTime.now().getMillis());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    private void setWalkTime(){
        TimePickerDialog.OnTimeSetListener walkTimeSetListener = (new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String time = String.format("%02d", hour) + ":" + String.format("%02d", minute);
                walkTimeTextView.setText(time);
            }
        });

        TimePickerDialog dialog = new TimePickerDialog(getDogInfoInputActivity(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                walkTimeSetListener, 7,0, true);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void setMealTime(){
        TimePickerDialog.OnTimeSetListener mealTimeSetListener = (new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String time = String.format("%02d", hour) + ":" + String.format("%02d", minute);
                mealTimeTextView.setText(time);
            }
        });

        TimePickerDialog dialog = new TimePickerDialog(getDogInfoInputActivity(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                mealTimeSetListener, 7,0, true);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void saveDog(){
        String name = nameEditTex.getText().toString();
        Double weight = Double.parseDouble(weightEditText.getText().toString());
        Breed selectedBreed = getDogInfoInputActivity().getSelectedBreed();
        String birthday = birthdayTextView.getText().toString();
        String firstMealTime = mealTimeTextView.getText().toString();
        String firstWalkTime = walkTimeTextView.getText().toString();
        Uri imageUri = getImageViewComponent().getSelectedImage();
        Dog dog = null;

        switch (getDogInfoInputActivity().purposeOfActivity){
            case CREATE:
                dog = new Dog(name, selectedBreed, birthday, weight, firstMealTime, firstWalkTime);
                if(imageUri != null){
                    dog.setImageUriString(imageUri.toString());
                }
            break;
            case UPDATE:
                dog = getDogInfoInputActivity().getDog();
                dog.setName(name);
                dog.setWeight(weight);
                dog.setBreed(selectedBreed);
                dog.setBirthDate(birthday);
                dog.setFirstWalkTime(firstWalkTime);
                dog.setFirstMealTime(firstMealTime);
                if(imageUri != null){
                    dog.setImageUriString(imageUri.toString());
                }
            break;
        }
        getDogInfoInputActivity().setDog(dog);
    }

    private void setDogInfo(Dog dog){
        nameEditTex.setText(dog.getName());
        breedTextView.setText(dog.getBreed().getName());
        getDogInfoInputActivity().setSelectedBreed(dog.getBreed());
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
        birthdayTextView.setText(dog.getBirthDate().toString(formatter));
        weightEditText.setText(String.valueOf(dog.getWeight()));
        walkTimeTextView.setText(dog.getFirstWalkTime().toString("hh:mm"));
        mealTimeTextView.setText(dog.getFirstMealTime().toString("hh:mm"));
        getDogInfoInputActivity().invokeAction(HandlerType.IMAGE, Action.SET_IMAGE);
    }

    private void findViews(){
        DogInfoInputActivity activity = getDogInfoInputActivity();
        this.nameEditTex = activity.findViewById(R.id.info_input_nameTextView);
        this.birthdayTextView = activity.findViewById(R.id.info_input_birthdayTextView);
        this.weightEditText = activity.findViewById(R.id.info_input_weightEditText);
        this.walkTimeTextView = activity.findViewById(R.id.info_input_walkTimeTextView);
        this.mealTimeTextView = activity.findViewById(R.id.info_input_mealTimeTextView);
        this.breedTextView = activity.findViewById(R.id.info_input_breedTextView);
    }
}
