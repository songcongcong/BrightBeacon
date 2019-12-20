package com.yizutiyu.brightbeacon.mvp.model;

import android.content.Context;

import com.example.retrofitmvplibrary.retrofit.RetrofitSource;
import com.example.retrofitmvplibrary.retrofit.RxHelper;
import com.yizutiyu.brightbeacon.api.RetrofitApi;
import com.yizutiyu.brightbeacon.info.PictureInfo;
import com.yizutiyu.brightbeacon.info.VideoInfo;


import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * @author
 * @data 2019/9/22
 */
public class RegionErrorBiz {
    /**
     * RegionErrorBiz
     */
    @Inject
    public RegionErrorBiz() {
    }

    /**
     * 上传图片
     * @param context context
     * @param file file
     * @return PictureInfo
     */
    public Observable<PictureInfo> updateImg(Context context, MultipartBody.Part file) {
        return RetrofitSource.createApi(RetrofitApi.class, context)
                .uploadImg(file)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 上传录制视频
     * @param context context
     * @param file file
     * @return VideoInfo
     */
    public Observable<VideoInfo> uploadVideo(Context context, MultipartBody.Part file) {
        return RetrofitSource.createApi(RetrofitApi.class, context)
                .uploadVideo(file)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
