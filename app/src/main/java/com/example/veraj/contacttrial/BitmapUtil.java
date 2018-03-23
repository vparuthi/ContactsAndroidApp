package com.example.veraj.contacttrial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Veraj on 2018-03-22.
 */

public class BitmapUtil {
    public static Bitmap getThumbnail(String filename, Context context) {
        Bitmap thumbnail = null;
        try {
            File filePath = context.getFileStreamPath(filename);
            FileInputStream fi = new FileInputStream(filePath);
            thumbnail = BitmapFactory.decodeStream(fi);
        } catch (Exception ex) {
            Log.e("Thumbnail on internal", ex.getMessage());
        }
        return thumbnail;
    }

    public static boolean saveImageToInternalStorage(Bitmap image, Contact contact, Context context) {

        try {
            // Use the compress method on the Bitmap object to write image to
            // the OutputStream
            FileOutputStream fos = context.openFileOutput("contact" + contact.getFirstName() + ".png" , Context.MODE_PRIVATE);
            // Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }
}
