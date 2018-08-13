package com.example.marcu.pawcompanion.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.component.ImageViewComponent;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.repository.DogRepository;
import com.example.marcu.pawcompanion.utility.DogCalculator;

import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;

/**
 * Created by marcu on 3/15/2018.
 */

public class DogListAdapter extends BaseAdapter {

    private static final String TAG = "ListAdapter";
    private DogRepository dogRepository;
    private ImageViewComponent imageViewComponent;

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
            TextView dogNameTextView = view.findViewById(R.id.list_item_nameTextView);
            TextView nextWalkTimeTextView = view.findViewById(R.id.list_item_nextWalk_textView);
            TextView nextMealTimeTextView = view.findViewById(R.id.list_item_nextMeal_textView);

            imageViewComponent = view.findViewById(R.id.list_item_imageView);
            if(!StringUtils.isBlank(dog.getImageUriString())){
                imageViewComponent.setSelectedImage(Uri.parse(dog.getImageUriString()));
            }
            dogNameTextView.setText(dog.getName());
            //Todo: add countdown
            nextWalkTimeTextView.setText( view.getContext().getString(R.string.next_walk_time, DogCalculator.getNextWalkTime(dog)));
            nextMealTimeTextView.setText(view.getContext().getString(R.string.next_meal_time, DogCalculator.getNextMealTime(dog)));
            setImageView(dog.getImageUriString(), viewGroup);
        }

        return view;
    }

    private void setImageView(String imageUriString, ViewGroup viewGroup){
        if(imageUriString != null){
            Uri imageUri = Uri.parse(imageUriString);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 2;
            Bitmap bitmap;

            try {
                bitmap = BitmapFactory.decodeStream(viewGroup.getContext().getContentResolver().openInputStream(imageUri), null, options);
                imageViewComponent.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                Log.d(TAG, "FileNotFoundException");
                Toast.makeText(viewGroup.getContext(), "Photo Not Found",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}