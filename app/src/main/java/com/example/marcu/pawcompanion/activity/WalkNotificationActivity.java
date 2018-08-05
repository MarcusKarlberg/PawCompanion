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
import com.example.marcu.pawcompanion.exception.ExceptionHandler;

public class WalkNotificationActivity extends AppCompatActivity implements ActionHandlerContract.RootActionHandler{

    private static final String TAG = "WalkNotificationActivity";
    private ActionHandlerContract.ActionHandler actionHandler;
    private TextView nameTextView;
    public ImageViewComponent imageViewComponent;
    private TextView walkingDistanceTextView;
    private TextView walkingDurationTextView;
    private ButtonComponent okButton;
    private ButtonComponent remindAgainButton;
    private Dog dog;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_walk_notification);

        findViews();
        Bundle bundle = (getIntent().getExtras().getBundle("bundle"));
        validateData(bundle);
        setHandlers();
        getImageViewComponent().setSelectedImage(this.dog.getImageUriString());

        invokeAction(HandlerType.NOTIFICATION, Action.SET_WALK_NOTIFICATION_INFO);
    }

    @Override
    public void invokeAction(HandlerType handlerType, Action action) {
        actionHandler.handle(handlerType, action);
    }

    public void setActionHandler(ActionHandlerContract.ActionHandler handler) {
        this.actionHandler = handler;
    }

    public void setImageViewComponent(Bitmap bitmap){
        imageViewComponent.setImageBitmap(bitmap);
    }

    public ImageViewComponent getImageViewComponent() {
        return imageViewComponent;
    }

    public void setNameTextView(String text){
        nameTextView.setText(text);
    }

    public void setWalkingDistanceTextView(String text){
        walkingDistanceTextView.setText(text);
    }

    public void setWalkingDurationTextView(String text){
        walkingDurationTextView.setText(text);
    }

    public Dog getDog() {
        return dog;
    }

    @SuppressLint("LongLogTag")
    private boolean validateData(Bundle bundle){
        if(bundle != null){
            dog = (Dog)bundle.getSerializable("dogData");
            Log.d(TAG, "DOG DATA:" + dog.toString() );
            return true;
        }else {
            Log.d(TAG, "DOG DATA EMPTY:");
            return false;
        }
    }

    private void findViews(){
        nameTextView = (TextView) findViewById(R.id.walk_notification_nameTextView);
        imageViewComponent = findViewById(R.id.walk_notification_imageView);
        imageViewComponent.setImageViewType(ImageViewComponent.ImageViewType.IDLE);
        walkingDistanceTextView = (TextView) findViewById(R.id.walk_notification_distanceTextView);
        walkingDurationTextView = (TextView) findViewById(R.id.walk_notification_durationTextView);
        okButton = findViewById(R.id.walk_notification_okButton);
        okButton.setButtonType(ButtonComponent.ButtonType.CLOSE_WALK_NOTIFICATION);
        remindAgainButton = findViewById(R.id.walk_notification_reminderButton);
        remindAgainButton.setButtonType(ButtonComponent.ButtonType.REMIND_AGAIN_WALK_NOTIFICATION);
    }

    private void setHandlers(){
        ActionHandlerContract.ActionHandler notificationHandler = new NotificationHandler(this);
        ActionHandlerContract.ActionHandler viewHandler = new ViewHandler(this);

        viewHandler.setNextHandler(notificationHandler);
        setActionHandler(viewHandler);
    }
}
