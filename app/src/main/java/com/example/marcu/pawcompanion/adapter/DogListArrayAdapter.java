package com.example.marcu.pawcompanion.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.data.Dog;
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

        //Change color when selected
//            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.primary_light));
//            convertView.invalidate();
//
//            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.primary));
//            convertView.invalidate();


        TextView dogNameTextView = (TextView) convertView.findViewById(R.id.dogNameTextView);
        //ImageView dogPortraitImageView = (ImageView) convertView.findViewById(R.id.photoImageView);

        Dog dog = dogs.get(position);

        dogNameTextView.setText(dog.getName());
        //Todo: figure out how to change/set an image???
        //photoImageView.setImageResource();

        return convertView;
    }
}