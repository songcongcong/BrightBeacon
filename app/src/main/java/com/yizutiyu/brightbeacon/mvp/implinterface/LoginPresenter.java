package com.yizutiyu.brightbeacon.mvp.implinterface;

import android.content.Context;

/**
 * 作者：宋聪聪 on 2019/6/19.
 */

public interface LoginPresenter {
    /**
     * normalLogin
     * @param phone phone
     * @param pwd pwd
     * @param context context
     */
    void normalLogin(String phone, String pwd, Context context);
}
