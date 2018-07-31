package com.example.marcu.pawcompanion.controller;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.marcu.pawcompanion.activity.DogInfoInputActivity;
import com.example.marcu.pawcompanion.activity.MainActivity;
import com.example.marcu.pawcompanion.activity.SelectBreedActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Breed;
import com.example.marcu.pawcompanion.data.Dog;

import org.apache.commons.lang3.StringUtils;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class ViewHandler extends Handler implements ActionHandlerContract.ActionHandler{

    ActionHandlerContract.ActionHandler nextHandler;

    public ViewHandler(MainActivity mainActivity) {
        super(mainActivity);
    }

    public ViewHandler(DogInfoInputActivity dogInfoInputActivity){
        super(dogInfoInputActivity);
    }

    public ViewHandler(SelectBreedActivity selectBreedActivity){
        super(selectBreedActivity);
    }

    @Override
    public void setNextHandler(ActionHandlerContract.ActionHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handle(HandlerType handlerType, Action action) {
        if(handlerType == HandlerType.VIEW){
            updateView(action);
        } else {
            nextHandler.handle(handlerType, action);
        }
    }

    private void updateView(Action action){
        switch (action){
            case REFRESH_MAIN_VIEW:
                getDogListAdapter().notifyDataSetChanged();
            break;

            case OPEN_DOG_INFO_UPDATE_VIEW:
                Intent intentUpdate = new Intent(getMainActivity(), DogInfoInputActivity.class);
                int position = getDogListComponent().getSelectedPosition();
                Dog dog = getDogRepo().getById(getDogRepo().getIdByPosition(position));
                intentUpdate.putExtra("selectedDog", dog);
                getMainActivity().startActivityForResult(intentUpdate, MainActivity.UPDATE_DOG_REQUEST);
            break;

            case OPEN_DOG_INFO_ADD_VIEW:
                Intent intentAdd = new Intent(getMainActivity(), DogInfoInputActivity.class);
                getMainActivity().startActivityForResult(intentAdd, MainActivity.ADD_DOG_REQUEST);
            break;

            case OPEN_SELECT_BREED_VIEW:
                Log.d(TAG, "updateView: set breed tex pressed");
                Intent intentBreedSelect = new Intent(getDogInfoInputActivity(), SelectBreedActivity.class);
                getDogInfoInputActivity().startActivityForResult(intentBreedSelect, DogInfoInputActivity.SELECT_BREED_REQUEST);
            break;

            case REFRESH_SELECT_BREED_VIEW:
                getBreedListAdapter().notifyDataSetChanged();
            break;

            case FINISH_SELECT_BREED_VIEW:
               if(validateSelectedBreed(getBreedListComponent().getSelectedBreed())){
                   Intent intent = new Intent();
                   intent.putExtra("breed", getBreedListComponent().getSelectedBreed());
                   getSelectBreedActivity().setResult(RESULT_OK, intent);
                   getSelectBreedActivity().finish();
               }
            break;

            case FINISH_DOG_INFO_INPUT_VIEW:
                if(validateDogInput()){
//                    Intent intent = new Intent();
//                    intent.putExtra("dog", dog);
//                    getSelectBreedActivity().setResult(RESULT_OK, intent);
                    getDogInfoInputActivity().finish();
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
