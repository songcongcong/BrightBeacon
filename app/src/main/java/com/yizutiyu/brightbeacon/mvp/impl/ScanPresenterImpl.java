package com.yizutiyu.brightbeacon.mvp.impl;

import android.content.Context;

import com.yizutiyu.brightbeacon.base.BasePresenterImpl;
import com.yizutiyu.brightbeacon.info.SuccessInfo;
import com.yizutiyu.brightbeacon.mvp.implinterface.ScanPresenter;
import com.yizutiyu.brightbeacon.mvp.model.ScanBiz;
import com.yizutiyu.brightbeacon.mvp.uiinterface.ScanUiInterface;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author
 * @data 2019/9/22
 */
public class ScanPresenterImpl extends BasePresenterImpl<ScanUiInterface> implements ScanPresenter {
    @Inject
    public ScanPresenterImpl() {
    }

    //注解M层
    @Inject
    ScanBiz biz;

    //初始化 UI层
    ScanUiInterface uiInterface;

    public void setUiInterface(ScanUiInterface uiInterface) {
        this.uiInterface = uiInterface;
    }

    private Disposable mDisposable;

    @Override
    public void addRegion(Context context, String key, String name) {
        biz.addRegiion(context, key, name)
                .subscribe(new Observer<SuccessInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(SuccessInfo addRegionInfo) {
                        uiInterface.addRegionSuccess(addRegionInfo.toString());
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
}
