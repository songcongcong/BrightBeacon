package com.yizutiyu.brightbeacon.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.AnimRes;
import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;


import com.example.retrofitmvplibrary.base.BaseUiInterface;
import com.example.retrofitmvplibrary.utils.ActivityContainer;
import com.example.retrofitmvplibrary.utils.TimeUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.di.compontent.ActivityCompontent;
import com.yizutiyu.brightbeacon.di.compontent.DaggerActivityCompontent;
import com.yizutiyu.brightbeacon.di.module.ActivityModule;
import com.yizutiyu.brightbeacon.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;


/**
 * Description:This is a fast-dev and common base activity.
 * Created by RockPhoenix on 2017/12/22 on 17:58.
 */
@SuppressWarnings("unused")
@SuppressLint("CheckResult")
public abstract class BaseMvpActivity<T extends BasePresenterImpl> extends BaseActivity implements BaseUiInterface {

    /**
     * Using activity class name as the log tag.
     */
    protected final String LOG_TAG = getClass().getSimpleName();
    protected ViewGroup bar_layout = null;
    private ProgressDialog mProgressDialog;
    private int enterAnimation = R.anim.fragment_slide_left_in;
    private int outAnimation = R.anim.fragment_slide_left_out;
    private Unbinder unbinder;
    protected T impl;
    //是否在前台显示
    public static boolean isForeground = false;
    public ActivityCompontent component;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "----- onCreate ----- Bundle=" + savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        parseIntentData(getIntent(), false);
        //向activity容器中添加
        ActivityContainer.addActivity(this);

        //初始化dagger容器及绑定presenter
        initActivityComponent();
        injectPresenter();

        //调用子类的各项初始化操作
        findViews(savedInstanceState);
        setListeners();
        init();
        setStatusBar();
    }

    //初始化Presenter
    private void injectPresenter() {
        impl = initInjector();
        impl.attachView(this);
    }

    //初始化Compontent
    private void initActivityComponent() {
        component = DaggerActivityCompontent.builder()
                .activityModule(new ActivityModule(this))
                .build();
    }


    protected abstract T initInjector();

    //设置状态栏
    public void setStatusBar() {
        //使状态栏透明并使布局向上填充
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
//
//        bar_layout = findViewById(R.id.bar_layout);
//        //设置沉浸式状态栏背景
//        if (bar_layout != null) {
//            bar_layout.setBackgroundResource(R.drawable.shape_gradient_bg);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                final int statusBarHeight = getStatusBarHeight();
//                bar_layout.post(() -> {
//                    //标题栏高度
//                    int height = bar_layout.getHeight();
//                    android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) bar_layout.getLayoutParams();
//                    //导航栏高度+通知栏高度
//                    params.height = height + statusBarHeight;
//                    bar_layout.setLayoutParams(params);
//                });
//            }
//        }
    }

    @CallSuper
    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "----- onResume -----");
        isForeground = true;
        super.onResume();
    }

    @CallSuper
    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "----- onPause -----");
        isForeground = false;
        super.onPause();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "----- onDestroy -----");
        impl.detachView();
        if (unbinder != null)
            unbinder.unbind();
        ActivityContainer.finishSingleActivity(this);
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    @CallSuper
    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(LOG_TAG, "----- onNewIntent -----");
        super.onNewIntent(intent);
        //refresh intent data!
        parseIntentData(intent, true);
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "----- onSaveInstanceState -----");
        /* super.onSaveInstanceState(outState);*/
        //为了解决activity被系统销毁后，fragment重叠的问题
        return;
    }

    @CallSuper
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "----- onRestoreInstanceState -----");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @CallSuper
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "--- onActivityResult --- requestCode=" + requestCode + ",resultCode=" +
                resultCode + ", data=" + data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * Specify the avtivity_group_details file this activity should display by calling {@link #setContentView(int)}.
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * Core method: retrieve views from {@link #getLayoutId()}.
     * Also all set listener code can put here.
     * <p>Tips: if too much listeners need to be set, consider override method {@link
     * #setListeners()} .</p>
     *
     * @param savedInstanceState see {@link #onCreate(Bundle)}
     */
    protected abstract void findViews(Bundle savedInstanceState);

    /**
     * Set listeners of retrieved widgets.
     * Note: this method always be called after {@link #findViews(Bundle)}.
     */
    protected void setListeners() {
        //empty implementation
    }

    /**
     * Parse and process the extra data from the intent.
     * <p>Note: this method will be called before {@link #findViews(Bundle)}!</p>
     *
     * @param intent          The intent which start this activity.
     * @param isFromNewIntent Indicates whether this method is called by {@link #onNewIntent
     *                        (Intent)}.
     */
    protected void parseIntentData(Intent intent, boolean isFromNewIntent) {
        //empty implementation
    }

    /**
     * Init presenters, data, etc.
     */
    protected abstract void init();

    protected void setEnterAnimation(int animation) {
        this.enterAnimation = animation;
    }

    protected void setOutAnimation(int animation) {
        this.outAnimation = animation;
    }

    @AnimRes
    protected int getTransitionEnterAnim() {
        return enterAnimation;
    }

    @AnimRes
    protected int getTransitionOutAnim() {
        return outAnimation;
    }

    @Override
    public void showDataException(String msg) {
        toastShort(msg);
    }

    @Override
    public void showNetworkException() {
        toastShort(R.string.msg_network_error);
    }

    @Override
    public void showUnknownException() {
        toastShort(R.string.msg_unknown_error);
    }

    @Override
    public void showLoadingComplete() {
        //Empty implementation
    }

    @Override
    public Dialog showLoadingDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            Log.d(LOG_TAG, "Call show loading dialog while dialog is still showing, is there a bug?");
            mProgressDialog.dismiss();
        }
        mProgressDialog = ProgressDialog.show(this, null, getString(R.string.loading), true, true);
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

    /**
     * Convenient call of {@link #findViewById(int)}, automatically cast the result object.
     *
     * @param id  The aapt-generated unique id.
     * @param <T> The declared type of this widget.
     * @return The view object, or null if not found.
     */
    @SuppressWarnings("unchecked")
    protected final <T extends View> T $(@IdRes int id) {
        return (T) (findViewById(id));
    }

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
        ToastUtils.showToast(msg);
    }

    protected void toastShort(@NonNull String msg) {
        ToastUtils.showToast(msg);
    }

    /**
     * Only for development usage!
     */
    protected void toastForNotImplementedFeature() {
        toastShort(R.string.commen_notmake);
    }

    @CallSuper
    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    @CallSuper
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(getTransitionEnterAnim(), getTransitionOutAnim());
    }

    @CallSuper
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(getTransitionEnterAnim(), getTransitionOutAnim());
    }

    protected void subscribeFeatureStub(@IdRes int viewId) {
        subscribeFeatureStub($(viewId));
    }

    protected void subscribeFeatureStub(View view) {
        subscribeClick(view, o -> toastForNotImplementedFeature());
    }

    /**
     * 使用默认的throttle设置来注册点击事件。
     *
     * @see #subscribeClick(View, Consumer)
     * @see #$(int)
     */
    protected void subscribeClick(@IdRes int id, Consumer<Object> consumer) {
        subscribeClick($(id), consumer);
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
     * @param view    要注册的View
     * @param action1 点击后执行的事件
     */
    protected void subscribeClickWithoutThrottle(View view, Consumer<Object> action1) {
        RxView.clicks(view)
                .subscribe(action1);
    }
//
//    /**
//     * 获取状态栏的高度
//     *
//     * @return 状态栏的高度
//     */
//    protected int getStatusBarHeight() {
//        try {
//            //通过反射获取到类
//            @SuppressLint("PrivateApi") Class<?> aClass = Class.forName("com.android.internal.R$dimen");
//            //创建对象
//            Object o = aClass.newInstance();
//            //拿取属性
//            Field status_bar_height = aClass.getField("status_bar_height");
//            //获取值
//            Object o1 = status_bar_height.get(o);
//            int height = Integer.parseInt(o1.toString());
//            return getResources().getDimensionPixelOffset(height);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    protected int getAndoirdHeight() {
//        DisplayMetrics dm = new DisplayMetrics();
//        //取得窗口属性
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        //窗口的宽度
//        return dm.heightPixels;
//    }
//
//    protected int getWidth() {
//
//        WindowManager wm = (WindowManager) this
//                .getSystemService(Context.WINDOW_SERVICE);
//        return wm.getDefaultDisplay().getWidth();
//    }

    /**
     * 判断软键盘是否弹出
     */
    public boolean isShowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }

}
