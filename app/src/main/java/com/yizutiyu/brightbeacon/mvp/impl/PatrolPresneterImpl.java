package com.yizutiyu.brightbeacon.mvp.impl;

import android.content.Context;
import android.util.Log;

import com.example.retrofitmvplibrary.base.BaseResponse;
import com.example.retrofitmvplibrary.retrofit.BaseObserver;
import com.yizutiyu.brightbeacon.base.BasePresenterImpl;
import com.yizutiyu.brightbeacon.info.FaceDateMessageInfo;
import com.yizutiyu.brightbeacon.info.RegionListInfo;
import com.yizutiyu.brightbeacon.info.SuccessInfo;
import com.yizutiyu.brightbeacon.mvp.implinterface.PatrolPresenter;
import com.yizutiyu.brightbeacon.mvp.model.PatrolBiz;
import com.yizutiyu.brightbeacon.mvp.uiinterface.PatrolUiInterface;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author
 * @data 2019/9/20
 */
public class PatrolPresneterImpl extends BasePresenterImpl<PatrolUiInterface> implements PatrolPresenter {
    /**
     * 获取Biz层的对象
     */
    @Inject
    PatrolBiz biz;

    /**
     * 初始化p层的方法
     */
    @Inject
    public PatrolPresneterImpl() {
    }

    /**
     * 定义Ui层对象
     */
    PatrolUiInterface uiInterface;

    /**
     * setUiInterface
     * @param uiInterface uiInterface
     */
    public void setUiInterface(PatrolUiInterface uiInterface) {
        this.uiInterface = uiInterface;
    }

    /**
     * mDisposable
     */
    private Disposable mDisposable;

    @Override
    public void saveBltoothMessageSucces(String key, String name, Context context) {
        biz.saveBluetoothMessage(key, name, context)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(String s) {
                        mDisposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getRegionListSuccess(Context context) {
        biz.getRegionList(context)
                .subscribe(new BaseObserver<BaseResponse<List<RegionListInfo>>>(uiInterface) {
                               @Override
                               public void onSuccess(BaseResponse<List<RegionListInfo>> response) {
                                   uiInterface.getRegionListSuccess(response.getData());
                               }
                           }
                );
    }

    @Override
    public void submitSuccess(Context context, String message, String startTiem, String endTime,
                              String state, String detail) {
        biz.submitResult(context, message, startTiem, endTime, state, detail)
                .subscribe(new Observer<SuccessInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(SuccessInfo successInfo) {
                        uiInterface.submitResuleSuccess(successInfo);
                        mDisposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("song", "提交异常：" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getFaceDate(Context context, String face, int position) {
        biz.getFaceDate(context, face)
                .subscribe(new Observer<FaceDateMessageInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(FaceDateMessageInfo faceDateMessageInfo) {
                        uiInterface.getFaceData(faceDateMessageInfo, position);
                        Log.d("song", "人脸识别成功：" + faceDateMessageInfo.isIs_exist());
                        mDisposable.dispose();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("song", "人脸识别异常：" + e.toString());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
