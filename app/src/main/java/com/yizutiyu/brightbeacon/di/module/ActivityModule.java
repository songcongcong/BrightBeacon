package com.yizutiyu.brightbeacon.di.module;


import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;

/**
 * 作者：宋聪聪 on 2019/6/18.
 */
@Module
public class ActivityModule {
    /**
     * mActivity
     */
    private AppCompatActivity mActivity;

    /**
     * ActivityModule
     * @param activity activity
     */
    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    /**
     * provideActivity
     * @return AppCompatActivity
     */
    @Provides
    public AppCompatActivity provideActivity() {
        return mActivity;
    }

}
