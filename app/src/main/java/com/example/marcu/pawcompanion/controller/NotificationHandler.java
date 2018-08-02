package com.example.marcu.pawcompanion.controller;

import com.example.marcu.pawcompanion.activity.MainActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.notification.NotificationMngr;

public class NotificationHandler extends Handler implements ActionHandlerContract.ActionHandler{

    public ActionHandlerContract.ActionHandler nextHandler;

    public NotificationHandler(MainActivity activity) {
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
        Dog dog = getDogListComponent().getSelectedDog();
        NotificationMngr notificationMngr = new NotificationMngr(getMainActivity());

        switch (action){
            case ADD_NOTIFICATION:
                dog.setWalkingDurationPerDay(dog.getBreed().getActivityLevel());
                dog.setWalkingDistancePerDay(dog.getBreed().getActivityLevel());
                dog.setIntervalWalkTime(dog.getBreed().getActivityLevel());
                dog.setIntervalMealTime();

                notificationMngr.setWalkNotification(dog);
                notificationMngr.setMealNotification(dog);
                notificationMngr.setAlarmToResetDailyNotificationAlarms(dog);
            break;

            case REMOVE_NOTIFICATION:
                notificationMngr.deleteNotifications(dog);
            break;
        }
    }
}
