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
    /**
     *
     * @param mainActivity mainActivity
     */
    void inject(MainActivity mainActivity);

    /**
     *
     * @param loginActivity loginActivity
     */
    void inject(LoginActivity loginActivity);

    /**
     *
     * @param patrolAreaActivity patrolAreaActivity
     */
    void inject(PatrolAreaActivity patrolAreaActivity);

    /**
     *
     * @param scanActivity scanActivity
     */
    void inject(ScanActivity scanActivity);

    /**
     *
     * @param regionErrorActivity regionErrorActivity
     */
    void inject(RegionErrorActivity regionErrorActivity);
}
