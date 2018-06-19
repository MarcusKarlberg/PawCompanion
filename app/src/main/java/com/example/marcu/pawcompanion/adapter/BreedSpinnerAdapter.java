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
import com.example.marcu.pawcompanion.data.Breed;

import java.util.ArrayList;

/**
 * Created by marcu on 3/18/2018.
 */

public class BreedSpinnerAdapter extends ArrayAdapter<Breed> {

    public BreedSpinnerAdapter(Context context, ArrayList<Breed> breeds){
        super(context, 0, breeds);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.breed_spinner_item, parent, false);
        }

        TextView breedTextView = convertView.findViewById(R.id.text_view_breed);
        Breed breedItem = getItem(position);

        if(breedItem != null){
            breedTextView.setText(breedItem.getName());
        }

        return convertView;
    }

}
