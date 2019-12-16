package com.example.retrofitmvplibrary.retrofit;

import android.text.TextUtils;
import android.util.Log;

import com.example.retrofitmvplibrary.base.BaseResponse;
import com.example.retrofitmvplibrary.base.BaseUiInterface;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Description:
 * Created by RockPhoenixHIAPAD on 2017/11/28 on 10:11.
 */

public abstract class BaseObserver<E extends BaseResponse> implements Observer<E> {

    protected final String LOG_TAG = getClass().getSimpleName();

    private final BaseUiInterface mUiInterface;
    protected Disposable mDisposable;

    public BaseObserver(BaseUiInterface baseUiInterface) {
        mUiInterface = baseUiInterface;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onComplete() {
        if (mUiInterface != null)
            mUiInterface.dismissLoadingDialog();
        mDisposable.dispose();
    }

    @Override
    public void onError(Throwable throwable) {
        mDisposable.dispose();
        if (mUiInterface != null)
            mUiInterface.dismissLoadingDialog();
        Log.d("BaseObserver", "Request Error!", throwable);
        handleError(throwable, mUiInterface, LOG_TAG);
    }

    /**
     * 按照通用规则解析和处理数据请求时发生的错误。这个方法在执行支付等非标准的REST请求时很有用。
     */
    public static void handleError(Throwable throwable, BaseUiInterface mUiInterface, String LOG_TAG) {
        if (throwable == null) {
            mUiInterface.showUnknownException();
            return;
        }
        //分为以下几类问题：网络连接，数据解析，客户端出错【空指针等】，服务器内部错误
        if (throwable instanceof SocketTimeoutException || throwable
                instanceof ConnectException || throwable instanceof UnknownHostException) {
            mUiInterface.showNetworkException();
        } else if ((throwable instanceof JsonSyntaxException) || (throwable instanceof
                NumberFormatException) || (throwable instanceof MalformedJsonException)) {
            mUiInterface.showDataException("数据解析出错");
        } else if ((throwable instanceof HttpException)) {
            mUiInterface.showDataException("服务器错误(" + ((HttpException) throwable).code() + ")");
            //自动上报这个异常
//            L.e(true, LOG_TAG, "Error while performing response!", throwable);
        } else if (throwable instanceof NullPointerException) {
            mUiInterface.showDataException("客户端开小差了，攻城狮正在修复中...");
            //自动上报这个异常
//            L.e(true, LOG_TAG, "Error while performing response!", throwable);
        } else {
//            mUiInterface.showUnknownException();
        }
    }

    @Override
    public void onNext(E response) {
        if (mUiInterface != null)
            mUiInterface.dismissLoadingDialog();
        switch (response.getCode()) {
            case BaseResponse.RESULT_CODE_SUCCESS:
                onSuccess(response);
                break;
//            case BaseResponse.RESULT_CODE_TOKEN_EXPIRED:
//                if (mUiInterface instanceof LoginActivity || mUiInterface instanceof SplashActivity){
//                    onDataFailure(response);
//                }
//                else if (mUiInterface instanceof BaseActivity || mUiInterface instanceof BaseFragment){
//                    final BaseActivity activity;
//                    if (mUiInterface instanceof BaseFragment){
//                        activity = (BaseActivity) ((BaseFragment)mUiInterface).getActivity();
//                    }
//                    else{
//                        activity = (BaseActivity)mUiInterface;
//                    }
//                    AlertDialog alertDialog = new AlertDialog.Builder(activity)
//                            .setMessage("你的账号在别处登录，请重新登录")
//                            .setPositiveButton("好", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    activity.startActivity(LoginSelectActivity.createIntent(activity));
//                                    activity.sendFinishBroadcast(LoginSelectActivity.class.getSimpleName());
//                                }
//                            })
//                            .create();
//                    alertDialog.show();
//                }
//                else{
//                    onDataFailure(response);
//                }
//                break;
            default:
                onDataFailure(response);
        }
    }

    public abstract void onSuccess(E response);

    protected void onDataFailure(E response) {
        String msg = response.getMessage();
        Log.d(LOG_TAG, "request data but get failure:" + msg);
        if (!TextUtils.isEmpty(msg)) {
            mUiInterface.showDataException(response.getMessage());
        } else {
            mUiInterface.showUnknownException();
        }
    }

    /**
     * Create a new silence, non-leak observer.
     */
    public static <T> Observer<T> silenceObserver() {
        return new Observer<T>() {

            @Override
            public void onError(Throwable e) {
                //Empty
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(T t) {
                //Empty
            }
        };
    }

}
