package com.yizutiyu.brightbeacon.mvp.implinterface;

import android.content.Context;

/**
 * @author
 * @data 2019/9/22
 */
public interface ScanPresenter {
    /**
     * addRegion
     * @param context context
     * @param key key
     * @param name name
     */
    void addRegion(Context context, String key, String name);
}
