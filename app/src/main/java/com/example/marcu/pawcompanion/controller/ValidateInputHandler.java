package com.example.marcu.pawcompanion.controller;

import android.view.Gravity;
import android.widget.Toast;

import com.example.marcu.pawcompanion.activity.DogInfoInputActivity;
import com.example.marcu.pawcompanion.activity.SelectBreedActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Breed;

import org.apache.commons.lang3.StringUtils;

public class ValidateInputHandler extends Handler implements ActionHandlerContract.ActionHandler{

    public ActionHandlerContract.ActionHandler nextHandler;

    public ValidateInputHandler(SelectBreedActivity activity) {
        super(activity);
    }

    public ValidateInputHandler(DogInfoInputActivity activity) {
        super(activity);
    }

    @Override
    public void setNextHandler(ActionHandlerContract.ActionHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handle(HandlerType handlerType, Action action) {
        if(handlerType == HandlerType.USER_INPUT){
            validate(action);
        } else {
            nextHandler.handle(handlerType, action);
        }
    }

    private void validate(Action action){
        switch (action){
            case VALIDATE_BREED:
                Breed breed = getBreedListComponent().getSelectedBreed();
                if(validateSelectedBreed(breed)){
                    getSelectBreedRootActionHandler().invokeAction(HandlerType.VIEW, Action.FINISH_SELECT_BREED_VIEW);
                }
            break;

            case VALIDATE_DOG:
                if (validateDogInput()){
                    getDogInfoInputRootActionHandler().invokeAction(HandlerType.MODEL, Action.CREATE_DOG);
                }
            break;
        }
    }

    private boolean validateSelectedBreed(Breed breed){
        if(breed != null){
            return true;
        } else {
            showToast("Invalid Breed - Choose a breed");
            return false;
        }
    }

    private boolean validateDogInput(){
        if(StringUtils.isBlank(getDogInfoInputActivity().getNameEditText())){
            showToast("invalid name");
            return false;
        }

        if(getDogInfoInputActivity().getSelectedBreed() == null){
            return false;
        }

        if(getDogInfoInputActivity().getBirthDayText().equalsIgnoreCase("set date")){
            showToast("invalid birthday");
            return false;
        }

        String weight = getDogInfoInputActivity().getWeighText();
        if(StringUtils.isBlank(weight) || !weight.matches("^[1-9]\\d*(\\.\\d+)?$") || Double.parseDouble(weight) == 0){
            showToast("invalid weight");
            return false;
        }

        if(getDogInfoInputActivity().getWalkTimeText().equalsIgnoreCase("set time")){
            showToast("invalid walktime");
            return false;
        }

        if(getDogInfoInputActivity().getMealTimeText().equalsIgnoreCase("set time")){
            showToast("invalid mealtime");
            return false;
        }

        return true;
    }

    private void showToast(String message){
        Toast toast = Toast.makeText(getDogInfoInputActivity().getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}
