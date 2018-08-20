package com.example.marcu.pawcompanion.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.activity.MainActivity;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class DisclaimerDialog {
    private static final String DISCLAIMER_INFO = "disclaimer info";
    private static final String PREFS_KEY_DISCLAIMER = "shared prefs key disclaimer";

    public void show(Context context){
        Log.d(TAG, "showDisclaimerDialog: ACTIVATED");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        boolean disclaimerViewed = context.getSharedPreferences(PREFS_KEY_DISCLAIMER, MODE_PRIVATE).getBoolean(DISCLAIMER_INFO, false);

        if(!disclaimerViewed){
            builder.setMessage(R.string.disclaimer_dialog_fragment_paragraph_1)
                    .setMessage(R.string.disclaimer_dialog_fragment_paragraph_2)
                    .setTitle(R.string.title__disclaimer_dialog)
                    .setPositiveButton(R.string.btn_accept_disclaimer_dialog, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            saveDisclaimerAgreement(context);
                        }
                    })
                    .setNegativeButton(R.string.btn_quit_disclaimer_dialog, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if(context instanceof MainActivity){
                                ((MainActivity)context).finish(); }
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    private void saveDisclaimerAgreement(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_KEY_DISCLAIMER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(DISCLAIMER_INFO, true);
        editor.apply();
        Log.d(TAG, "saveDisclaimerAgreement: DISCLAIMER SAVED!");
    }
}
