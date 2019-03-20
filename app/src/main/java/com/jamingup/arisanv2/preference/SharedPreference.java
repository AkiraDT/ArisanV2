package com.jamingup.arisanv2.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPreference mInstance;
    private Context mCtx;

    private SharedPreference(Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized SharedPreference getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPreference(mCtx);
        }
        return mInstance;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userid", -1) != -1;
    }

    public void incrementBoyIndex(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int tempIndex = sharedPreferences.getInt("boyIndex", 0);
        if(tempIndex == 3){
            tempIndex = 0;
        }else {
            tempIndex++;
        }
        editor.putInt("boyIndex", tempIndex);
        editor.apply();
    }

    public int getBoyIndex(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("boyIndex", 0);
    }

    public void incrementGirlIndex(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int tempIndex = sharedPreferences.getInt("girlIndex", 0);
        if(tempIndex == 3){
            tempIndex = 0;
        }else {
            tempIndex++;
        }
        editor.putInt("girlIndex", tempIndex);
        editor.apply();
    }

    public int getGirlIndex(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("girlIndex", 0);
    }

    public void incrementKelompokIndex(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int tempIndex = sharedPreferences.getInt("kelompokIndex", 0);
        if(tempIndex == 1){
            tempIndex = 0;
        }else {
            tempIndex++;
        }
        editor.putInt("kelompokIndex", tempIndex);
        editor.apply();
    }

    public int getKelompokIndex(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("kelompokIndex", 0);
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
