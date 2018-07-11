package com.example.marcu.pawcompanion.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.data.Dog;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by marcu on 3/15/2018.
 */

public class DogListArrayAdapter extends ArrayAdapter {

    private static final String TAG = "ListAdapter";

    List<Dog> dogs;
    private final LayoutInflater inflater;
    private final int layoutResource;

    public DogListArrayAdapter(@NonNull Context context, int resource, List<Dog> dogs) {
        super(context, resource);
        this.layoutResource = resource;
        this.inflater = LayoutInflater.from(context);
        this.dogs = dogs;
    }

    @Override
    public int getCount() {
        return dogs.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(layoutResource, parent, false);
        }

        TextView dogNameTextView = (TextView) convertView.findViewById(R.id.dogNameTextView);
        ImageView dogPhoto = (ImageView) convertView.findViewById(R.id.photoImageView);

        Dog dog = dogs.get(position);

        dogNameTextView.setText(dog.getName());
        String imageUriString = dog.getImageUriString();

        if(imageUriString != null){
            Uri imageUri = Uri.parse(imageUriString);
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(imageUri));
                dogPhoto.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                //Todo: what's the best practice to handle exceptions in android
                Log.d(TAG, "FileNotFoundException");
            }
        }

        return convertView;
    }
}