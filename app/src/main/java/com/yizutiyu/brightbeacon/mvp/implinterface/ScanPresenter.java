package com.yizutiyu.brightbeacon.mvp.implinterface;

import android.content.Context;

/**
 * @author
 * @data 2019/9/22
 */
public interface ScanPresenter {
    void addRegion(Context context, String key, String name);
}
