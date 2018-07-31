package com.example.marcu.pawcompanion.adapter;

import android.util.Log;
import android.widget.Filter;

import com.example.marcu.pawcompanion.data.Breed;
import com.example.marcu.pawcompanion.repository.BreedRepo;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class BreedFilter extends Filter{

    private BreedRepo breedRepo;
    private List<Breed> filteredBreeds;

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        List<Breed> breeds = breedRepo.resetFilteredList();

        if(isBlank(charSequence)){
            return setFilteredBreeds(breeds);
        }
        else {
            String charSequenceString = charSequence.toString().toLowerCase();
            this.filteredBreeds = new ArrayList<>(breeds.size());

            for(Breed b: breeds){
                String breedName = b.getName();
                if(breedName.contains(charSequenceString)){
                    this.filteredBreeds.add(b);
                }
            }
            return setFilteredBreeds(this.filteredBreeds);
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
    }
}
