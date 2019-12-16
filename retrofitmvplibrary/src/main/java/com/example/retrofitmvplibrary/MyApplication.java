package com.example.retrofitmvplibrary;

import android.app.Application;

/**
 * @author
 * @data 2019/9/2
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static MyApplication instance() {
        return instance;
    }
}
