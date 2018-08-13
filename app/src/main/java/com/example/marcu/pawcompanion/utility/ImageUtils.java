package com.example.marcu.pawcompanion.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.FileNotFoundException;

import static android.content.ContentValues.TAG;

public class ImageUtils {
    private Context context;

    public ImageUtils(Context context) {
        this.context = context;
    }

    public Bitmap setImage(Uri uri) {
        if (uri != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 2;

            try {
                Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
                return bitmap;
            }catch (FileNotFoundException e) {
                Log.d(TAG, "FileNotFoundException");
            }
        }

        return null;
    }
}
