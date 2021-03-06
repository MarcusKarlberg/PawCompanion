package com.example.marcu.pawcompanion.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import com.example.marcu.pawcompanion.activity.DogInfoInputActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.utility.ImageUtils;

import static com.example.marcu.pawcompanion.activity.DogInfoInputActivity.ACCESS_PHOTO_LIB;


public class ImageHandler extends Handler implements ActionHandlerContract.ActionHandler {

    public ActionHandlerContract.ActionHandler nextHandler;

    public ImageHandler(DogInfoInputActivity activity) {
        super(activity);
    }

    @Override
    public void setNextHandler(ActionHandlerContract.ActionHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handle(HandlerType handlerType, Action action) {
        if(handlerType == HandlerType.IMAGE){
            updateImage(action);
        }
    }

    private void updateImage(Action action){
        switch (action){
            case REQUEST_ACCESS_PHOTO_LIB:
                requestAccessPhotoLibrary();
            break;
            case ACCESS_PHOTO_LIB:
                accessPhotoLibrary();
            break;
            case SET_IMAGE:
                ImageUtils imageUtils = new ImageUtils(getDogInfoInputActivity());
                getDogInfoInputActivity().setImageViewComponent(imageUtils
                        .getBitmap(getImageViewComponent().getSelectedImage()));
            break;
        }
    }

    private void accessPhotoLibrary(){
        Intent intent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }

        getDogInfoInputActivity().startActivityForResult(intent, ACCESS_PHOTO_LIB);
    }

    private void requestAccessPhotoLibrary(){
        if(ActivityCompat.checkSelfPermission(getDogInfoInputActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getDogInfoInputActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, ACCESS_PHOTO_LIB);
        }
        else{
            Intent intent;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            } else {
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }

            getDogInfoInputActivity().startActivityForResult(intent, ACCESS_PHOTO_LIB);
        }
    }
}
