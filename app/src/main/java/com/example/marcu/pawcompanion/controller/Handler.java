package com.example.marcu.pawcompanion.controller;

import com.example.marcu.pawcompanion.activity.DogInfoInputActivity;
import com.example.marcu.pawcompanion.activity.MainActivity;
import com.example.marcu.pawcompanion.activity.MealNotificationActivity;
import com.example.marcu.pawcompanion.activity.SelectBreedActivity;
import com.example.marcu.pawcompanion.activity.WalkNotificationActivity;
import com.example.marcu.pawcompanion.adapter.BreedListAdapter;
import com.example.marcu.pawcompanion.adapter.DogListAdapter;
import com.example.marcu.pawcompanion.component.BreedListComponent;
import com.example.marcu.pawcompanion.component.DogListComponent;
import com.example.marcu.pawcompanion.component.ImageViewComponent;
import com.example.marcu.pawcompanion.repository.BreedRepo;
import com.example.marcu.pawcompanion.repository.DogRepository;

public abstract class Handler {

    private MainActivity mainActivity;
    private DogInfoInputActivity dogInfoInputActivity;
    private SelectBreedActivity selectBreedActivity;
    private MealNotificationActivity mealNotificationActivity;
    private WalkNotificationActivity walkNotificationActivity;

    public Handler(MainActivity activity) {
        this.mainActivity = activity;
    }
    public Handler(DogInfoInputActivity activity) {
        this.dogInfoInputActivity = activity;
    }
    public Handler(SelectBreedActivity activity){
        this.selectBreedActivity = activity;
    }
    public Handler(MealNotificationActivity activity){
        this.mealNotificationActivity = activity;
    }
    public Handler(WalkNotificationActivity activity){
        this.walkNotificationActivity = activity;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }
    public DogInfoInputActivity getDogInfoInputActivity() {
        return dogInfoInputActivity;
    }
    public SelectBreedActivity getSelectBreedActivity() {
        return selectBreedActivity;
    }
    public MealNotificationActivity getMealNotificationActivity(){
        return mealNotificationActivity;
    }
    public WalkNotificationActivity getWalkNotificationActivity() {
        return walkNotificationActivity;
    }

    ActionHandlerContract.RootActionHandler getMainRootActionHandler(){
        return getMainActivity();
    }

    ActionHandlerContract.RootActionHandler getDogInfoInputRootActionHandler(){
        return getDogInfoInputActivity();
    }

    ActionHandlerContract.RootActionHandler getSelectBreedRootActionHandler(){
        return getSelectBreedActivity();
    }

    DogListComponent getDogListComponent(){
        return getMainActivity().getListComponent();
    }

    BreedListComponent getBreedListComponent(){
        return getSelectBreedActivity().getBreedListComponent();
    }

    DogListAdapter getDogListAdapter(){
        return (DogListAdapter) getDogListComponent().getAdapter();
    }
    BreedListAdapter getBreedListAdapter(){
        return (BreedListAdapter) getBreedListComponent().getAdapter();
    }

    ImageViewComponent getImageViewComponent(){
        return getDogInfoInputActivity().getImageViewComponent();
    }

    DogRepository getDogRepo(){
        return getDogListAdapter().getDogRepository();
    }
    BreedRepo getBreedRepo(){
        return getBreedListAdapter().getDogRepository();
    }
}
