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

    private void setHandlers(){
        ActionHandlerContract.ActionHandler viewHandler = new ViewHandler(this);
        ActionHandlerContract.ActionHandler selectBreedHandler = new SelectBreedHandler(this);
        ActionHandlerContract.ActionHandler userInputHandler = new ValidateInputHandler(this);

        viewHandler.setNextHandler(selectBreedHandler);
        selectBreedHandler.setNextHandler(userInputHandler);
        setActionHandler(viewHandler);
    }

    private void findViews(){
        listView =  findViewById(R.id.listView);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        selectButton = findViewById(R.id.selectButton);
        selectButton.setButtonType(ButtonComponent.ButtonType.SELECT);
    }

    public ActionHandlerContract.ActionHandler getActionHandler() {
        return actionHandler;
    }

    public void setActionHandler(ActionHandlerContract.ActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }

    public BreedListComponent getBreedListComponent(){
        return listView;
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

    @Override
    public void invokeAction(HandlerType handlerType, Action action) {
        actionHandler.handle(handlerType, action);
    }

    public void setSearchViewText(String text){
        searchView.setQuery(text, true);
    }

//    private void loadBreedList(){
//        breedRepo = new BreedRepo();
//        breeds = breedRepo.getAllBreedNames();
//    }

//    private void setAdapter(){
//        adapter = new ArrayAdapter<String>(SelectBreedActivity.this, android.R.layout.simple_list_item_1, breeds);
//        listView.setAdapter(adapter);
//    }
//    private void setOnQueryTextListener(){
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                listView.setVisibility(View.VISIBLE);
//                adapter.getFilter().filter(newText);
//                return true;
//            }
//        });
//    }


//    private void setOnClickListener(){
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedBreed = adapterView.getItemAtPosition(i).toString();
//                searchView.setQuery(selectedBreed, true);
//                listView.setVisibility(View.GONE);
//            }
//        });
//    }

//    private void setSelectBreedButtonClickListener(){
//        selectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!isBlank(selectedBreed)){
//                    Intent intent = new Intent();
//                    intent.putExtra("selectedBreed", selectedBreed);
//                    setResult(RESULT_OK, intent);
//                    finish();
//                } else {
//                    showToast("Invalid Breed - Choose a breed");
//                }
//            }
//        });
//    }

//    private void showToast(String message){
//        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL, 0, 0);
//        toast.show();
//    }
}
