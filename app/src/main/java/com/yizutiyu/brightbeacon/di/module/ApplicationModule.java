package com.yizutiyu.brightbeacon.di.module;

import android.content.Context;


import com.yizutiyu.brightbeacon.MyApplication;
import com.yizutiyu.brightbeacon.di.PerApp.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * 作者：宋聪聪 on 2019/6/18.
 */
@Module  //提供依赖对象的实例 （module是个容器）
public class ApplicationModule {
    private MyApplication myApplication;
    //构造方法
    public ApplicationModule(MyApplication application) {
        this.myApplication = application;
    }
    @Provides //关键字，标明该方法提供依赖对象
    @PerApp
    public Context provideApplicatinContext(){
        return myApplication.getApplicationContext();
    }

}
