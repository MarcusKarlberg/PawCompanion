package com.example.marcu.pawcompanion.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.data.Breed;
import com.example.marcu.pawcompanion.repository.BreedRepo;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class BreedListAdapter extends BaseAdapter implements Filterable{

    private BreedRepo breedRepo;
    private BreedFilter breedFilter;

    public BreedListAdapter(){
        this.breedRepo = new BreedRepo();
    }

    public BreedRepo getDogRepository(){
        return breedRepo;
    }

    @Override
    public int getCount() {
       return breedRepo.getAllBreeds().size();
    }

    @Override
    public Object getItem(int position) {
        return this.breedRepo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return breedRepo.getIndexOf(position);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Breed breed = (Breed) this.getItem(i);

        if (view == null) {
            view = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.breed_list_item, viewGroup, false);
        }

        if(breed != null){
            TextView breedTextView = view.findViewById(R.id.textViewBreed);
            breedTextView.setText(breed.getName());
            //Todo: add boolean in breedRepo that the breed is selected
            //breedItemView.setCheckMarkTintList();
            //breedItemView.setChecked(breed.isChecked);
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        if(breedFilter == null){
            breedFilter = new BreedFilter();
        }
        return breedFilter;
    }

    public class BreedFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Breed> breeds = breedRepo.resetFilteredList();

            if(isBlank(charSequence)){
                return setFilteredBreeds(breeds);
            }
            else {
                String charSequenceString = charSequence.toString().toLowerCase();
                List<Breed> filteredBreeds = new ArrayList<>();

                for(Breed b: breeds){
                    if(b.getName().toLowerCase().contains(charSequenceString)){
                        filteredBreeds.add(b);
                    }
                }
                return setFilteredBreeds(filteredBreeds);
            }
        }

        private FilterResults setFilteredBreeds(List<Breed> breeds) {
            FilterResults results = new FilterResults();
            results.count = breeds.size();
            results.values = breeds;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            Log.d(TAG, "publishResults: " + filterResults.values);
            breedRepo.setAll((List<Breed>) filterResults.values);
            notifyDataSetChanged();
        }
    }
}
