package com.yizutiyu.brightbeacon.di.compontent;


import com.yizutiyu.brightbeacon.activity.PatrolAreaActivity;
import com.yizutiyu.brightbeacon.mvp.ui.LoginActivity;
import com.yizutiyu.brightbeacon.activity.MainActivity;
import com.yizutiyu.brightbeacon.di.module.ActivityModule;
import com.yizutiyu.brightbeacon.mvp.ui.RegionErrorActivity;
import com.yizutiyu.brightbeacon.mvp.ui.ScanActivity;

import dagger.Component;

/**
 * 作者：宋聪聪 on 2019/6/18.
 */
@Component (modules = ActivityModule.class)
public interface ActivityCompontent {
    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity);
    void inject(PatrolAreaActivity patrolAreaActivity);
    void inject(ScanActivity scanActivity);
    void inject(RegionErrorActivity regionErrorActivity);
}
