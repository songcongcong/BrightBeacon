package com.yizutiyu.brightbeacon.mvp.model;

import android.content.Context;

import com.example.retrofitmvplibrary.retrofit.RetrofitSource;
import com.example.retrofitmvplibrary.retrofit.RxHelper;
import com.yizutiyu.brightbeacon.api.RetrofitApi;
import com.yizutiyu.brightbeacon.info.SuccessInfo;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author
 * @data 2019/9/22
 */
public class ScanBiz {
    @Inject
    public ScanBiz() {
    }

    public Observable<SuccessInfo> addRegiion(Context context , String key, String name) {
        return RetrofitSource.createApi(RetrofitApi.class, context)
                .addRegion(key, name)
                .compose(RxHelper.rxSchedulerHelper());
    }
}