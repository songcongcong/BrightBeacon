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
    /**
     * ScanBiz
     */
    @Inject
    public ScanBiz() {
    }

    /**
     * addRegiion
     * @param context context
     * @param key key
     * @param name name
     * @return SuccessInfo
     */
    public Observable<SuccessInfo> addRegiion(Context context , String key, String name) {
        return RetrofitSource.createApi(RetrofitApi.class, context)
                .addRegion(key, name)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
