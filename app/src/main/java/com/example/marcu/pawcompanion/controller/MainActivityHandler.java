package com.example.marcu.pawcompanion.controller;

import android.util.Log;

import com.example.marcu.pawcompanion.activity.MainActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Dog;

import static android.content.ContentValues.TAG;

public class MainActivityHandler extends Handler implements ActionHandlerContract.ActionHandler {

    public ActionHandlerContract.ActionHandler nextHandler;

    public MainActivityHandler(MainActivity activity) {
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
        Dog dog = getDogListComponent().getSelectedDog();

        switch (action){
            case ADD_DOG:
                getDogRepo().addDog(dog);
            break;
            case REMOVE_DOG:
                getDogRepo().removeDog(dog);
            break;
            case TOGGLE_DOG_SELECT:
                dog.setSelected(!dog.isSelected());
            break;
            case UPDATE_DOG:
                Dog dogToUpdate = getDogListComponent().getSelectedDog();
                Log.d(TAG, "updateModel: " + dogToUpdate.toString());
//                int position = getDogListComponent().getSelectedPosition();
//                position = getDogRepo().getIdByPosition(position);
                getDogRepo().updateDog(dogToUpdate);
            break;
        }
        getMainRootActionHandler().invokeAction(HandlerType.VIEW, Action.REFRESH_MAIN_VIEW);
    }
}
