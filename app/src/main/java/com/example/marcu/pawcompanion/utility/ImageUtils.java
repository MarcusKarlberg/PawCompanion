package com.example.marcu.pawcompanion.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;

import static android.content.ContentValues.TAG;

public class ImageUtils {
    private Context context;

    public ImageUtils(Context context) {
        this.context = context;
    }

    public Bitmap getBitmap(Uri uri) {
        if (uri != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 2;

            try {
                Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
                return rotateBitmap(getImageRotation(context, uri), bitmap);
            }catch (FileNotFoundException e) {
                Log.d(TAG, "FileNotFoundException");
            }
        }

        return null;
    }

    private static Bitmap rotateBitmap(int orientation, Bitmap bitmap){
        Matrix matrix = new Matrix();

        switch (orientation){
            case 180:
                Log.d(TAG, "rotateBitmap: CASE 3");
                matrix.setRotate(180);
            break;

            case 90:
                Log.d(TAG, "rotateBitmap: CASE 6");
                matrix.setRotate(90);
            break;

            case 270:
                Log.d(TAG, "rotateBitmap: CASE 8");
                matrix.setRotate(-90);
            break;

            default:
                Log.d(TAG, "rotateBitmap: CASE DEFAULT orientation: " + orientation);
                return bitmap;
        }

        try {
            Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return oriented;
        } catch (OutOfMemoryError e){
            e.printStackTrace();
            return bitmap;
        }
    }

    private static int getImageRotation(Context context, Uri imageUri) {
        try {
            ExifInterface exif = new ExifInterface(imageUri.getPath());
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            if (rotation == ExifInterface.ORIENTATION_UNDEFINED)
                return getRotationFromMediaStore(context, imageUri);
            else return rotation;
        } catch (IOException e) {
            return 0;
        }
    }

    private static int getRotationFromMediaStore(Context context, Uri imageUri) {
        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.ORIENTATION};
        @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(imageUri, columns, null, null, null);
        if (cursor == null) return 0;
        cursor.moveToFirst();

        int orientationColumnIndex = cursor.getColumnIndex(columns[1]);
        return cursor.getInt(orientationColumnIndex);
    }
}
