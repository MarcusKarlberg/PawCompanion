package com.example.marcu.pawcompanion.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.component.ButtonComponent;
import com.example.marcu.pawcompanion.component.DogListComponent;
import com.example.marcu.pawcompanion.controller.ActionHandlerContract;
import com.example.marcu.pawcompanion.controller.MainActivityHandler;
import com.example.marcu.pawcompanion.controller.NotificationHandler;
import com.example.marcu.pawcompanion.controller.PreferencesHandler;
import com.example.marcu.pawcompanion.controller.ViewHandler;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.dialog.DisclaimerDialog;
import com.example.marcu.pawcompanion.exception.ExceptionHandler;

public class MainActivity extends AppCompatActivity implements ActionHandlerContract.RootActionHandler{
    public static final int ADD_DOG_REQUEST = 0;
    public static final int UPDATE_DOG_REQUEST = 1;

    private ActionHandlerContract.ActionHandler actionHandler;
    private DogListComponent listView;
    private ButtonComponent addDogButton;
    private ButtonComponent deleteDogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_main);
        DisclaimerDialog dialog = new DisclaimerDialog();
        dialog.show(this);
        findViews();
        setHandlers();

        invokeAction(HandlerType.PREFERENCES, Action.LOAD_DOGS_PREFERENCES);
    }

    @Override
    public void invokeAction(HandlerType handlerType, Action action) {
        actionHandler.handle(handlerType, action);
    }

    public void setActionHandler(ActionHandlerContract.ActionHandler handler) {
        this.actionHandler = handler;
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
            getListComponent().setSelectedDog(dog);
            getListComponent().setSelectedPosition(position);

            switch (requestCode){
                case ADD_DOG_REQUEST:
                    invokeAction(HandlerType.MODEL, Action.ADD_DOG);
                    invokeAction(HandlerType.NOTIFICATION, Action.ADD_NOTIFICATION);
                break;
                case UPDATE_DOG_REQUEST:
                    invokeAction(HandlerType.MODEL, Action.UPDATE_DOG);
                    invokeAction(HandlerType.NOTIFICATION, Action.ADD_NOTIFICATION);
                break;
            }
        }
    }

    private void findViews(){
        deleteDogButton = findViewById(R.id.main_deleteDogButton);
        deleteDogButton.setButtonType(ButtonComponent.ButtonType.REMOVE);
        addDogButton = findViewById(R.id.main_addDogButton);
        addDogButton.setButtonType(ButtonComponent.ButtonType.ADD);
        listView = findViewById(R.id.main_dogListView);
    }

    private void setHandlers(){
        ActionHandlerContract.ActionHandler mainActivityHandler = new MainActivityHandler(this);
        ActionHandlerContract.ActionHandler viewHandler = new ViewHandler(this);
        ActionHandlerContract.ActionHandler preferencesHandler = new PreferencesHandler(this);
        ActionHandlerContract.ActionHandler notificationHandler = new NotificationHandler(this);

        viewHandler.setNextHandler(mainActivityHandler);
        mainActivityHandler.setNextHandler(preferencesHandler);
        preferencesHandler.setNextHandler(notificationHandler);
        setActionHandler(viewHandler);
    }
}
