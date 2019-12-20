package com.yizutiyu.brightbeacon.mvp.implinterface;

import android.content.Context;


/**
 * @author
 * @data 2019/9/22
 */
public interface RegionErrorPersenter {
    /**
     * updateImg
     * @param context context
     * @param file file
     */
    void updateImg(Context context, String file);

    /**
     * uploadVideo
     * @param context context
     * @param file file
     */
    void uploadVideo(Context context, String file);
}
