package com.example.marcu.pawcompanion.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.marcu.pawcompanion.activity.DogInfoInputActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Breed;
import com.example.marcu.pawcompanion.data.Dog;

import org.joda.time.LocalDate;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class DogInfoInputHandler extends Handler implements ActionHandlerContract.ActionHandler {

    public ActionHandlerContract.ActionHandler nextHandler;

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

    //Todo: divide handlers
    private void updateModel(Action action){

        switch (action){

            case SET_BREED:
                Breed breed = getDogInfoInputActivity().getSelectedBreed();
                getDogInfoInputActivity().setBreedText(breed.getName());
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
                createDog();
                getDogInfoInputRootActionHandler().invokeAction(HandlerType.VIEW, Action.FINISH_DOG_INFO_INPUT_VIEW);
            break;

        }
    }

    private void setDate(){
        int year, month, day;

        LocalDate currentDate = LocalDate.now();
        year = currentDate.getYear();
        month = currentDate.getMonthOfYear();
        day = currentDate.getDayOfMonth();

        DatePickerDialog.OnDateSetListener birthdayDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = String.format("%02d", month) + "/" + String.format("%02d", day) + "/" + year;
                getDogInfoInputActivity().setBirthdayTextView(date);
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(getDogInfoInputActivity(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                birthdayDateSetListener,
                year, month, day);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    private void setWalkTime(){
        TimePickerDialog.OnTimeSetListener walkTimeSetListener = (new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String time = String.format("%02d", hour) + ":" + String.format("%02d", minute);
                getDogInfoInputActivity().setWalkTimeTextView(time);
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
                getDogInfoInputActivity().setMealTimeTextView(time);
            }
        });
        TimePickerDialog dialog = new TimePickerDialog(getDogInfoInputActivity(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                mealTimeSetListener, 7,0, true);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void createDog(){
            Log.d(TAG, "DogInfoInputActivity:SAVE BUTTON PRESSED");
            DogInfoInputActivity activity = getDogInfoInputActivity();

            String name = activity.getNameEditText();
            Double weight = Double.parseDouble(activity.getWeighText());
            Breed selectedBreed = activity.getSelectedBreed();
            String birthday = activity.getBirthDayText();
            String firstMealTime = activity.getMealTimeText();
            String firstWalkTime = activity.getWalkTimeText();

            Dog dog = new Dog(name, selectedBreed, birthday, weight, firstMealTime, firstWalkTime);
            Log.d(TAG, "DogInfoInputActivity: NEW DOG CREATED: " + dog.toString());
            getDogInfoInputActivity().setDog(dog);

//            }
    }
}
