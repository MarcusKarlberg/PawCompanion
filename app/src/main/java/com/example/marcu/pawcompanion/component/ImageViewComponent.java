package com.example.marcu.pawcompanion.component;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.marcu.pawcompanion.controller.ActionHandlerContract;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class ImageViewComponent extends AppCompatImageView {
    public enum ImageViewType {
        BUTTON, IDLE
    }

    private ActionHandlerContract.RootActionHandler rootActionHandler;
    private Uri selectedImage;

    public Uri getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(String uriString){
        if(!isBlank(uriString)){
            setSelectedImage(Uri.parse(uriString));
        }
    }

    public void setSelectedImage(Uri selectedImage) {
        this.selectedImage = selectedImage;
    }

    public ImageViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.rootActionHandler = (ActionHandlerContract.RootActionHandler) context;
    }

    public void setImageViewType(ImageViewType type){
        switch (type){
            case BUTTON:
                this.setOnClickListener(onClickAddImage());
            break;
        }
    }

    private OnClickListener onClickAddImage(){
        return view -> rootActionHandler.invokeAction(HandlerType.IMAGE, Action.REQUEST_ACCESS_PHOTO_LIB);
    }

}
