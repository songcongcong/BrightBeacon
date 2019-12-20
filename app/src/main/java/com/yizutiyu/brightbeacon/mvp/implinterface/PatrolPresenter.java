package com.yizutiyu.brightbeacon.mvp.implinterface;

import android.content.Context;

/**
 * @author
 * @data 2019/9/20
 */
public interface PatrolPresenter {
    /**
     * saveBltoothMessageSucces
     * @param key key
     * @param name name
     * @param context context
     */
    void saveBltoothMessageSucces(String key, String name,  Context context);

    /**
     * getRegionListSuccess
     * @param context context
     */
    void getRegionListSuccess(Context context);

    /**
     * submitSuccess
     * @param context context
     * @param message message
     * @param startTiem startTiem
     * @param endTime endTime
     * @param state state
     * @param detail detail
     */
    void submitSuccess(Context context, String message, String startTiem, String endTime, String state,
                              String detail);

    /**
     * getFaceDate
     * @param context context
     * @param face face
     * @param position position
     */
    void getFaceDate(Context context, String face, int position);
}
