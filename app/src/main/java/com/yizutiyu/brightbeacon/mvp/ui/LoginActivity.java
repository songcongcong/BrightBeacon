package com.yizutiyu.brightbeacon.mvp.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.activity.MainActivity;
import com.yizutiyu.brightbeacon.activity.PatrolAreaActivity;
import com.yizutiyu.brightbeacon.base.BaseMvpActivity;
import com.yizutiyu.brightbeacon.info.LoginInfo;
import com.yizutiyu.brightbeacon.mvp.impl.LoginPresenterImpl;
import com.yizutiyu.brightbeacon.mvp.uiinterface.LoginUiInterface;
import com.yizutiyu.brightbeacon.utils.AppUtils;
import com.yizutiyu.brightbeacon.utils.ContensUtils;
import com.yizutiyu.brightbeacon.utils.ToastUtils;


import javax.inject.Inject;

import butterknife.BindView;

public class LoginActivity extends BaseMvpActivity<LoginPresenterImpl> implements LoginUiInterface {

    private static final String TAG = "LoginActivity";
    // 注解presenter层
    @Inject
    LoginPresenterImpl impl;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_circle)
    SimpleDraweeView ivCircle;
    @BindView(R.id.relayout_user)
    RelativeLayout relayoutUser;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.ed_account)
    EditText edAccount;
    @BindView(R.id.linear_account)
    LinearLayout linearAccount;
    @BindView(R.id.linear)
    RelativeLayout linear;
    @BindView(R.id.ed_pwd)
    EditText edPwd;
    @BindView(R.id.linear_password)
    LinearLayout linearPassword;
    @BindView(R.id.linear_pwd)
    RelativeLayout linearPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;


    // 定位权限
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 101;

    @Override
    protected LoginPresenterImpl initInjector() {
        component.inject(this);
        return impl;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        // 将view对象设置给presenter
        impl.setLoginUiInterface(this);
    }

    @Override
    protected void init() {
        //登录
        subscribeClick(tvLogin, o -> {
            if (!TextUtils.isEmpty(edAccount.getText().toString().trim()) && !TextUtils.isEmpty(edPwd.getText().toString().trim())) {
                impl.normalLogin(edAccount.getText().toString().trim(), edPwd.getText().toString().trim(), this);
            } else {
                ToastUtils.showToast("账号密码不能为空");
            }
        });

        // 加载圆形
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse("http://avatar.csdn.net/4/E/8/1_ylscp.jpg"))
                .setTapToRetryEnabled(true)
                .build();

        ivCircle.setController(controller);
    }

    @Override
    public void NormalLoginSucces(LoginInfo loginBean) {
        // 请求成功，更新UI
//        Log.d("song", "请求成功" + loginBean.toString());
        Intent intent = new Intent(LoginActivity.this, PatrolAreaActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            //检测是否授权
            if (ContensUtils.checkAndApplyfPermissionActivity(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CAMERA_PERMISSION_REQUEST_CODE)) {
                Log.e(TAG, "===========检查权限---用户已经拥有相机这个权限了");

            } else {
                Log.e(TAG, "===========检查权限---弹出系统的用户没有相机这个权限");
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CAMERA_PERMISSION_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (ContensUtils.checkPermission(grantResults)) {
            if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
                Log.e(TAG, "===========权限回调---用户同意了");//                    }
            }
        }
    }
}
