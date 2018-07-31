package com.example.marcu.pawcompanion.controller;

import android.content.Intent;
import android.util.Log;

import com.example.marcu.pawcompanion.activity.DogInfoInputActivity;
import com.example.marcu.pawcompanion.activity.MainActivity;
import com.example.marcu.pawcompanion.activity.SelectBreedActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Dog;

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
                //Todo: where do we set intent, do we need it?
                getSelectBreedActivity().finish();
            break;
        }
    }
}
