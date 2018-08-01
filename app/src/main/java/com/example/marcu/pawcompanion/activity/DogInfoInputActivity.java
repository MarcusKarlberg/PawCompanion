package com.example.marcu.pawcompanion.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.component.ButtonComponent;
import com.example.marcu.pawcompanion.component.EditTextComponent;
import com.example.marcu.pawcompanion.component.TextViewComponent;
import com.example.marcu.pawcompanion.controller.ActionHandlerContract;
import com.example.marcu.pawcompanion.controller.DogInfoInputHandler;
import com.example.marcu.pawcompanion.controller.ValidateInputHandler;
import com.example.marcu.pawcompanion.controller.ViewHandler;
import com.example.marcu.pawcompanion.controller.constant.Action;
import com.example.marcu.pawcompanion.controller.constant.HandlerType;
import com.example.marcu.pawcompanion.data.Breed;
import com.example.marcu.pawcompanion.data.Dog;

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
    public int purposeOfActivity = CREATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_info_input);

        findViews();
        setHandlers();

        Intent intent = getIntent();
        Dog dog = (Dog) intent.getSerializableExtra("selectedDog");
        if(dog != null){
            this.dog = dog;
            purposeOfActivity = UPDATE;
            invokeAction(HandlerType.MODEL, Action.SET_DOG_INFO);
        }
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    private void findViews(){
        nameEditText = findViewById(R.id.dogNameEditText);
        nameEditText.setEditTextType(EditTextComponent.EditTextType.SET_NAME);

        birthdayTextView = findViewById(R.id.birthdayTextView);
        birthdayTextView.setTextViewType(TextViewComponent.TextViewType.SET_BIRTHDAY);

        weightEditText = findViewById(R.id.weightEditText);
        weightEditText.setEditTextType(EditTextComponent.EditTextType.SET_WEIGHT);

        walkTimeTextView = findViewById(R.id.walkTimeTextView);
        walkTimeTextView.setTextViewType(TextViewComponent.TextViewType.SET_WALK_TIME);

        mealTimeTextView = findViewById(R.id.mealTimeTextView);
        mealTimeTextView.setTextViewType(TextViewComponent.TextViewType.SET_MEAL_TIME);

        breedTextView = findViewById(R.id.breedTextView);
        breedTextView.setTextViewType(TextViewComponent.TextViewType.BREED_SELECT);

        saveDogButton = findViewById(R.id.saveCompanionButton);
        saveDogButton.setButtonType(ButtonComponent.ButtonType.SAVE);

        //imageView = (ImageView) findViewById(R.id.imageView);
    }

    private void setHandlers(){
        ActionHandlerContract.ActionHandler dogInfoInputHandler = new DogInfoInputHandler(this);
        ActionHandlerContract.ActionHandler viewHandler = new ViewHandler(this);
        ActionHandlerContract.ActionHandler userInputHandler = new ValidateInputHandler(this);

        viewHandler.setNextHandler(dogInfoInputHandler);
        dogInfoInputHandler.setNextHandler(userInputHandler);
        setActionHandler(viewHandler);
    }

    @Override
    public void invokeAction(HandlerType handlerType, Action action) {
        actionHandler.handle(handlerType, action);
    }

    //Todo: is this method really needed
    public ActionHandlerContract.ActionHandler getActionHandler() {
        return this.actionHandler;
    }

    public void setActionHandler(ActionHandlerContract.ActionHandler handler){
        this.actionHandler = handler;
    }

    public void setBreedText(String breedName){
        Log.d(TAG, "setBreedText: " + breedName);
        breedTextView.setText(breedName);
    }

    public void setBirthdayTextView(String birthday){
        birthdayTextView.setText(birthday);
    }

    public void setWalkTimeTextView(String time){
        walkTimeTextView.setText(time);
    }

    public void setMealTimeTextView(String time){
        mealTimeTextView.setText(time);
    }

    public String getBirthDayText(){
        return birthdayTextView.getText().toString();
    }

    public String getWalkTimeText(){
        return walkTimeTextView.getText().toString();
    }

    public String getMealTimeText(){
        return mealTimeTextView.getText().toString();
    }

    public String getWeighText(){
        return weightEditText.getText().toString();
    }

    public String getNameEditText(){
        return nameEditText.getText().toString();
    }

    public Breed getSelectedBreed(){
        return this.selectedBreed;
    }

    public void setNameText(String name) {
        this.nameEditText.setText(name);
    }

    public void setWeightText(String weight) {
        this.weightEditText.setText(weight);
    }

    public void setSelectedBreed(Breed selectedBreed) {
        this.selectedBreed = selectedBreed;
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
                    //Todo: Create ImageHandler  and invokeAction(HandlerType.MODEL, Action.SET_PHOTO);
                break;
            }
        }
    }

//    private void setImageViewClickListener(){
//        imageView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//
//                if(ActivityCompat.checkSelfPermission(DogInfoInputActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(DogInfoInputActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, ACCESS_PHOTO_LIB);
//                } else {
////                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                    startActivityForResult(intent, ACCESS_PHOTO_LIB);
//                    Intent intent;
//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    } else {
//                        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    }
//                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//                    startActivityForResult(intent, ACCESS_PHOTO_LIB);
//                }
//
//            }
//        });
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
//    {
//        if(requestCode == ACCESS_PHOTO_LIB){
//            //If request is denied array is empty
//            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Intent intent;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                } else {
//                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                }
//                //Todo: figure out what flags do and if this flag is necessary
//                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//                startActivityForResult(intent, ACCESS_PHOTO_LIB);
//            } else {
//                Toast.makeText(DogInfoInputActivity.this, "Couldn't access photo-library", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == ACCESS_PHOTO_LIB){
//            if(resultCode == RESULT_OK){
//                imageUri = data.getData();
//                setImageView(imageUri.toString());
//            }
//        }
//        if(requestCode == SELECT_BREED_REQUEST){
//            if(resultCode == RESULT_OK){
//                //Select the breed
//                this.selectedBreedstring = data.getStringExtra("selectedBreed");
//                breedTextView.setText(this.selectedBreedstring);
//                selectedBreed = breedRepo.getBreedByName(selectedBreedstring);
//            }
//        }
//    }



//    private void setImageView(String imageUriString){
//
//        if(imageUriString != null){
//            Uri imageUri = Uri.parse(imageUriString);
//
//            //to minimize memory -  outofmemoryerror
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.RGB_565;
//            options.inSampleSize = 2;
//            Bitmap bitmap;
//
//            try {
//                //Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
//                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, options);
//                imageView.setImageBitmap(bitmap);
//            }catch (FileNotFoundException e){
//                //Todo: what's the best practice to handle exceptions in android
//                Log.d(TAG, "FileNotFoundException");
//            }
//        }
//    }
//    private void showToast(String message){
//        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL, 0, 0);
//        toast.show();
//    }
}
