package com.example.marcu.pawcompanion.controller;

import android.net.Uri;
import com.example.marcu.pawcompanion.activity.MainActivity;
import com.example.marcu.pawcompanion.activity.MealNotificationActivity;
import com.example.marcu.pawcompanion.activity.WalkNotificationActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.notification.NotificationMngr;
import com.example.marcu.pawcompanion.utility.ImageUtils;

import org.joda.time.LocalTime;

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
        dog.setWalkingDurationPerDay(dog.getBreed().getActivityLevel());
        dog.setWalkingDistancePerDay(dog.getBreed().getActivityLevel());
        dog.setIntervalWalkTime(dog.getBreed().getActivityLevel());
        dog.setIntervalMealTime();

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
            getMealNotificationActivity().setImageViewComponent(imageUtils
                    .setImage(Uri.parse(getMealNotificationActivity().getDog().getImageUriString())));
        }
    }

    private void setWalkNotificationInfo(Dog dog, WalkNotificationActivity activity){
        if (dog != null) {
            activity.setNameTextView(dog.getName());
            activity.setWalkingDistanceTextView(String.format("Distance %.1f km", getDistancePerWalk(dog)));
            activity.setWalkingDurationTextView(String.format("Duration: %d min", getDurationPerWalk(dog)));

            ImageUtils imageUtils = new ImageUtils(getWalkNotificationActivity());
            getWalkNotificationActivity().setImageViewComponent(imageUtils
                    .setImage(Uri.parse(getWalkNotificationActivity().getDog().getImageUriString())));
        }
    }

    private double getDistancePerWalk(Dog dog) {
        double distancePerDay = dog.getWalkingDistancePerDay();
        int numberOfWalksPerDay;

        switch (dog.getIntervalWalkTime()){
            case 180: numberOfWalksPerDay = 7;
                break;
            case 240: numberOfWalksPerDay = 5;
                break;
            case 300: numberOfWalksPerDay = 4;
                break;
            case 360: numberOfWalksPerDay = 3;
                break;
            default: numberOfWalksPerDay = 3;
        }
        return distancePerDay/numberOfWalksPerDay;
    }

    private int getDurationPerWalk(Dog dog){
        double durationPerDay = dog.getWalkingDurationPerDay();
        int numberOfWalksPerDay;

        switch (dog.getIntervalWalkTime()){
            case 180: numberOfWalksPerDay = 7;
                break;
            case 240: numberOfWalksPerDay = 5;
                break;
            case 300: numberOfWalksPerDay = 4;
                break;
            case 360: numberOfWalksPerDay = 3;
                break;
            default: numberOfWalksPerDay = 3;
        }

        return (int) Math.round(Math.abs(durationPerDay/numberOfWalksPerDay));
    }
}
