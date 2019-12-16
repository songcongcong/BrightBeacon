package com.yizutiyu.brightbeacon.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.AnimRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.example.retrofitmvplibrary.base.BaseUiInterface;
import com.example.retrofitmvplibrary.utils.CustomToast;
import com.example.retrofitmvplibrary.utils.StatusBarCompat;
import com.example.retrofitmvplibrary.utils.TimeUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.di.compontent.DaggerFragmentCompontent;
import com.yizutiyu.brightbeacon.di.compontent.FragmentCompontent;
import com.yizutiyu.brightbeacon.di.module.FragmentModule;
import com.yizutiyu.brightbeacon.utils.ToastUtils;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Description:
 * Created by RockPhoenixHIAPAD on 2017/12/22 on 17:32.
 */
@SuppressLint("CheckResult")
public abstract class BaseMvpFragment<T extends BasePresenterImpl> extends BaseFragment implements BaseUiInterface {

    protected float mDensity;
    protected int mDensityDpi;
    protected int mWidth;
    protected ViewGroup barLayout = null;
    private Unbinder unbinder;
    private ProgressDialog mProgressDialog;

    protected T impl;
    private FragmentCompontent component;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mWidth = dm.widthPixels;

        //初始化dagger容器及绑定presenter
        initActivityComponent();
        injectPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        Log.d(LOG_TAG, "----- onCreateView ----- Bundle=" + savedInstanceState);
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews(view);
        setListeners();
        return view;
    }

    private void injectPresenter() {
        impl = initInjector();
        impl.attachView(this);
    }

    private void initActivityComponent() {
        component = DaggerFragmentCompontent.builder()
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    protected abstract T initInjector();

    public void setStatusBarColor() {
        StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.white));
    }


    /**
     * Returns the view avtivity_group_details which should be inflated on creating view.
     */
    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initViews(View view);

    /**
     * Convenient call of {@link View#findViewById(int)}, automatically cast the result object.
     *
     * @param view The view object which contains target object.
     * @param id   The aapt-generated unique id.
     * @param <T>  The declared type of this widget.
     * @return The view object, or null if not found.
     */
    @SuppressWarnings("unchecked")
    protected final <T extends View> T $(@NonNull View view, @IdRes int id) {
        return (T) (view.findViewById(id));
    }


    protected void toastShort(@StringRes int msg) {
        if (getActivity() != null) {
            CustomToast.makeCustomText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    protected void toastShort(@NonNull String msg) {
        if (getActivity() != null) {
            CustomToast.makeCustomText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Only for development usage!
     */
    protected void toastForNotImplementedFeature() {
        toastShort(R.string.commen_notmake);
    }

    /**
     * @see BaseMvpActivity#getTransitionEnterAnim()
     */
    @AnimRes
    protected int getTransitionEnterAnim() {
        return R.anim.fragment_slide_left_in;
    }

    /**
     * @see BaseMvpActivity#getTransitionOutAnim()
     */
    @AnimRes
    protected int getTransitionOutAnim() {
        return R.anim.fragment_slide_left_out;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(getTransitionEnterAnim(), getTransitionOutAnim());
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(getTransitionEnterAnim(), getTransitionOutAnim());
    }

    protected void subscribeFeatureStub(@NonNull View view) {
        subscribeClick(view, new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                toastForNotImplementedFeature();
            }
        });
    }

    /**
     * 使用默认的throttle设置来注册点击事件。
     *
     * @param view     要注册的View
     * @param consumer 点击后执行的事件
     */
    protected void subscribeClick(View view, Consumer<Object> consumer) {
        RxView.clicks(view)
                .throttleFirst(TimeUtils.VIEW_THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribe(consumer);
    }

    /**
     * 注册点击事件，不允许throttle。
     *
     * @param view     要注册的View
     * @param consumer 点击后执行的事件
     */
    protected void subscribeClickWithoutThrottle(View view, Consumer<Object> consumer) {
        RxView.clicks(view)
                .subscribe(consumer);
    }

    protected int getWidth(Context context) {

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    protected void setListeners() {
        //empty implementation
    }

    @Override
    public void showDataException(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void showNetworkException() {
        ToastUtils.showToast(R.string.msg_network_error);
    }

    @Override
    public void showUnknownException() {
        ToastUtils.showToast(R.string.msg_unknown_error);
    }

    @Override
    public void showLoadingComplete() {
        //Empty implementation
    }

    public Dialog showLoadingDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            Log.d(LOG_TAG, "Call show loading dialog while dialog is still showing, is there a bug?");
            mProgressDialog.dismiss();
        }
        mProgressDialog = ProgressDialog.show(getActivity(), null, "请稍候", true, false);
        return mProgressDialog;
    }

    @Override
    public void dismissLoadingDialog() {
        if (mProgressDialog == null || (!mProgressDialog.isShowing())) {
            Log.d(LOG_TAG, "Try to dismiss a dialog but dialog is null or already dismiss!");
            return;
        }
        mProgressDialog.dismiss();
        mProgressDialog = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
       Log.d(LOG_TAG, "-----base onHiddenChanged -----" + hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * 获取状态栏的高度
     *
     * @return 状态栏的高度
     */
    protected int getStatusBarHeight() {
        try {
            //通过反射获取到类
            @SuppressLint("PrivateApi") Class<?> aClass = Class.forName("com.android.internal.R$dimen");
            //创建对象
            Object o = aClass.newInstance();
            //拿取属性
            Field status_bar_height = aClass.getField("status_bar_height");
            //获取值
            Object o1 = status_bar_height.get(o);
            int height = Integer.parseInt(o1.toString());
            return getResources().getDimensionPixelOffset(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
