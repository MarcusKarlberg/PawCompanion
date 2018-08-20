package com.example.marcu.pawcompanion.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.component.ImageViewComponent;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.repository.DogRepository;
import com.example.marcu.pawcompanion.utility.DogCalculator;
import com.example.marcu.pawcompanion.utility.ImageUtils;

import org.apache.commons.lang3.StringUtils;


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
        ViewHolder viewHolder;

        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if(dog != null){
            if(!StringUtils.isBlank(dog.getImageUriString())){
                //Picasso.get().load(dog.getImageUri()).into(viewHolder.imageViewComponent);
                ImageUtils imageUtils = new ImageUtils(view.getContext());
                viewHolder.imageViewComponent.setImageBitmap(imageUtils.getBitmap(dog.getImageUri()));
            }
            else {
                viewHolder.imageViewComponent.setImageDrawable(viewHolder.placeHolder);
            }
            viewHolder.dogNameTextView.setText(dog.getName());
            //Todo: add countdown
            viewHolder.nextWalkTimeTextView.setText( view.getContext().getString(R.string.next_walk_time, DogCalculator.getNextWalkTimeString(dog)));
            viewHolder.nextMealTimeTextView.setText(view.getContext().getString(R.string.next_meal_time, DogCalculator.getNextMealTimeString(dog)));
        }

        return view;
    }

    private class ViewHolder {
        TextView dogNameTextView;
        TextView nextWalkTimeTextView;
        TextView nextMealTimeTextView;
        ImageViewComponent imageViewComponent;
        Drawable placeHolder;

        public ViewHolder(View view){
            dogNameTextView = view.findViewById(R.id.list_item_nameTextView);
            nextWalkTimeTextView = view.findViewById(R.id.list_item_nextWalk_textView);
            nextMealTimeTextView = view.findViewById(R.id.list_item_nextMeal_textView);
            imageViewComponent = view.findViewById(R.id.list_item_imageView);
            placeHolder = view.getContext().getResources().getDrawable(R.drawable.placeholder);
        }
    }
}