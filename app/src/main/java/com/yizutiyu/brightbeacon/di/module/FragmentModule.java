package com.yizutiyu.brightbeacon.di.module;


import androidx.fragment.app.Fragment;

import dagger.Module;
import dagger.Provides;

/**
 * 作者：宋聪聪 on 2019/6/18.
 */
@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule (Fragment fragment) {
        this.mFragment =fragment;
    }
    @Provides
    public Fragment provideFragment() {
        return mFragment;
    }

}
