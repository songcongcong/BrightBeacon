package com.yizutiyu.brightbeacon.mvp.impl;

import android.content.Context;
import android.util.Log;

import com.yizutiyu.brightbeacon.base.BasePresenterImpl;
import com.yizutiyu.brightbeacon.info.PictureInfo;
import com.yizutiyu.brightbeacon.mvp.implinterface.RegionErrorPersenter;
import com.yizutiyu.brightbeacon.mvp.model.RegionErrorBiz;
import com.yizutiyu.brightbeacon.mvp.uiinterface.RegionErrorUiinterface;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author
 * @data 2019/9/22
 */
public class RegionErrorPersenterImpl extends BasePresenterImpl<RegionErrorUiinterface> implements RegionErrorPersenter {
    @Inject
    public RegionErrorPersenterImpl() {
    }
    @Inject
    RegionErrorBiz biz;

    public void setUiinterface(RegionErrorUiinterface uiinterface) {
        this.uiinterface = uiinterface;
    }

    private RegionErrorUiinterface uiinterface;

    private Disposable mDisposable;
    @Override
    public void updateImg(Context context,String filePath) {
//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM);//表单类型
        //   for (int i = 0; i < list.size(); i++) {
        File file = new File(filePath);
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
//        builder.addFormDataPart("file", file.getName(), photoRequestBody);
        //   }

        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);
//        List<MultipartBody.Part> parts = builder.build().parts();
        biz.updateImg(context, part)
                .subscribe(new Observer<PictureInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(PictureInfo pictureInfo) {
                        uiinterface.updataimgSuccess(pictureInfo);
                        Log.d("song","上传成功："+pictureInfo.toString());
                        mDisposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("song","上传失败："+e.toString());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
