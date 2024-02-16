package com.cocktail_app.apps.cocktailsfoodcreator.services;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "sharedPreferencesUser";
    private static final String KEY_ID = "ID_key";
    public static final String KEY_EMAIL = "email_key";
    private static SharedPrefManager instance;
    private static Context context;

    public SharedPrefManager(Context context){
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void setUserID(int id){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, id);
        editor.apply();
    }

    public int getUserID(){
        int id  = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getInt(KEY_ID, 0);
        return id;
    }

    public void setUserEmail(String email){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public String getUserEmail(){
        String email = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getString(KEY_EMAIL, "");
        return email;
    }

}
