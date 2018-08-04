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

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.component.ImageViewComponent;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.repository.DogRepository;

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
            imageViewComponent = view.findViewById(R.id.list_item_imageView);
            if(!StringUtils.isBlank(dog.getImageUriString())){
                imageViewComponent.setSelectedImage(Uri.parse(dog.getImageUriString()));
            }
            dogNameTextView.setText(dog.getName());
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
                //Todo: what's the best practice to handle exceptions in android
                Log.d(TAG, "FileNotFoundException");
            }
        }
    }
}