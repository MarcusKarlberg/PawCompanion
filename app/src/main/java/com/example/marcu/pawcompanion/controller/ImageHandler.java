package com.example.marcu.pawcompanion.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.marcu.pawcompanion.activity.DogInfoInputActivity;
import com.example.marcu.pawcompanion.activity.MainActivity;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;

import java.io.FileNotFoundException;

import static android.content.ContentValues.TAG;
import static com.example.marcu.pawcompanion.activity.DogInfoInputActivity.ACCESS_PHOTO_LIB;

public class ImageHandler extends Handler implements ActionHandlerContract.ActionHandler {

    public ActionHandlerContract.ActionHandler nextHandler;

    public ImageHandler(MainActivity activity) {
        super(activity);
    }

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
                setImage(getImageViewComponent().getSelectedImage().toString());
            break;
        }
    }

    private void setImage(String imageUriString){

        //Todo: add if statement that sets activity to either MainActivity or DogInfoInputActivity

        if(imageUriString != null){
            Uri imageUri = Uri.parse(imageUriString);

            //to minimize memory -  outofmemoryerror
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 2;
            Bitmap bitmap;

            try {
                bitmap = BitmapFactory.decodeStream(getDogInfoInputActivity().getContentResolver().openInputStream(imageUri), null, options);
                getDogInfoInputActivity().imageViewComponent.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                //Todo: what's the best practice to handle exceptions in android
                Log.d(TAG, "FileNotFoundException");
            }
        }
    }

    private void accessPhotoLibrary(){
        Intent intent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }

        //Todo: figure out what flags do and if this flag is necessary
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
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

            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            getDogInfoInputActivity().startActivityForResult(intent, ACCESS_PHOTO_LIB);
        }
    }
}
