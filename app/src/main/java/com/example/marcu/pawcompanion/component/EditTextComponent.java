package com.example.marcu.pawcompanion.component;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.example.marcu.pawcompanion.controller.ActionHandlerContract;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;

public class EditTextComponent extends AppCompatEditText{

    public enum EditTextType {
        SET_NAME, SET_WEIGHT
    }

    private ActionHandlerContract.RootActionHandler rootActionHandler;

    public EditTextComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        rootActionHandler = (ActionHandlerContract.RootActionHandler) context;
    }

    public void setEditTextType(EditTextType type){
        switch (type){
            case SET_NAME:
                this.setOnClickListener(onClickOpenEditTextName());
            break;
            case SET_WEIGHT:
                this.setOnClickListener(onClickOpenEditTextWeight());
            break;
        }
    }

    OnClickListener onClickOpenEditTextName() {
        return view -> {
            rootActionHandler.invokeAction(HandlerType.MODEL, Action.SET_NAME);
        };
    }

    OnClickListener onClickOpenEditTextWeight() {
        return view -> {
            rootActionHandler.invokeAction(HandlerType.MODEL, Action.SET_WEIGHT);
        };
    }
}
