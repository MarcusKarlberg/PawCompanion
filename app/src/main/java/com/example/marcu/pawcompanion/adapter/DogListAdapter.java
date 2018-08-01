package com.example.marcu.pawcompanion.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.component.DogListComponent;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.repository.DogRepository;

import java.io.FileNotFoundException;

/**
 * Created by marcu on 3/15/2018.
 */

public class DogListAdapter extends BaseAdapter {

    private static final String TAG = "ListAdapter";
    private DogRepository dogRepository;

    public DogListAdapter(){
        this.dogRepository = new DogRepository();
    }

    public DogRepository getDogRepository(){
        return dogRepository;
    }

    @Override
    public int getCount() {
        return this.dogRepository.getAllDogs().size();
    }

    @Override
    public Object getItem(int i) {
        return this.dogRepository.getById(i);
    }

    @Override
    public long getItemId(int i) {
        return this.dogRepository.getIdByPosition(i);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Dog dog = (Dog) this.getItem(position);

        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item, viewGroup, false);
        }

        if(dog != null){
            TextView dogNameTextView = view.findViewById(R.id.dogNameTextView);
            dogNameTextView.setText(dog.getName());
        }

        return view;
    }


    //Todo: reformat setImage method
//    private void setImageView(){
//
//        Dog dog = dogs.get(position);
//        dogNameTextView.setText(dog.getName());
//        String imageUriString = dog.getImageUriString();
//
//        if(imageUriString != null){
//            Uri imageUri = Uri.parse(imageUriString);
//
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.RGB_565;
//            options.inSampleSize = 2;
//            Bitmap bitmap;
//
//            try {
//                //Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
//                bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(imageUri), null, options);
//                dogPhoto.setImageBitmap(bitmap);
//
//            }catch (FileNotFoundException e){
//                //Todo: what's the best practice to handle exceptions in android
//                Log.d(TAG, "FileNotFoundException");
//            }
//        }
//    }
}