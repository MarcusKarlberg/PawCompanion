package com.example.marcu.pawcompanion.controller;

import com.example.marcu.pawcompanion.activity.DogInfoInputActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Breed;

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
        Breed breed = getBreedListComponent().getSelectedBreed();

        switch (action){
            case SET_NAME:
                //Todo: activate EditText and setText
                break;
            case SET_BIRTHDAY:
                //Todo: activate date-picker and setText
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
            case SET_BREED:
                //Todo: setTExt to chosen breed
                break;
        }
        getMainRootActionHandler().invokeAction(HandlerType.VIEW, Action.REFRESH_DOG_INPUT_VIEW);
    }
}
