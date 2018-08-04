package com.example.marcu.pawcompanion.controller;

import android.content.Intent;

import com.example.marcu.pawcompanion.activity.DogInfoInputActivity;
import com.example.marcu.pawcompanion.activity.MainActivity;
import com.example.marcu.pawcompanion.activity.MealNotificationActivity;
import com.example.marcu.pawcompanion.activity.SelectBreedActivity;
import com.example.marcu.pawcompanion.activity.WalkNotificationActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Dog;

import static android.app.Activity.RESULT_OK;

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

    public ViewHandler(MealNotificationActivity mealNotificationActivity){
        super(mealNotificationActivity);
    }

    public ViewHandler(WalkNotificationActivity walkNotificationActivity){
        super(walkNotificationActivity);
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
                Dog dog = getDogListComponent().getSelectedDog();
                intentUpdate.putExtra("selectedDog", dog);
                getMainActivity().startActivityForResult(intentUpdate, MainActivity.UPDATE_DOG_REQUEST);
            break;
            case OPEN_DOG_INFO_ADD_VIEW:
                Intent intentAdd = new Intent(getMainActivity(), DogInfoInputActivity.class);
                getMainActivity().startActivityForResult(intentAdd, MainActivity.ADD_DOG_REQUEST);
            break;
            case OPEN_SELECT_BREED_VIEW:
                Intent intentBreedSelect = new Intent(getDogInfoInputActivity(), SelectBreedActivity.class);
                getDogInfoInputActivity().startActivityForResult(intentBreedSelect, DogInfoInputActivity.SELECT_BREED_REQUEST);
            break;
            case REFRESH_SELECT_BREED_VIEW:
                getBreedListAdapter().notifyDataSetChanged();
            break;
            case CLOSE_SELECT_BREED_VIEW:
                   Intent intent = new Intent();
                   intent.putExtra("breed", getBreedListComponent().getSelectedBreed());
                   getSelectBreedActivity().setResult(RESULT_OK, intent);
                   getSelectBreedActivity().finish();
            break;
            case CLOSE_DOG_INFO_INPUT_VIEW:
                Intent dogIntent = new Intent();
                dogIntent.putExtra("dog", getDogInfoInputActivity().getDog());
                getDogInfoInputActivity().setResult(RESULT_OK, dogIntent);
                getDogInfoInputActivity().finish();
            break;
            case CLOSE_MEAL_NOTIFICATION_VIEW:
                getMealNotificationActivity().finish();
            break;
            case ClOSE_WALK_NOTIFICATION_VIEW:
                getWalkNotificationActivity().finish();
            break;
        }
    }
}
