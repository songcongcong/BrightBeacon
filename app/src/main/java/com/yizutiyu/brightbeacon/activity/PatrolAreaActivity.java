package com.yizutiyu.brightbeacon.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brtbeacon.sdk.BRTBeacon;
import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.BRTMonitor;
import com.brtbeacon.sdk.BRTRegion;
import com.brtbeacon.sdk.BRTThrowable;
import com.brtbeacon.sdk.callback.BRTBeaconManagerListener;
import com.brtbeacon.sdk.callback.BRTBeaconMonitorListener;
import com.google.gson.Gson;
import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.adapter.PatrolAdapter;
import com.yizutiyu.brightbeacon.base.BaseMvpActivity;
import com.yizutiyu.brightbeacon.eventbus.FileEventBus;
import com.yizutiyu.brightbeacon.eventbus.RegionNumEventBus;
import com.yizutiyu.brightbeacon.face.MHFace;
import com.yizutiyu.brightbeacon.info.FaceDateMessageInfo;
import com.yizutiyu.brightbeacon.info.RegionListInfo;
import com.yizutiyu.brightbeacon.info.SuccessInfo;
import com.yizutiyu.brightbeacon.info.UserInfo;
import com.yizutiyu.brightbeacon.mvp.impl.PatrolPresneterImpl;
import com.yizutiyu.brightbeacon.mvp.ui.InspectionDetailsActivity;
import com.yizutiyu.brightbeacon.mvp.ui.RerrorListActivity;
import com.yizutiyu.brightbeacon.mvp.ui.ResultActivity;
import com.yizutiyu.brightbeacon.mvp.ui.ScanActivity;
import com.yizutiyu.brightbeacon.mvp.uiinterface.PatrolUiInterface;
import com.yizutiyu.brightbeacon.surfaceview.CameraSurfaceHolder;
import com.yizutiyu.brightbeacon.utils.ContensUtils;
import com.yizutiyu.brightbeacon.utils.SPUtils;
import com.yizutiyu.brightbeacon.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * PatrolAreaActivity
 */
public class PatrolAreaActivity extends BaseMvpActivity<PatrolPresneterImpl> implements PatrolUiInterface {
    public static String TAG = "PatrolAreaActivity";
    @BindView(R.id.title_header)
    TextView titleHeader;
    @BindView(R.id.tv_region)
    TextView tvRegion;
    @BindView(R.id.tv_scan)
    CheckBox tvScan;
    @BindView(R.id.area_recycleView)
    RecyclerView areaRecycleView;
    @BindView(R.id.tv_submit_result)
    TextView tvSubmitResult;
    /**
     * 注解P层
     */
    @Inject
    PatrolPresneterImpl impl;
    /**
     * beaconManager
     */
    private BRTBeaconManager beaconManager;
    /**
     * 用来识别设备是否进入该区域
     */
    private BRTMonitor monitor;
    /**
     * mAdapter
     */
    private PatrolAdapter mAdapter;
    /**
     * mList
     */
    private List<RegionListInfo> mList;
    /**
     * mTitleRegion
     */
    private String mTitleRegion;
    /**
     * 判断是否取消弹窗
     */
    private static boolean isSuccess = false;

    /**
     * mCameraSurfaceHolder   设置mSurfaceView
     */
    CameraSurfaceHolder mCameraSurfaceHolder = new CameraSurfaceHolder();
    /**
     * 相机权限
     */
    public static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    /**
     * 相机请求码
     */
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    /**
     * 标记状态回调
     */
    private static final int FACE_SUCCESS = 100;//crop_small_picture
    /**
     * 得到照相机的人脸数据
     */
    private ByteArrayOutputStream mFile = null;

    /**
     * 存放提交巡检的详情参数
     */
    private ArrayList<String> maps;
    /**
     * 用来存放异常或正常状态（key:position）
     */
    private static Map<Integer, Boolean> mapPosition = new HashMap<>();

    /**
     * mStatePosition
     */
    private int mStatePosition;
    /**
     * 标记区域的状态
     */
    private int mErrorState = -1;
    /**
     * mSurfaceView
     */
    private SurfaceView mSurfaceView;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 上传图片集合
     */
    private List<String> normalList;

    /**
     * 用来存储异常的状态
     */
    private List<Integer> mIndexs = new ArrayList<>();
    /**
     * 标记查询只跳转一次
     */
    private boolean isIntent = false;
    /**
     * myHandler
     */
    private MyHandler myHanlder = new MyHandler(PatrolAreaActivity.this);

    @SuppressLint("HandlerLeak")
    public class MyHandler extends Handler {

        // 通过弱引用解决内存泄露问题
        private WeakReference<PatrolAreaActivity> weakReference;

        /**
         * 构造器创建弱引用对象
         *
         * @param activity activity
         */
        public MyHandler(PatrolAreaActivity activity) {
            this.weakReference = new WeakReference<PatrolAreaActivity>(activity);
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 通过.get()方法得到activity实例
            PatrolAreaActivity patrolAreaActivity = weakReference.get();
            if (patrolAreaActivity != null) {
                if (msg.what == 1) {
                    // 扫描到设备去刷新适配器
                    mAdapter.notifyDataSetChanged();
                } else if (msg.what == 0) {
                    // 未进入设备去刷新适配器
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private SharedPreferences config;
    /**
     * 记录已完成巡检的次数
     */
    private int mRegionNum;

    @Override
    protected PatrolPresneterImpl initInjector() {
        component.inject(this);
        return impl;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patrol_area;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        config = getSharedPreferences("config", MODE_PRIVATE);
        SPUtils.put(this, "currenttime", TimeUtil.getCurrentTime()); // 记录当前时间
        // 设置给p层
        impl.setUiInterface(this);
        // 设置已完成区域UI
        titleHeader.setText(R.string.title_patrol_region);
        setTitleUi(null, 0);

        // 初始化蓝牙
        beaconManager = BRTBeaconManager.getInstance(PatrolAreaActivity.this);
        beaconManager.setPowerMode(BRTBeaconManager.POWER_MODE_LOW_POWER);

        // 调用列表接口
        impl.getRegionListSuccess(PatrolAreaActivity.this);


        // 列表数据
        mList = new ArrayList<>();
        mAdapter = new PatrolAdapter(this, mList);
        areaRecycleView.setLayoutManager(new GridLayoutManager(PatrolAreaActivity.this, 2));
        areaRecycleView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        // 点击进入扫描设备页面
        subscribeClick(tvScan, o -> {
            ScanActivity.startActivity(PatrolAreaActivity.this);
//            finish();
        });
        // 点击条目进行人脸检测
        mAdapter.setOnItemListener(new PatrolAdapter.onItemListener() {
            @Override
            public void onItemListener(int position) {
                mStatePosition = position;
                if (Build.VERSION.SDK_INT >= 23) {
                    //检测是否授权
                    if (ContextCompat.checkSelfPermission(PatrolAreaActivity.this, CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
                        Log.e(TAG, "===========检查权限---用户已经拥有相机这个权限了");
                        getDialog(position);
                    } else {
                        Log.e(TAG, "===========检查权限---弹出系统的用户没有相机这个权限");
                        ActivityCompat.requestPermissions(PatrolAreaActivity.this, new String[]{CAMERA_PERMISSION}, CAMERA_PERMISSION_REQUEST_CODE);
                    }
                }
            }
        });

        //点击查看
        mAdapter.setOnSeeChilk(new PatrolAdapter.onSeeChilk() {
            @Override
            public void onItemSeeChilk() {
//                if (normalList != null) {
                Intent intent = new Intent(PatrolAreaActivity.this, RerrorListActivity.class);
//                    intent.putStringArrayListExtra("errorNormalList", (ArrayList<String>) normalList); // 将上传图片集合传给activity
                startActivity(intent);
//                }
            }
        });

        // 提交巡检结果
        subscribeClick(tvSubmitResult, o -> {
            if (mRegionNum != 0) {
                startTime = (String) SPUtils.get(this, "currenttime", "");
                endTime = TimeUtil.getCurrentTime();
                if (mIndexs.contains(1)) {
                    mErrorState = 1;
                } else {
                    mErrorState = 0;
                }
                impl.submitSuccess(this, "日常巡检", startTime, endTime, String.valueOf(mErrorState), maps.toString());
            } else {
                Toast.makeText(PatrolAreaActivity.this, "没有巡检区域，请前去巡检！", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void init() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            if (mList != null) {
                for (int i = 0; i < mList.size(); i++) {
                    // 添加一个进入区域的设备
                    monitor = new BRTMonitor(mList.get(i).getBluetoothKey(), null, null, null, true, true);
                    beaconManager.addMonitor(monitor);
                    beaconManager.startRanging();
                    startMonit();
                }
            }
            mAdapter.notifyDataSetChanged();
        }
        Log.d("song", "resume:");
    }

    //开始添加 进入区域监听
    private void startMonit() {
        beaconManager.setBeaconMonitorListener(monitorListener);
        beaconManager.startMonitoring();
    }

    @Override
    protected void onPause() {
        super.onPause();
        beaconManager.stopMonitoring();
//        beaconManager.setBeaconMonitorListener(null);
//        beaconManager.removeAllMonitor();
        Log.d("song", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        beaconManager.stopMonitoring();
        beaconManager.setBeaconMonitorListener(null);
        beaconManager.removeAllMonitor();
        if (myHanlder != null) {
            myHanlder.removeMessages(1);
        }
        // 提交巡检参数
        ContensUtils.removeFileAppsKey(this, config, "parameslist");
        // 存放异常的图片
        ContensUtils.removeFileAppsKey(this, config, "errorImglist");

        Log.d("song", "onDestroy");
    }

    //扫描设备成功
    @Override
    public void saveBluetoothSuccess(String data) {

    }

    //列表请求成功
    @Override
    public void getRegionListSuccess(List<RegionListInfo> regionListInfo) {
        this.mList = regionListInfo;
        mAdapter.setDate(regionListInfo);
        setTitleUi(null, mList.size());
        areaRecycleView.setAdapter(mAdapter);
        if (regionListInfo != null) {
            for (int i = 0; i < regionListInfo.size(); i++) {
                // 添加一个进入区域的设备
                monitor = new BRTMonitor(regionListInfo.get(i).getBluetoothKey(), null, null, null, true, true);
                beaconManager.addMonitor(monitor);
                beaconManager.startRanging();
                startMonit();
                Log.d("song", "列表请求成功:" + mList.get(i).getBluetoothKey());
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void submitResuleSuccess(SuccessInfo successInfo) {
        // 提交巡检成功清除已巡检的数据
        if (mAdapter != null) {
            mAdapter.getMap().clear();
        }
        // 跳转到结束页面，将异常数量和巡检的时间传过去
        String timeDifference = TimeUtil.getTimeDifference(startTime, endTime);
        Intent intent = new Intent(PatrolAreaActivity.this, ResultActivity.class);
        intent.putExtra("data", timeDifference);
        intent.putIntegerArrayListExtra("errornum", (ArrayList<Integer>) mIndexs);
        startActivity(intent);
        finish();
    }

    @Override
    public void getFaceData(FaceDateMessageInfo faceDateMessageInfo, int position) {
        isIntent = false;
        if (faceDateMessageInfo.isIs_exist() == true) {
            isSuccess = true;
            Log.e(TAG, "成功跳转：");
            Intent intent = new Intent(PatrolAreaActivity.this, InspectionDetailsActivity.class);
            intent.putExtra("detailList", (Serializable) mList.get(position).getProjectList()); // 巡检详情页数据
            intent.putExtra("title", mList.get(position).getRegion()); // 区域名称
            startActivityForResult(intent, FACE_SUCCESS);
        } else {
            isSuccess = false;
            Toast.makeText(this, "人脸检测失败！", Toast.LENGTH_LONG).show();
        }


    }


    // 是否发现新的蓝牙设备监听
    @SuppressLint("StringFormatMatches")
    private BRTBeaconManagerListener brtBeaconManagerListener = new BRTBeaconManagerListener() {
        @Override
        public void onUpdateBeacon(ArrayList<BRTBeacon> arrayList) {
//            for (int i = 0; i < arrayList.size(); i++) {
//                if (mList != null) {
//                    for (int j = 0; j < mList.size(); j++) {
//                        if (mList.get(j).getBluetoothKey().equals(arrayList.get(i).getMacAddress())) {
////                            if (!brtBeaconList.contains(arrayList.get(i).getMacAddress())) {
//                            brtBeaconList.add(arrayList.get(i).getMacAddress());
//                            isInRegion = true;
//                            // 添加一个进入区域的设备
//                            monitor = new BRTMonitor(arrayList.get(i).getMacAddress(), null, null, null, true, true);
//                            beaconManager.addMonitor(monitor);
//                            beaconManager.setBeaconMonitorListener(monitorListener);
//                            beaconManager.startRanging();
////                            Log.d("song", "添加一个:" + mList.get(j).getBluetoothKey() + ",:" + brtBeaconList.toString());
////                            Toast.makeText(PatrolAreaActivity.this, "添加：" + mList.get(j).getBluetoothKey(), Toast.LENGTH_LONG).show();
////                            } else {
////                                Toast.makeText(PatrolAreaActivity.this, "已添加：" + mList.get(j).getBluetoothKey(), Toast.LENGTH_LONG).show();
////                            }
//                        }
//                    }
//                }
//            }
        }

        @Override
        public void onNewBeacon(final BRTBeacon brtBeacon) {  // 找到一个蓝牙设备
//            if (mList != null) {
//                for (int j = 0; j < mList.size(); j++) {
//                    if (mList.get(j).getBluetoothKey().equals(brtBeacon.getMacAddress())) {
//                        // 添加一个进入区域的设备
//                        monitor = new BRTMonitor(brtBeacon.getMacAddress(), null, null, null, true, true);
//                        beaconManager.addMonitor(monitor);
//                        beaconManager.startRanging();
//                        Log.d("song", "添加24234234234234一个:"+brtBeacon.getName());
//                    }
//                }
//            }
        }

        @Override
        public void onGoneBeacon(BRTBeacon brtBeacon) {
        }

        @Override
        public void onError(BRTThrowable brtThrowable) {
        }
    };

    // 监听设备是否进入该区域
    private BRTBeaconMonitorListener monitorListener = new BRTBeaconMonitorListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onRegion(BRTBeacon beacon, BRTRegion region, final int state) {
            Log.e(TAG, "监听通知");
            Log.d("song","监听通知");
            String toastMsg;
            if (state == 0) {
                toastMsg = "离开Beacon：" + beacon.name + "地址：" + beacon.getMacAddress() + "所在区域!";
                // 设置标志
                if (mList != null) {
                    // 标记是否进入区域
                    for (int i = 0; i < mList.size(); i++) {
                        mList.get(i).setFlag(false);
                    }
                }
                myHanlder.sendEmptyMessage(0);
            } else {
                toastMsg = "进入Beacon：" + beacon.name + "地址：" + beacon.getMacAddress() + "所在区域!";
                // 设置标志
                if (mList != null) {
                    // 标记是否进入区域
                    for (int i = 0; i < mList.size(); i++) {
                        mList.get(i).setFlag(true);
                    }
                }
                myHanlder.sendEmptyMessage(1);
                mAdapter.setBrt(beacon);
            }
            Toast.makeText(PatrolAreaActivity.this, toastMsg, Toast.LENGTH_LONG).show();
        }
    };

    // 刷新标题UI
    @Subscribe
    public void onMessageNumEvent(RegionNumEventBus regionNumEventBus) {
        if (regionNumEventBus != null) {
            this.mRegionNum = regionNumEventBus.getmNum();
            setTitleUi(regionNumEventBus, mList.size());
        }
    }

    // 检测人脸接受到的File文件
    @Subscribe
    public void onMessageEvent(FileEventBus fileEventBus) {
        if (fileEventBus.getFile() != null) {
            this.mFile = fileEventBus.getFile();
        }
    }


    // 刷新标题UI
    private void setTitleUi(RegionNumEventBus regionNumEventBus, int count) {
        if (mList != null && regionNumEventBus != null) {
            mTitleRegion = String.format(getResources().getString(R.string.Patrol_region), count, regionNumEventBus.getmNum());
        } else {
            mTitleRegion = String.format(getResources().getString(R.string.Patrol_region), count, 0);
        }
        tvRegion.setText(mTitleRegion);
    }


    // 弹出弹窗，检测人脸
    private void getDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.camera_layout, null);

        final AlertDialog alertDialog = builder.create();
        mSurfaceView = view.findViewById(R.id.sup_sufview);
        ImageView mBack = view.findViewById(R.id.camera_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (!isSuccess) {
//                    Log.d(TAG,"为")
                    mAdapter.setIsSucces(true);
                } else {
                    mAdapter.setIsSucces(false);
                }
            }
        });

        TextView mTvStart = view.findViewById(R.id.tv_start);
        mTvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isIntent) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            isIntent = true;
                            try {
//                                String userId = MHFace.addUser(mFile, "朱凯", "ezu_kj2");
//                                MHFace.addUser(mFile, "朱凯", userId, "ezu_kj2");
                                queryFaceUserId(mFile, alertDialog, position);
                                Log.d("song", "查询");
                            } catch (Exception e) {
                            }
                        }
                    }).start();
                } else {
                    Log.d("song", "查询:121212");
                }


            }
        });
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                alertDialog.dismiss();
                if (!isSuccess) {
                    mAdapter.setIsSucces(true);
                } else {
                    mAdapter.setIsSucces(false);
                }

            }
        });
        alertDialog.setView(view);
        alertDialog.show();
        mCameraSurfaceHolder.setCameraSurfaceHolder(this, mSurfaceView);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.63);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth());    //宽度设置为屏幕的0.5
        alertDialog.getWindow().setAttributes(p);     //设置生效
    }


    /**
     * 查询人脸信息根据人脸库中的用户id
     *
     * @param file
     */
    public void queryFaceUserId(ByteArrayOutputStream file, AlertDialog dialog, int position) {

        try {
            JSONObject json = MHFace.detectUserIdByFace(file, "ezu_kj2");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "json：" + json.toString());
                    UserInfo userInfo = new Gson().fromJson(json.toString(), UserInfo.class);
                    if (userInfo.getError_msg().equals("SUCCESS")) {
                        dialog.dismiss();
                        try {
                            for (int i = 0; i < userInfo.getResult().getUser_list().size(); i++) {
                                String user_id = userInfo.getResult().getUser_list().get(i).getUser_id();
//                        String ezu_kj2 = MHFace.deleteUser(user_id, "ezu_kj2");
//                        Log.e(TAG, "删除之后：" +ezu_kj2);

                                impl.getFaceDate(PatrolAreaActivity.this, user_id, position);
                            }
                        } catch (Exception e) {
                            Log.d("song", "识别异常：" + e.toString());
                        }
                    } else {
                        isIntent = false;
                        Log.e(TAG, "检测失败：");
                        Toast.makeText(PatrolAreaActivity.this, "人脸检测失败！", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * activity回调
     *
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FACE_SUCCESS) {
            if (resultCode == 10) { // 正常
                if (data != null) {
                    maps = (ArrayList<String>) data.getSerializableExtra("parmelist"); // 提交巡检结果参数
                    int mErrorIndexState = data.getIntExtra("errornum", -1); // 异常状态集合
                    mIndexs.add(mErrorIndexState);
                }
                // 将当前position的状态传给适配器
                mapPosition.put(mStatePosition, true);
                mAdapter.setMapPosition(mapPosition);
            } else if (resultCode == 11) {  //异常
                if (data != null) {
                    maps = (ArrayList<String>) data.getSerializableExtra("parmelist"); // 提交巡检结果参数
                    int mErrorIndexState = data.getIntExtra("errornum", -1); // 异常状态集合
//                    normalList = data.getStringArrayListExtra("normalList"); // 上传图片集合
                    mIndexs.add(mErrorIndexState);
                }
                // 将当前position的状态传给适配器
                mapPosition.put(mStatePosition, false);
                mAdapter.setMapPosition(mapPosition);
            }
        }
    }

    /**
     * 权限回调
     *
     * @param requestCode  requestCode
     * @param permissions  permissions
     * @param grantResults grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (permissions[0].equals(CAMERA_PERMISSION)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "===========权限回调---用户同意了");
                    getDialog(mStatePosition);
                } else {
                    Log.e(TAG, "===========权限回调---用户拒绝了");
                }
            }
        }
    }

}
