package com.yizutiyu.brightbeacon.mvp.impl;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yizutiyu.brightbeacon.base.BasePresenterImpl;
import com.yizutiyu.brightbeacon.info.LoginInfo;
import com.yizutiyu.brightbeacon.mvp.implinterface.LoginPresenter;
import com.yizutiyu.brightbeacon.mvp.model.LogBiz;
import com.yizutiyu.brightbeacon.mvp.uiinterface.LoginUiInterface;


import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者：宋聪聪 on 2019/6/19.
 */

public class LoginPresenterImpl extends BasePresenterImpl<LoginUiInterface> implements LoginPresenter {
    /**
     * 获取model层对象biz
     */
    @Inject
    public LogBiz logBiz;
    /**
     * loginUiInterface
     */
    private LoginUiInterface loginUiInterface;
    /**
     * mDisposable
     */
    private Disposable mDisposable;

    /**
     * 构造方法，初始化
     */
    @Inject
    public LoginPresenterImpl() {
    }


    /**
     * 设置view层对象
     * @param loginUiInterface loginUiInterface
     */
    public void setLoginUiInterface(LoginUiInterface loginUiInterface) {
        this.loginUiInterface = loginUiInterface;
    }

    @Override
    public void normalLogin(String phone, String pwd, Context context) {
        //调用model层数据
        logBiz.normalLogin(phone, pwd, context)
                .subscribe(new Observer<LoginInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(LoginInfo loginInfo) {
                        loginUiInterface.NormalLoginSucces(loginInfo);
                        mDisposable.dispose();
                        Log.d("song", "请求成功：");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("song", "请求失败：" + e.toString());

                        if (e.toString().contains("com.google.gson.JsonSyntaxException")) {
                            Toast.makeText(context, "用户名或密码不正确！", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "网络连接失败！", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
