package com.yizutiyu.brightbeacon.mvp.model;


import android.content.Context;

import com.example.retrofitmvplibrary.base.BaseResponse;
import com.example.retrofitmvplibrary.retrofit.RetrofitSource;
import com.example.retrofitmvplibrary.retrofit.RxHelper;
import com.yizutiyu.brightbeacon.api.RetrofitApi;
import com.yizutiyu.brightbeacon.info.LoginInfo;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * 作者：宋聪聪 on 2019/9/2.
 */

public class LogBiz {
    @Inject
    public LogBiz(){}

    // 登录网络请求
    public Observable<LoginInfo> normalLogin(String phone, String pwd, Context context){
        return RetrofitSource.createApi(RetrofitApi.class,context)
                .normalLogin(phone, pwd)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
