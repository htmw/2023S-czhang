package com.example.gsorting.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtil {

    public static byte[] UriToByte(Context context, Uri uri){
        Bitmap bitmap1 = null;
        try {
            bitmap1 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int size = bitmap1.getWidth() * bitmap1.getHeight() * 4;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imagedata1 = baos.toByteArray();
        return  imagedata1;
    }


    public static Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }
}
