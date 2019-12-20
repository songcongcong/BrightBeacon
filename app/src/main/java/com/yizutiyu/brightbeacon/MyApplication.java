package com.yizutiyu.brightbeacon;

import android.app.Application;
import android.os.Handler;

import androidx.multidex.MultiDex;

import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.utils.L;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.yizutiyu.brightbeacon.di.compontent.DaggerApplicationCompontent;
import com.yizutiyu.brightbeacon.di.module.ApplicationModule;
import com.yizutiyu.brightbeacon.sqlite.DatabaseUtils;

/**
 * MyApplication
 */
public class MyApplication extends Application {
    /**
     * instance
     */
    private static MyApplication instance;
    /**
     * handler
     */
    protected static Handler handler;
    /**
     * mainThreadId
     */
    protected static int mainThreadId;
    /**
     * beaconManager
     */
    private BRTBeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();
        //解决65535
        MultiDex.install(this);
        instance = this;
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();
        //初始化dagger2
        initApplicationCompontent();
        Fresco.initialize(this);
        // 开启log打印
        L.enableDebugLogging(true);
        // 单例
        beaconManager = BRTBeaconManager.getInstance(this);
        // 注册应用 APPKEY申请地址 http://brtbeacon.com/main/index.shtml   "09ac5cf5145a493c8945454395c4c347"
//        beaconManager.registerApp("00000000000000000000000000000000");
//        beaconManager.startService();
        DatabaseUtils.initHelper(this, "brt");

    }

    /**
     * initApplicationCompontent
     */
    private void initApplicationCompontent() {
        DaggerApplicationCompontent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }

    /**
     * instance
     * @return MyApplication
     */
    public static MyApplication instance() {
        return instance;
    }
}
