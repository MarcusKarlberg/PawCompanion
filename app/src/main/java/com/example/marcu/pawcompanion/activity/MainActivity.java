package com.example.marcu.pawcompanion.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.marcu.pawcompanion.adapter.DogListAdapter;
import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.component.ButtonComponent;
import com.example.marcu.pawcompanion.component.DogListComponent;
import com.example.marcu.pawcompanion.controller.ActionHandlerContract;
import com.example.marcu.pawcompanion.controller.MainActivityHandler;
import com.example.marcu.pawcompanion.controller.ViewHandler;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Dog;

public class MainActivity extends AppCompatActivity implements ActionHandlerContract.RootActionHandler{
    private static final String TAG = "MainActivity";
    public static final int ADD_DOG_REQUEST = 0;
    public static final int UPDATE_DOG_REQUEST = 1;

    private ActionHandlerContract.ActionHandler actionHandler;

    private DogListAdapter adapter;
    private DogListComponent listView;
    private ButtonComponent addDogButton;
    private ButtonComponent deleteDogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setHandlers();
    }

    @Override
    public void invokeAction(HandlerType handlerType, Action action) {
        actionHandler.handle(handlerType, action);
    }

    public void setActionHandler(ActionHandlerContract.ActionHandler handler) {
        this.actionHandler = handler;
    }

    //Todo: originally = getRootActionHandler method - remove this later NOT NEEDED??
    public ActionHandlerContract.ActionHandler getActionHandler() {
        return this.actionHandler;
    }

    public DogListComponent getListComponent() {
        return this.listView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode == RESULT_OK){
            Dog dog = (Dog) intent.getSerializableExtra("dog");
            int position = intent.getIntExtra("dogPosition", -1);
            Log.d(TAG, "onActivityResult: " + dog);
            getListComponent().setSelectedDog(dog);
            getListComponent().setSelectedPosition(position);

            switch (requestCode){
                case ADD_DOG_REQUEST:
                    invokeAction(HandlerType.VIEW, Action.ADD_DOG);
                break;
                case UPDATE_DOG_REQUEST:
                    invokeAction(HandlerType.VIEW, Action.UPDATE_DOG);
                break;
            }
        } else {
            Log.d(TAG, "onActivityResult: FAIL RESULT NOT OK");
        }
    }

    private void findViews(){
        deleteDogButton = findViewById(R.id.deleteDogButton);
        deleteDogButton.setButtonType(ButtonComponent.ButtonType.REMOVE);
        addDogButton = findViewById(R.id.addDogButton);
        addDogButton.setButtonType(ButtonComponent.ButtonType.ADD);
        listView = findViewById(R.id.dogListView);
    }

    private void setHandlers(){
        ActionHandlerContract.ActionHandler modelHandler = new MainActivityHandler(this);
        ActionHandlerContract.ActionHandler viewHandler = new ViewHandler(this);

        viewHandler.setNextHandler(modelHandler);
        setActionHandler(viewHandler);
    }

    //Todo: add this to a PrefrencesHandler class
//    private void loadSavedDogsFromPrefs(){
//        prefs = new SPreferences(this);
//        dogList = prefs.load();
//        for (Dog d: dogList){
//            dogRepository.addDog(d);
//        }
//    }

//          //Todo: NotificationHandler/AlarmHandler
//            dog.setWalkingDurationPerDay(dog.getBreed().getActivityLevel());
//            dog.setWalkingDistancePerDay(dog.getBreed().getActivityLevel());
//            dog.setIntervalWalkTime(dog.getBreed().getActivityLevel());
//            dog.setIntervalMealTime();
//
//            notificationMngr.setWalkNotification(dog);
//            notificationMngr.setMealNotification(dog);
//            notificationMngr.setAlarmToResetDailyNotificationAlarms(dog);
//




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == ADD_DOG_REQUEST) {
//
//            if (resultCode == RESULT_OK) {
//                Dog dog = (Dog) data.getSerializableExtra("dog");
//
//                dogRepository.addDog(dog);
//                if(dogRepository.getAllDogs() != null){
//                    dogList = dogRepository.getAllDogs();
//                    prefs.save(dogList);
//                }
//
//                notificationMngr.setWalkNotification(dog);
//                notificationMngr.setMealNotification(dog);
//                notificationMngr.setAlarmToResetDailyNotificationAlarms(dog);
//
//            }
//        }
//
//        if (requestCode == UPDATE_DOG_REQUEST){
//
//            if(resultCode == RESULT_OK){
//                Dog dog = (Dog) data.getSerializableExtra("dog");
//                Log.i(TAG, "*** Dog info to update: ***" + dog);
//                updateDog(dog);
//                prefs.save(dogList);
//                adapter.notifyDataSetChanged();
//            }
//        }
//    }
}
