package com.example.marcu.pawcompanion.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.marcu.pawcompanion.adapter.DogListAdapter;
import com.example.marcu.pawcompanion.controller.ActionHandlerContract;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Dog;

public class DogListComponent extends ListView
        implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener{

    private Dog selectedDog;
    private int selectedPosition;
    private ActionHandlerContract.RootActionHandler rootActionHandler;

    public DogListComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.rootActionHandler = (ActionHandlerContract.RootActionHandler) context;

        initialize();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        setSelectedPosition(i);
        Dog selectedDog = (Dog) getItemAtPosition(i);
        setSelectedDog(selectedDog);

        this.rootActionHandler.invokeAction(HandlerType.MODEL, Action.TOGGLE_DOG_SELECT);
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public Dog getSelectedDog() {
        return selectedDog;
    }

    public void setSelectedDog(Dog selectedDog) {
        this.selectedDog = selectedDog;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        setSelectedPosition(i);
        Dog selectedDog = (Dog) getItemAtPosition(i);
        setSelectedDog(selectedDog);

        this.rootActionHandler.invokeAction(HandlerType.VIEW, Action.OPEN_DOG_INFO_UPDATE_VIEW);
        return false;
    }

    private void initialize() {
        this.setAdapter(new DogListAdapter());
        this.setOnItemClickListener(this);
        this.setOnItemLongClickListener(this);
    }
}
