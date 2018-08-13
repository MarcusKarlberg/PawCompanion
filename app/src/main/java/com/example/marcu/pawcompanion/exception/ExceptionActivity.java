package com.example.marcu.pawcompanion.exception;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.marcu.pawcompanion.R;

public class ExceptionActivity extends Activity{

    TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_exception);

        errorTextView.setText(getIntent().getStringExtra("error"));
    }
}
