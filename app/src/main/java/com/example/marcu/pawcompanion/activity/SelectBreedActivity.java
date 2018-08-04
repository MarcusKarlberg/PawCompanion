package com.example.marcu.pawcompanion.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.adapter.BreedListAdapter;
import com.example.marcu.pawcompanion.component.BreedListComponent;
import com.example.marcu.pawcompanion.component.ButtonComponent;
import com.example.marcu.pawcompanion.controller.ActionHandlerContract;
import com.example.marcu.pawcompanion.controller.SelectBreedHandler;
import com.example.marcu.pawcompanion.controller.ValidateInputHandler;
import com.example.marcu.pawcompanion.controller.ViewHandler;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;

public class SelectBreedActivity extends AppCompatActivity implements ActionHandlerContract.RootActionHandler, SearchView.OnQueryTextListener{

    private ActionHandlerContract.ActionHandler actionHandler;
    private BreedListComponent listView;
    private ButtonComponent selectButton;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_breed);

        findViews();
        setHandlers();
    }

    public void setActionHandler(ActionHandlerContract.ActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }

    public BreedListComponent getBreedListComponent(){
        return listView;
    }

    @Override
    public void invokeAction(HandlerType handlerType, Action action) {
        actionHandler.handle(handlerType, action);
    }

    public void setSearchViewText(String text){
        searchView.setQuery(text, true);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        BreedListAdapter breedListAdapter = (BreedListAdapter) getBreedListComponent().getAdapter();
        breedListAdapter.getFilter().filter(s);

        return true;
    }

    private void setHandlers(){
        ActionHandlerContract.ActionHandler viewHandler = new ViewHandler(this);
        ActionHandlerContract.ActionHandler selectBreedHandler = new SelectBreedHandler(this);
        ActionHandlerContract.ActionHandler userInputHandler = new ValidateInputHandler(this);

        viewHandler.setNextHandler(selectBreedHandler);
        selectBreedHandler.setNextHandler(userInputHandler);
        setActionHandler(viewHandler);
    }

    private void findViews(){
        listView =  findViewById(R.id.select_breed_listView);
        searchView = findViewById(R.id.select_breed_searchView);
        searchView.setOnQueryTextListener(this);
        selectButton = findViewById(R.id.select_breed_button);
        selectButton.setButtonType(ButtonComponent.ButtonType.SELECT);
    }
}
