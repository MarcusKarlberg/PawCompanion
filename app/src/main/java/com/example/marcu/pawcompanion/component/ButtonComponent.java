package com.example.marcu.pawcompanion.component;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.example.marcu.pawcompanion.controller.ActionHandlerContract;
import com.example.marcu.pawcompanion.controller.Handler;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;

public class ButtonComponent extends AppCompatButton{

    public enum ButtonType {
        ADD, REMOVE, SAVE, SELECT
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
        }
    }

    OnClickListener onClickOpenDogInfoInputView(){
        return view -> {
            rootActionHandler.invokeAction(HandlerType.VIEW, Action.OPEN_DOG_INFO_ADD_VIEW);
        };
    }

    OnClickListener onClickRemoveDog(){
        return view -> {
          rootActionHandler.invokeAction(HandlerType.MODEL, Action.REMOVE_DOG);
        };
    }

    OnClickListener onClickSelectBreed(){
        return view -> {
            rootActionHandler.invokeAction(HandlerType.VIEW, Action.FINISH_SELECT_BREED_VIEW);
        };
    }

    OnClickListener onClickSaveDog(){
        return view -> {
            rootActionHandler.invokeAction(HandlerType.VIEW, Action.FINISH_DOG_INFO_INPUT_VIEW);
        };
    }

}
