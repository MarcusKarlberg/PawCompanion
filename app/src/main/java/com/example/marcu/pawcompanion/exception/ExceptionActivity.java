package com.example.marcu.pawcompanion.exception;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.marcu.pawcompanion.R;

public class ExceptionActivity extends Activity{

    TextView titleTextView;
    TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_exception);

        titleTextView = (TextView) findViewById(R.id.activity_exception_titleTextView);
        errorTextView = (TextView) findViewById(R.id.activity_exception_textView);
        errorTextView.setText(getIntent().getStringExtra("error"));
    }
}
