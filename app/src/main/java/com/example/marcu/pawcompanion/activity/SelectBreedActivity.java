package com.example.marcu.pawcompanion.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.repository.BreedRepo;

import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class SelectBreedActivity extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    Button selectButton;
    ArrayAdapter<String> adapter;
    ArrayList<String> breeds;
    String selectedBreed;
    BreedRepo breedRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_breed);

        loadBreedList();
        findViews();
        setAdapter();

        setOnQueryTextListener();
        setOnClickListener();
        setSelectBreedButtonClickListener();
    }

    private void findViews(){
        listView = (ListView) findViewById(R.id.listView);
        listView.setVisibility(View.GONE);
        searchView = (SearchView) findViewById(R.id.searchView);
        selectButton = (Button) findViewById(R.id.selectButton);
    }

    private void loadBreedList(){
        breedRepo = new BreedRepo();
        breeds = breedRepo.getAllBreedNames();
    }

    private void setAdapter(){
        adapter = new ArrayAdapter<String>(SelectBreedActivity.this, android.R.layout.simple_list_item_1, breeds);
        listView.setAdapter(adapter);
    }
    private void setOnQueryTextListener(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }


    private void setOnClickListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBreed = adapterView.getItemAtPosition(i).toString();
                searchView.setQuery(selectedBreed, true);
                listView.setVisibility(View.GONE);
            }
        });
    }

    private void setSelectBreedButtonClickListener(){
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isBlank(selectedBreed)){
                    Intent intent = new Intent();
                    intent.putExtra("selectedBreed", selectedBreed);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    showToast("Invalid Breed - Choose a breed");
                }
            }
        });
    }

    private void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}
