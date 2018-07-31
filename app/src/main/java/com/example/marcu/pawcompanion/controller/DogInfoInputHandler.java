package com.example.marcu.pawcompanion.controller;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.DatePicker;

import com.example.marcu.pawcompanion.activity.DogInfoInputActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Breed;

import org.joda.time.LocalDate;

import static android.content.ContentValues.TAG;

public class DogInfoInputHandler extends Handler implements ActionHandlerContract.ActionHandler {

    public ActionHandlerContract.ActionHandler nextHandler;
    private DatePickerDialog.OnDateSetListener birthdayDateSetListener;

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
            case SET_NAME:
                //Todo: activate EditText and setText
            break;

            case SET_BREED:
                Breed breed = getDogInfoInputActivity().getSelectedBreed();
                getDogInfoInputActivity().setBreedText(breed.getName());
            break;

            case SET_BIRTHDAY:
                setDate();
            break;

            case SET_WEIGHT:
                //Todo: activate edit\text and setText
            break;

            case SET_WALK_TIME:
                //Todo: activate time-picker and setText
            break;

            case SET_MEAL_TIME:
                //Todo: activate time-picker and setText
            break;
        }

        getDogInfoInputRootActionHandler().invokeAction(HandlerType.VIEW, Action.REFRESH_DOG_INPUT_VIEW);
    }

    private void setDate(){
        int year, month, day;
        LocalDate currentDate = LocalDate.now();
        year = currentDate.getYear();
        month = currentDate.getMonthOfYear();
        day = currentDate.getDayOfMonth();
        setBirthdatDateListener();

        DatePickerDialog dialog = new DatePickerDialog(getDogInfoInputActivity(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                birthdayDateSetListener,
                year, month, day);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void setBirthdatDateListener(){
        birthdayDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = String.format("%02d", month) + "/" + String.format("%02d", day) + "/" + year;
                getDogInfoInputActivity().setBirthdayTextView(date);
            }
        };
    }
}
