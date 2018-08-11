package com.example.marcu.pawcompanion.controller;

import android.net.Uri;
import android.util.Log;

import com.example.marcu.pawcompanion.activity.MainActivity;
import com.example.marcu.pawcompanion.activity.MealNotificationActivity;
import com.example.marcu.pawcompanion.activity.WalkNotificationActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.notification.NotificationMngr;
import com.example.marcu.pawcompanion.utility.DogCalculator;
import com.example.marcu.pawcompanion.utility.ImageUtils;

import org.joda.time.LocalTime;

import java.util.Locale;

import static android.content.ContentValues.TAG;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class NotificationHandler extends Handler implements ActionHandlerContract.ActionHandler{

    public ActionHandlerContract.ActionHandler nextHandler;

    public NotificationHandler(MainActivity activity) {
        super(activity);
    }

    public NotificationHandler(MealNotificationActivity activity){
        super(activity);
    }

    public NotificationHandler(WalkNotificationActivity activity){
        super(activity);
    }

    @Override
    public void setNextHandler(ActionHandlerContract.ActionHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handle(HandlerType handlerType, Action action) {
        if(handlerType == HandlerType.NOTIFICATION){
            updateNotification(action);
        }
    }

    private void updateNotification(Action action){
        switch (action){
            case ADD_NOTIFICATION:
                addNotifications();
            break;
            case REMOVE_NOTIFICATION:
                Dog dog = getDogListComponent().getSelectedDog();
                NotificationMngr notificationMngr = new NotificationMngr(getMainActivity());
                notificationMngr.deleteNotifications(dog);
                Log.d(TAG, "updateNotification: NOTIFICATIONS REMOVED for:  " + dog.toString());
            break;
            case SET_MEAL_REMINDER:
                setMealNotificationReminder();
            break;
            case SET_WALK_REMINDER:
                setWalkNotificationReminder();
            break;
            case SET_WALK_NOTIFICATION_INFO:
                setWalkNotificationInfo(getWalkNotificationActivity().getDog(),getWalkNotificationActivity());
            break;
            case SET_MEAL_NOTIFICATION_INFO:
                setMealNotificationInfo(getMealNotificationActivity().getDog(), getMealNotificationActivity());
            break;
        }
    }

    private void addNotifications(){
        Dog dog = getDogListComponent().getSelectedDog();

        NotificationMngr notificationMngr = new NotificationMngr(getMainActivity());
        notificationMngr.setWalkNotification(dog);
        notificationMngr.setMealNotification(dog);
        notificationMngr.setAlarmToResetDailyNotificationAlarms(dog);
    }

    private void setMealNotificationReminder(){
        Dog dog = getMealNotificationActivity().getDog();
        NotificationMngr mealNotificationMngr = new NotificationMngr(getMealNotificationActivity().getApplicationContext());
        LocalTime firstMealTime = dog.getFirstMealTime();
        dog.setFirstMealTime(LocalTime.now().plusMinutes(10).toString());
        mealNotificationMngr.setMealNotification(dog);
        dog.setFirstMealTime(firstMealTime.toString());
        getMealNotificationActivity().finish();
    }

    private void setWalkNotificationReminder(){
        Dog dog = getWalkNotificationActivity().getDog();
        NotificationMngr walkNotificationMngr = new NotificationMngr(getWalkNotificationActivity().getApplicationContext());
        LocalTime firstWalkTime = dog.getFirstMealTime();
        dog.setFirstWalkTime(LocalTime.now().plusMinutes(10).toString());
        walkNotificationMngr.setWalkNotification(dog);
        dog.setFirstWalkTime(firstWalkTime.toString());
        getWalkNotificationActivity().finish();
    }

    private void setMealNotificationInfo(Dog dog, MealNotificationActivity activity){
        if (dog != null) {
            activity.setNameTextView(dog.getName());

            ImageUtils imageUtils = new ImageUtils(getMealNotificationActivity());
            String uri = getMealNotificationActivity().getDog().getImageUriString();
            if(!isBlank(uri)){
                getMealNotificationActivity().setImageViewComponent(imageUtils
                        .setImage(Uri.parse(uri)));
            }
        }
    }

    private void setWalkNotificationInfo(Dog dog, WalkNotificationActivity activity){
        if (dog != null) {
            activity.setNameTextView(dog.getName());
            double distance = DogCalculator.getDistancePerWalk(dog);
            activity.setWalkingDistanceTextView(String.format(Locale.getDefault(),"Distance %.1f km  |  %.1f mi", distance, (distance * 0.62)));
            activity.setWalkingDurationTextView(String.format(Locale.getDefault(),"Duration: ~ %d min", DogCalculator.getDurationPerWalk(dog)));

            ImageUtils imageUtils = new ImageUtils(getWalkNotificationActivity());
            String uri = getWalkNotificationActivity().getDog().getImageUriString();
            if(!isBlank(uri)){
                getWalkNotificationActivity().setImageViewComponent(imageUtils
                        .setImage(Uri.parse(uri)));
            }
        }
    }
}
