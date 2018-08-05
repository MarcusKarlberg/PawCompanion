package com.example.marcu.pawcompanion.exception;

import android.app.Activity;
import java.io.PrintWriter;
import java.io.StringWriter;
import android.content.Intent;
import android.os.Build;

public class ExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler{

    private final Activity activity;
    private final String NEW_LINE = "\n";

    public ExceptionHandler(Activity activity){
        this.activity = activity;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        StringWriter stackTrace = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder errorReport = new StringBuilder();
        errorReport.append("************ CAUSE OF ERROR ************\n\n");
        errorReport.append(stackTrace.toString());

        errorReport.append("\n************ DEVICE INFORMATION ***********\n")
            .append("Brand: ")
            .append(Build.BRAND)
            .append(NEW_LINE)
            .append("Device: ")
            .append(Build.DEVICE)
            .append(NEW_LINE)
            .append("Model: ")
            .append(Build.MODEL)
            .append(NEW_LINE)
            .append("Id: ")
            .append(Build.ID)
            .append(NEW_LINE)
            .append("Product: ")
            .append(Build.PRODUCT)
            .append(NEW_LINE)
            .append("\n************ FIRMWARE ************\n")
            .append("SDK: ")
            .append(Build.VERSION.SDK_INT)
            .append(NEW_LINE)
            .append("Release: ")
            .append(Build.VERSION.RELEASE)
            .append(NEW_LINE)
            .append("Incremental: ")
            .append(Build.VERSION.INCREMENTAL)
            .append(NEW_LINE);

        Intent intent = new Intent(activity, ExceptionActivity.class);
        intent.putExtra("error", errorReport.toString());
        activity.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }
}
