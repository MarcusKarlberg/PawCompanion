package com.example.marcu.pawcompanion.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.component.ButtonComponent;
import com.example.marcu.pawcompanion.component.EditTextComponent;
import com.example.marcu.pawcompanion.component.ImageViewComponent;
import com.example.marcu.pawcompanion.component.TextViewComponent;
import com.example.marcu.pawcompanion.controller.ActionHandlerContract;
import com.example.marcu.pawcompanion.controller.DogInfoInputHandler;
import com.example.marcu.pawcompanion.controller.ImageHandler;
import com.example.marcu.pawcompanion.controller.ValidateInputHandler;
import com.example.marcu.pawcompanion.controller.ViewHandler;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Breed;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.exception.ExceptionHandler;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class DogInfoInputActivity extends AppCompatActivity implements ActionHandlerContract.RootActionHandler{
    private static final String TAG = "DogInfoInputActivity";
    public static final int ACCESS_PHOTO_LIB = 7686;
    public static final int SELECT_BREED_REQUEST = 2733;
    public static final int UPDATE = 0;
    public static final int CREATE = 1;

    private ActionHandlerContract.ActionHandler actionHandler;
    private ButtonComponent saveDogButton;
    private TextViewComponent breedTextView, birthdayTextView, walkTimeTextView, mealTimeTextView;
    private EditTextComponent nameEditText, weightEditText;
    private Breed selectedBreed;
    private Dog dog;
    private ImageViewComponent imageViewComponent;
    public int purposeOfActivity = CREATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_dog_info_input);

        findViews();
        setHandlers();
        handleIntent();
    }

    @Override
    public void invokeAction(HandlerType handlerType, Action action) {
        actionHandler.handle(handlerType, action);
    }

    public void setActionHandler(ActionHandlerContract.ActionHandler handler){
        this.actionHandler = handler;
    }

    public ImageViewComponent getImageViewComponent() {
        return imageViewComponent;
    }

    public void setImageViewComponent(Bitmap bitmap){
        imageViewComponent.setImageBitmap(bitmap);
    }

    public Breed getSelectedBreed(){
        return this.selectedBreed;
    }

    public void setSelectedBreed(Breed selectedBreed) {
        this.selectedBreed = selectedBreed;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if(requestCode == ACCESS_PHOTO_LIB){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                invokeAction(HandlerType.IMAGE, Action.ACCESS_PHOTO_LIB);
            }
            else {
                Toast.makeText(DogInfoInputActivity.this, "Couldn't access photo-library", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case SELECT_BREED_REQUEST:
                    this.selectedBreed = (Breed) intent.getSerializableExtra("breed");
                    invokeAction(HandlerType.MODEL, Action.SET_BREED);
                    break;
                case ACCESS_PHOTO_LIB:
                    getImageViewComponent().setSelectedImage(intent.getData());
                    invokeAction(HandlerType.IMAGE, Action.SET_IMAGE);
                    break;
            }
        }
    }

    private void handleIntent(){
        Intent intent = getIntent();
        Dog dog = (Dog) intent.getSerializableExtra("selectedDog");
        if(dog != null){
            this.dog = dog;
            purposeOfActivity = UPDATE;
            if(!isBlank(dog.getImageUriString())){
                getImageViewComponent().setSelectedImage(dog.getImageUriString());
            } else {
               imageViewComponent.setImageDrawable(getApplicationContext()
                       .getResources().getDrawable(R.drawable.placeholder));
            }
            invokeAction(HandlerType.MODEL, Action.SET_DOG_INFO);
        }
    }

    private void findViews(){
        nameEditText = findViewById(R.id.info_input_nameTextView);
        nameEditText.setEditTextType(EditTextComponent.EditTextType.SET_NAME);

        birthdayTextView = findViewById(R.id.info_input_birthdayTextView);
        birthdayTextView.setTextViewType(TextViewComponent.TextViewType.SET_BIRTHDAY);

        weightEditText = findViewById(R.id.info_input_weightEditText);
        weightEditText.setEditTextType(EditTextComponent.EditTextType.SET_WEIGHT);

        walkTimeTextView = findViewById(R.id.info_input_walkTimeTextView);
        walkTimeTextView.setTextViewType(TextViewComponent.TextViewType.SET_WALK_TIME);

        mealTimeTextView = findViewById(R.id.info_input_mealTimeTextView);
        mealTimeTextView.setTextViewType(TextViewComponent.TextViewType.SET_MEAL_TIME);

        breedTextView = findViewById(R.id.info_input_breedTextView);
        breedTextView.setTextViewType(TextViewComponent.TextViewType.BREED_SELECT);

        saveDogButton = findViewById(R.id.info_input_saveCompanionButton);
        saveDogButton.setButtonType(ButtonComponent.ButtonType.SAVE);

        imageViewComponent = findViewById(R.id.info_input_imageView);
        imageViewComponent.setImageViewType(ImageViewComponent.ImageViewType.BUTTON);
    }

    private void setHandlers(){
        ActionHandlerContract.ActionHandler dogInfoInputHandler = new DogInfoInputHandler(this);
        ActionHandlerContract.ActionHandler viewHandler = new ViewHandler(this);
        ActionHandlerContract.ActionHandler userInputHandler = new ValidateInputHandler(this);
        ActionHandlerContract.ActionHandler imageHandler = new ImageHandler(this);

        viewHandler.setNextHandler(userInputHandler);
        userInputHandler.setNextHandler(dogInfoInputHandler);
        dogInfoInputHandler.setNextHandler(imageHandler);
        setActionHandler(viewHandler);
    }
}
