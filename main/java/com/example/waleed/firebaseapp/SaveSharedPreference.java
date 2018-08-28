package com.example.waleed.firebaseapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * Created by Sara Alkurdy on 01-Mar-18.
 */

public class SaveSharedPreference{


    static final String NAME = "name";
    static final String EMAIL = "email";

    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }



    /**********  SAVE EMAIL OF USER IN SHARED PREFERENCES    **/
    public static void setName(Context context, String name)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(NAME, name);
        editor.commit();
    }

    public static String getName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(NAME, "");
    }


    /**********  SAVE EMAIL OF USER IN SHARED PREFERENCES    **/
    public static void setEmail(Context context, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(EMAIL, userName);
        editor.commit();
    }

    public static String getEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(EMAIL, "");
    }


}