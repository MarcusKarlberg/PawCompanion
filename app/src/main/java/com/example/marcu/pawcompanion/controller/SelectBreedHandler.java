package com.example.marcu.pawcompanion.controller;

import android.util.Log;

import com.example.marcu.pawcompanion.activity.SelectBreedActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Breed;

import static android.content.ContentValues.TAG;

public class SelectBreedHandler extends Handler implements ActionHandlerContract.ActionHandler {

    public ActionHandlerContract.ActionHandler nextHandler;

    public SelectBreedHandler(SelectBreedActivity activity) {
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
        Breed breed = getBreedListComponent().getSelectedBreed();

            switch (action){
                case UPDATE_BREED:
                    int breedPosition = getBreedListComponent().getSelectedPosition();
                    breedPosition = getBreedRepo().getIndexOf(breedPosition);
                    getBreedRepo().update(breedPosition, breed);
                break;
                case TOGGLE_BREED_SELECT:
                    Log.d(TAG, "updateModel: you selected: " + breed.getName());
                    getSelectBreedActivity().setSearchViewText(breed.getName());
                break;
        }

        getSelectBreedRootActionHandler().invokeAction(HandlerType.VIEW, Action.REFRESH_SELECT_BREED_VIEW);
    }
}
