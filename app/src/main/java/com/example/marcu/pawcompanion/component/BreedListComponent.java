package com.example.marcu.pawcompanion.component;

import android.content.Context;
import android.util.AttributeSet;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.marcu.pawcompanion.adapter.BreedListAdapter;
import com.example.marcu.pawcompanion.controller.ActionHandlerContract;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Breed;

import static android.content.ContentValues.TAG;

public class BreedListComponent extends ListView
        implements AdapterView.OnItemClickListener,
        MenuItem.OnMenuItemClickListener  {

    private int selectedPosition;
    private Breed selectedBreed;

    private ActionHandlerContract.RootActionHandler rootActionHandler;

    public BreedListComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.rootActionHandler = (ActionHandlerContract.RootActionHandler) context;

        initialize();
    }

    private void initialize() {
        this.setAdapter(new BreedListAdapter());
        this.setOnItemClickListener(this);
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public Breed getSelectedBreed() {
        return selectedBreed;
    }

    public void setSelectedBreed(Breed selectedBreed) {
        this.selectedBreed = selectedBreed;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) menuItem.getMenuInfo();
        this.setSelectedPosition(menuInfo.position);
        this.setSelectedBreed((Breed) getItemAtPosition(menuInfo.position));

        String menuActionSelection = menuItem.getTitle().toString();

        if(menuActionSelection.equals("Select")) {
            rootActionHandler.invokeAction(HandlerType.VIEW, Action.OPEN_DOG_INFO_UPDATE_VIEW);
        }

        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        setSelectedPosition(position);
        Breed selectedBreed = (Breed) getItemAtPosition(position);
        setSelectedBreed(selectedBreed);

        Log.d(TAG, "onItemClick: " + selectedBreed.getName() + " selected");
        this.rootActionHandler.invokeAction(HandlerType.MODEL, Action.TOGGLE_BREED_SELECT);
    }
}
