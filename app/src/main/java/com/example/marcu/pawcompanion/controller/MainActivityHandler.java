package com.example.marcu.pawcompanion.controller;

import com.example.marcu.pawcompanion.activity.MainActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Dog;

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
                getMainRootActionHandler().invokeAction(HandlerType.NOTIFICATION, Action.REMOVE_NOTIFICATION);
            break;
            case TOGGLE_DOG_SELECT:
                dog.setSelected(!dog.isSelected());
                getMainRootActionHandler().invokeAction(HandlerType.VIEW, Action.REFRESH_MAIN_VIEW);
            break;
            case UPDATE_DOG:
                Dog dogToUpdate = getDogListComponent().getSelectedDog();
                getDogRepo().updateDog(dogToUpdate);
            break;
        }

        getMainRootActionHandler().invokeAction(HandlerType.PREFERENCES, Action.SAVE_PREFERENCES);
    }
}
