package com.example.marcu.pawcompanion.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.component.ButtonComponent;
import com.example.marcu.pawcompanion.component.ImageViewComponent;
import com.example.marcu.pawcompanion.controller.ActionHandlerContract;
import com.example.marcu.pawcompanion.controller.NotificationHandler;
import com.example.marcu.pawcompanion.controller.ViewHandler;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Dog;

public class MealNotificationActivity extends AppCompatActivity implements ActionHandlerContract.RootActionHandler {

    private static final String TAG = "MealNotificationActivity";
    private ActionHandlerContract.ActionHandler actionHandler;
    private TextView nameTextView;
    private ImageViewComponent imageViewComponent;
    private TextView portionTextView;
    private ButtonComponent okButton;
    private ButtonComponent remindAgainButton;
    Dog dog;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_notification);

        findViews();
        Bundle bundle = (getIntent().getExtras().getBundle("bundle"));
        validateData(bundle);
        setHandlers();
        getImageViewComponent().setSelectedImage(this.dog.getImageUriString());
        invokeAction(HandlerType.NOTIFICATION, Action.SET_MEAL_NOTIFICATION_INFO);
    }

    @Override
    public void invokeAction(HandlerType handlerType, Action action) {
        actionHandler.handle(handlerType, action);
    }

    public void setActionHandler(ActionHandlerContract.ActionHandler handler) {
        this.actionHandler = handler;
    }

    public void setNameTextView(String text){
        nameTextView.setText(text);
    }

    public void setImage(Bitmap bitmap){
        imageViewComponent.setImageBitmap(bitmap);
    }

    public ImageViewComponent getImageViewComponent() {
        return imageViewComponent;
    }

    public void setImageViewComponent(Bitmap bitmap){
        imageViewComponent.setImageBitmap(bitmap);
    }

    public Dog getDog() {
        return dog;
    }

    @SuppressLint("LongLogTag")
    private void validateData(Bundle bundle){
        if(bundle != null){
            dog = (Dog)bundle.getSerializable("dogData");
            Log.d(TAG, "DOG DATA:" + dog.toString() );
        }else {
            Log.d(TAG, "DOG DATA EMPTY:");
        }
    }

    private void findViews(){
        nameTextView = findViewById(R.id.meal_notification_nameTextView);
        imageViewComponent = findViewById(R.id.meal_notification_imageView);
        imageViewComponent.setImageViewType(ImageViewComponent.ImageViewType.IDLE);
        portionTextView = findViewById(R.id.meal_notification_portionTextView);
        okButton = findViewById(R.id.meal_notification_okButton);
        okButton.setButtonType(ButtonComponent.ButtonType.CLOSE_MEAL_NOTIFICATION);
        remindAgainButton = findViewById(R.id.meal_notification_reminderButton);
        remindAgainButton.setButtonType(ButtonComponent.ButtonType.REMIND_AGAIN_MEAL_NOTIFICATION);
    }

    private void setHandlers(){
        ActionHandlerContract.ActionHandler notificationHandler = new NotificationHandler(this);
        ActionHandlerContract.ActionHandler viewHandler = new ViewHandler(this);

        viewHandler.setNextHandler(notificationHandler);
        setActionHandler(viewHandler);
    }
}
