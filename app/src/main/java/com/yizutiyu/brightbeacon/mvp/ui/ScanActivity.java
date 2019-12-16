package com.yizutiyu.brightbeacon.mvp.ui;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brtbeacon.sdk.BRTBeacon;
import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.BRTThrowable;
import com.brtbeacon.sdk.callback.BRTBeaconManagerListener;
import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.activity.PatrolAreaActivity;
import com.yizutiyu.brightbeacon.adapter.BeaconViewAdapter;
import com.yizutiyu.brightbeacon.base.BaseMvpActivity;
import com.yizutiyu.brightbeacon.eventbus.BrtBeaconEventBus;
import com.yizutiyu.brightbeacon.info.BrtBeaconInfo;
import com.yizutiyu.brightbeacon.mvp.impl.ScanPresenterImpl;
import com.yizutiyu.brightbeacon.mvp.uiinterface.ScanUiInterface;
import com.yizutiyu.brightbeacon.sqlite.DataBaseOpenHelper;
import com.yizutiyu.brightbeacon.sqlite.DatabaseUtils;
import com.yizutiyu.brightbeacon.utils.AppUtils;
import com.yizutiyu.brightbeacon.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * ScanActivity
 */
public class ScanActivity extends BaseMvpActivity<ScanPresenterImpl> implements ScanUiInterface {

    private static final String TAG = "ScanActivity";
    @Inject
    ScanPresenterImpl impl;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.btn_refresh)
    Button mBack;
    /**
     * beaconManager
     */
    private BRTBeaconManager beaconManager;
    /**
     * mAdapter
     */
    private BeaconViewAdapter mAdapter;

    private BRTBeacon mBrtBeacon;
    private static List<String> list;
    // 数据库表名
    public static String mDbTableName = "t_message";
    // 创建表
    public static String sql_message = "create table " + mDbTableName + " (id int primary key,longitude varchar(50),lat varchar(50))";

    @Override
    protected ScanPresenterImpl initInjector() {
        component.inject(this);
        return impl;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        impl.setUiInterface(this);
        checkBluetoothValid();
        DatabaseUtils.initHelper(ScanActivity.this, "brt");
        // 初始化蓝牙
        beaconManager = BRTBeaconManager.getInstance(this);
        beaconManager.setPowerMode(BRTBeaconManager.POWER_MODE_LOW_POWER);

        mAdapter = new BeaconViewAdapter();
        recycle.setAdapter(mAdapter);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.notifyDataSetChanged();

        //点击添加设备
        mAdapter.setOnItemViewListener(new BeaconViewAdapter.onItemViewListener() {
            @Override
            public void onItemListener(String mAdress, String name, BRTBeacon brtBeacon) {
                mBrtBeacon = brtBeacon;
                if (name != null) {
                    impl.addRegion(ScanActivity.this, mAdress, name);
                }
            }
        });

        // 点击返回
        subscribeClick(mBack, o -> {
            startActivity(new Intent(ScanActivity.this, PatrolAreaActivity.class));
            finish();
        });
    }

    @Override
    protected void init() {
    }

    // 请求成功
    @Override
    public void addRegionSuccess(String msg) {
        ToastUtils.showToast("添加成功！");
//        List<BRTBeacon> brtBeaconInfos = DatabaseUtils.getHelper().queryAll(BRTBeacon.class);
//        if (!brtBeaconInfos.contains(mBrtBeacon)) {
//            DatabaseUtils.getHelper().save(mBrtBeacon);
//            Log.d("song","存入成功:"+mBrtBeacon.getMacAddress());
//        } else {
//            Log.d("song","存入失败"+mBrtBeacon.getMacAddress());
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        startScan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopScan();
    }

    // 开始扫描
    private void startScan() {
        beaconManager.setBRTBeaconManagerListener(scanListener);
        beaconManager.startRanging();
    }

    //结束扫描
    private void stopScan() {
        beaconManager.stopRanging();
        beaconManager.setBRTBeaconManagerListener(null);
    }

    // 扫描设备监听
    private BRTBeaconManagerListener scanListener = new BRTBeaconManagerListener() {

        @Override
        public void onUpdateBeacon(final ArrayList<BRTBeacon> arg0) {
            mAdapter.replaceAll(arg0);
//            Log.d("song", "扫描到：" + arg0.toString());
        }

        @Override
        public void onNewBeacon(BRTBeacon arg0) {
        }

        @Override
        public void onGoneBeacon(BRTBeacon arg0) {
        }

        @Override
        public void onError(BRTThrowable arg0) {
        }
    };

    // activity跳转
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ScanActivity.class);
        context.startActivity(intent);
    }

    //检查蓝牙是否开启
    private void checkBluetoothValid() {
        final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            AlertDialog dialog = new AlertDialog.Builder(this).setTitle("错误").setMessage("你的设备不具备蓝牙功能!").create();
            dialog.show();
            return;
        }

        if (!adapter.isEnabled()) {
            AlertDialog dialog = new AlertDialog.Builder(this).setTitle("提示")
                    .setMessage("蓝牙设备未打开,请开启此功能后重试!")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(mIntent, 1);
                        }
                    })
                    .create();
            dialog.show();
        }
    }
}
