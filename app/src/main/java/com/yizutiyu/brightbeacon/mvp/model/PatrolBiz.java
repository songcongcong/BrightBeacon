package com.yizutiyu.brightbeacon.mvp.model;


import android.content.Context;

import com.example.retrofitmvplibrary.base.BaseResponse;
import com.example.retrofitmvplibrary.retrofit.RetrofitSource;
import com.example.retrofitmvplibrary.retrofit.RxHelper;
import com.yizutiyu.brightbeacon.api.RetrofitApi;
import com.yizutiyu.brightbeacon.info.FaceDateMessageInfo;
import com.yizutiyu.brightbeacon.info.RegionListInfo;
import com.yizutiyu.brightbeacon.info.SuccessInfo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author
 * @data 2019/9/20
 */
public class PatrolBiz {
    // 巡检页面请求数据
    @Inject
    public PatrolBiz() {
    }

    // 将扫描到的地址上传给后台
    public Observable<String> saveBluetoothMessage(String key, String regionName, Context context) {
        return RetrofitSource.createApi(RetrofitApi.class, context)
                .saveBluetoothMessage(key, regionName)
                .compose(RxHelper.rxSchedulerHelper());
    }

    // 请求区域数据
    public Observable<BaseResponse<List<RegionListInfo>>> getRegionList(Context context) {
        return RetrofitSource.createApi(RetrofitApi.class, context)
                .getRegionList("")
                .compose(RxHelper.rxSchedulerHelper());
    }

    // 提交巡检结果
    public Observable<SuccessInfo> submitResult(Context context, String message, String startTiem, String endTime, String state,
                                                String detail) {
        return RetrofitSource.createApi(RetrofitApi.class, context)
                .submitResult(message, startTiem, endTime, state, detail)
                .compose(RxHelper.rxSchedulerHelper());
    }

    // 查询人脸信息
    public Observable<FaceDateMessageInfo> getFaceDate(Context context, String face) {
        return RetrofitSource.createApi(RetrofitApi.class, context)
                .getFaceData(face)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
