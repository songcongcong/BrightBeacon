package com.yizutiyu.brightbeacon.mvp.implinterface;

import android.content.Context;

import okhttp3.MultipartBody;

/**
 * @author
 * @data 2019/9/22
 */
public interface RegionErrorPersenter {
    void updateImg(Context context, String file);
    void uploadVideo(Context context, String file);
}
