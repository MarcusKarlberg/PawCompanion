package com.example.marcu.pawcompanion.component;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.marcu.pawcompanion.controller.ActionHandlerContract;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;

public class TextViewComponent extends AppCompatTextView{
    public enum TextViewType{
        BREED_SELECT,
        SET_BIRTHDAY,
        SET_WALK_TIME,
        SET_MEAL_TIME
    }

    private ActionHandlerContract.RootActionHandler rootActionHandler;

    public TextViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.rootActionHandler = (ActionHandlerContract.RootActionHandler) context;
    }

     public void setTextViewType(TextViewType type){
            switch (type){
                case BREED_SELECT:
                    this.setOnClickListener(onClickOpenSelectBreedActivity());
                break;
                case SET_BIRTHDAY:
                    this.setOnClickListener(onClickOpenDatePicker());
                break;
                case SET_WALK_TIME:
                    this.setOnClickListener(onClickOpenWalkTimePicker());
                break;
                case SET_MEAL_TIME:
                    this.setOnClickListener(onClickOpenMealTimePicker());
                break;
            }
     }

     OnClickListener onClickOpenSelectBreedActivity(){
        return view -> {
            rootActionHandler.invokeAction(HandlerType.VIEW, Action.OPEN_SELECT_BREED_VIEW);
        };
     }

     OnClickListener onClickOpenDatePicker(){
        return view -> {
            rootActionHandler.invokeAction(HandlerType.MODEL, Action.SET_BIRTHDAY);
        };
     }

     OnClickListener onClickOpenWalkTimePicker(){
         return view -> {
             rootActionHandler.invokeAction(HandlerType.MODEL, Action.SET_WALK_TIME);
         };
     }

    OnClickListener onClickOpenMealTimePicker(){
        return view -> {
            rootActionHandler.invokeAction(HandlerType.MODEL, Action.SET_MEAL_TIME);
        };
    }
}
