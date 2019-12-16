package com.yizutiyu.brightbeacon.mvp.implinterface;

import android.content.Context;

/**
 * @author
 * @data 2019/9/20
 */
public interface PatrolPresenter {
    void saveBltoothMessageSucces(String key, String name,  Context context);
    void getRegionListSuccess(Context context);
    void submitSuccess(Context context, String message, String startTiem, String endTime, String state,
                              String detail);
    void getFaceDate(Context context, String face, int position);
}
