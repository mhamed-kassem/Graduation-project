package com.example.waleed.firebaseapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sara Alkurdy on 2/26/2018.
 */

public class PreferenceImage {
    private Context context;
    private SharedPreferences preferences;

    public PreferenceImage(Context context) {
        this.context = context;
    }


    public void Show_Saved_Image(Context context, CircleImageView profile_photo) {
        //Show saved image when app is open
        preferences = context.getSharedPreferences("myprefs", 0);
        String img_str = preferences.getString("userphoto", BuildConfig.FLAVOR);
        if (!img_str.equals(BuildConfig.FLAVOR)) {
            byte[] imageAsBytes = Base64.decode(img_str.getBytes(), 0);
             profile_photo.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            // profile_photo.setBackground(new BitmapDrawable(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)));
        }
    }


    public void AddPhoto(Context context) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
        } else {
            Toast.makeText(context, "Please it is not allowed here", Toast.LENGTH_SHORT).show();
        }
    }


    public void DeletePhoto(Context context, CircleImageView profile_photo) {
        SharedPreferences preferences = context.getSharedPreferences("myprefs", 0);
        if (preferences.contains("userphoto")) {
            SharedPreferences.Editor editor = preferences.edit();
            profile_photo.setImageResource(0);
            profile_photo.setBackgroundResource(0);
            editor.remove("userphoto");
            editor.commit();
        }
    }


    public void Save_Image_In_Preferences(Context context, CircleImageView profile_image, Intent data) {
        try {
            //profile_image.setBackground(new BitmapDrawable(context.getResources(), MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData())));
            profile_image.setImageDrawable(new BitmapDrawable(context.getResources(), MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData())));
            profile_image.buildDrawingCache();
            Bitmap bitmap = profile_image.getDrawingCache();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            String img_str = Base64.encodeToString(stream.toByteArray(), 0);
            String base = img_str;
            SharedPreferences.Editor editor = context.getSharedPreferences("myprefs", 0).edit();
            editor.putString("userphoto", img_str);
            editor.commit();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

    }
}
