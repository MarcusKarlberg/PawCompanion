package com.example.marcu.pawcompanion.component;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.example.marcu.pawcompanion.controller.ActionHandlerContract;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;

public class ButtonComponent extends AppCompatButton{

    public enum ButtonType {
        ADD, REMOVE, SAVE, SELECT,
        CLOSE_MEAL_NOTIFICATION, REMIND_AGAIN_MEAL_NOTIFICATION,
        CLOSE_WALK_NOTIFICATION, REMIND_AGAIN_WALK_NOTIFICATION
    }
    
    private ActionHandlerContract.RootActionHandler rootActionHandler;

    public ButtonComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.rootActionHandler = (ActionHandlerContract.RootActionHandler) context;
    }

    public void setButtonType(ButtonType buttonType){
        switch (buttonType){
            case ADD:
                this.setOnClickListener(onClickOpenDogInfoInputView());
            break;
            case REMOVE:
                this.setOnClickListener(onClickRemoveDog());
            break;
            case SELECT:
                this.setOnClickListener(onClickSelectBreed());
            break;
            case SAVE:
                this.setOnClickListener(onClickSaveDog());
            break;
            case CLOSE_MEAL_NOTIFICATION:
                this.setOnClickListener(onClickCloseMealNotification());
            break;
            case REMIND_AGAIN_MEAL_NOTIFICATION:
                this.setOnClickListener(onClickRemindAgainMealNotification());
            break;
            case CLOSE_WALK_NOTIFICATION:
                this.setOnClickListener(onClickCloseWalkNotification());
            break;
            case REMIND_AGAIN_WALK_NOTIFICATION:
                this.setOnClickListener(onClickRemindAgainWalkNotification());
            break;
        }
    }

    OnClickListener onClickOpenDogInfoInputView(){
        return view -> rootActionHandler.invokeAction(HandlerType.VIEW, Action.OPEN_DOG_INFO_ADD_VIEW);
    }

    OnClickListener onClickRemoveDog(){
        return view -> rootActionHandler.invokeAction(HandlerType.MODEL, Action.REMOVE_DOG);
    }

    OnClickListener onClickSelectBreed(){
        return view -> rootActionHandler.invokeAction(HandlerType.USER_INPUT, Action.VALIDATE_BREED);
    }

    OnClickListener onClickSaveDog(){
        return view -> rootActionHandler.invokeAction(HandlerType.USER_INPUT, Action.VALIDATE_DOG);
    }

    OnClickListener onClickCloseMealNotification(){
        return view -> rootActionHandler.invokeAction(HandlerType.VIEW, Action.CLOSE_MEAL_NOTIFICATION_VIEW);
    }

    OnClickListener onClickRemindAgainMealNotification(){
        return view -> rootActionHandler.invokeAction(HandlerType.NOTIFICATION, Action.SET_MEAL_REMINDER);
    }

    OnClickListener onClickCloseWalkNotification(){
        return view -> rootActionHandler.invokeAction(HandlerType.VIEW, Action.ClOSE_WALK_NOTIFICATION_VIEW);
    }

    OnClickListener onClickRemindAgainWalkNotification(){
        return view -> rootActionHandler.invokeAction(HandlerType.NOTIFICATION, Action.SET_WALK_REMINDER);
    }
}
