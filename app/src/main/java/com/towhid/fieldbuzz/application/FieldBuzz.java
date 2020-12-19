package com.towhid.fieldbuzz.application;

import android.app.Application;
import android.content.Context;

public class FieldBuzz extends Application {

    private static Context instance;

    private static  Application mApplication;

    public static Context getInstance() {
        if (instance != null) {
            return instance;
        }
        return null;
    }

    public static Application getApplication(){
        if(mApplication!=null){
            return mApplication;
        }
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = getApplicationContext();
        mApplication = getApplication();
    }

}
