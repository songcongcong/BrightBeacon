package com.yizutiyu.brightbeacon.mvp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.adapter.PictureAdapter;
import com.yizutiyu.brightbeacon.base.BaseActivity;
import com.yizutiyu.brightbeacon.custom.MyHashMap;
import com.yizutiyu.brightbeacon.info.RegionListInfo;
import com.yizutiyu.brightbeacon.utils.ContensUtils;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * InspectionDetailsActivity
 */
public class InspectionDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_header_back)
    ImageView ivHeaderBack;
    @BindView(R.id.relay_back)
    RelativeLayout relayBack;
    @BindView(R.id.tv_back_header)
    TextView titleHeader;
    @BindView(R.id.iv_liean)
    View ivLiean;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.tv_detail_success)
    TextView tvDetailSuccess;
    /**
     * 判断是否点击了返回键按钮
     */
    private boolean isMChilk = false;
    /**
     * mLinear
     */
    private LinearLayout mLinear;
    /**
     * mRelayout
     */
    private RelativeLayout mRelayout;
    /**
     * mError
     */
    private TextView mError;
    /**
     * mSwitchSee
     */
    private TextView mSwitchSee;
    /**
     * mTitle
     */
    private String mTitle;
    /**
     * mTvTitle
     */
    private TextView mTvTitle;
    /**
     * mContent
     */
    private TextView mContent;
    /**
     * mState
     */
    private int mState;
    /**
     * 上传图片结合
     */
    private List<String> imgLists;

    /**
     * 上传视频结合
     */
    private List<String> videoLists;
    /**
     * 记录异常状态
     */
    public int index = 0;
    /**
     * 存储提交巡检参数（detail）
     */
    private List<MyHashMap<String>> mapList;
    /**
     * 存储状态
     */
    private List<Integer> mErrorState = new ArrayList<>();
    /**
     * 将参数传给 patrolAreaActivity(json格式)
     */
    private List<MyHashMap<String>> myHashMaps;
    /**
     * config
     */
    private SharedPreferences config;
    /**
     * adapter
     */
    private PictureAdapter adapter;
    /**
     * mDetailList  (详情页数据)
     */
    private List<RegionListInfo.ProjectListBean> mDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);
        ButterKnife.bind(this);
        titleHeader.setText("巡检详情页");
        mapList = new ArrayList<>();
        init();
    }

    private void init() {
        config = getSharedPreferences("config", MODE_PRIVATE);
        // 点击返回
        ivHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 得到详情数据
        mDetailList = (List<RegionListInfo.ProjectListBean>) getIntent().getSerializableExtra("detailList");
        mTitle = getIntent().getStringExtra("title");
        if (mDetailList == null) {
            return;
        }
        if (mTitle == null) {
            return;
        }
        adapter = new PictureAdapter(R.layout.picture_result_item, mDetailList);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.setAdapter(adapter);

        //点击异常或正常
        adapter.setOnChilkLisener(new PictureAdapter.onChilkLisener() {
            @Override
            public void OnLisener(LinearLayout linearLayout, RelativeLayout relativeLayout, TextView switchError, TextView mSee, TextView title, TextView content, int state, int position) {
                // 点击异常
                isMChilk = true;
                mLinear = linearLayout;
                mRelayout = relativeLayout;
                mError = switchError;
                mSwitchSee = mSee;
                mTvTitle = title;
                mContent = content;
                mState = state;
                Intent intent = new Intent(InspectionDetailsActivity.this, RegionErrorActivity.class);
                startActivityForResult(intent, 1);
            }

            @Override
            public void OnNormalLisener(LinearLayout linearLayout, RelativeLayout relativeLayout, TextView switchError, TextView mSee, TextView title, TextView content, int state, int position) {
                // 点击正常
                isMChilk = true;
                mTvTitle = title;
                mContent = content;
                mState = state;
                setParmers(false);
            }
        });
        //查看回调
        adapter.setOnItemSeeLisener(new PictureAdapter.onItemSeeLisener() {
            @Override
            public void OnLisener() {
                Intent intent = new Intent(InspectionDetailsActivity.this, NonomarlContentActivity.class);
                intent.putStringArrayListExtra("normallist", (ArrayList<String>) imgLists);
                startActivity(intent);
            }
        });

        // 区域巡检完成
        tvDetailSuccess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (adapter.getmFlagList() != null && mDetailList.size() == adapter.getmFlagList().size()) {
                    // 将参数存入本地
                    if (mapList.size() > 0) {
                        ContensUtils.setDataList("parameslist", mapList, config);
                    }
                    // 从本地取出数据（json格式）
                    myHashMaps = ContensUtils.getDataList("parameslist", config);
                    if (index <= 0) { // 正常
                        if (isMChilk) {
                            Intent intent = new Intent();
                            intent.putExtra("all", true);
                            intent.putExtra("errornum", 0);
                            intent.putExtra("parmelist", (Serializable) myHashMaps);
                            setResult(10, intent);
                            finish();
                        }
                    } else { // 异常
                        Intent intent = new Intent();
                        intent.putExtra("errornum", 1);
                        intent.putExtra("parmelist", (Serializable) myHashMaps);
//                        intent.putStringArrayListExtra("normalList", (ArrayList<String>) imgLists);
                        setResult(11, intent);
                        finish();
                    }
                } else {
                    Toast.makeText(InspectionDetailsActivity.this, "请选择区域状态！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 2) {
                // 选择图片页面的回调
                if (data != null) {
                    imgLists = data.getStringArrayListExtra("imgList");
                    videoLists = data.getStringArrayListExtra("videoList");
                }
                index++;
                if (mLinear != null && mRelayout != null && mError != null && mSwitchSee != null) {
                    mLinear.setVisibility(View.GONE);
                    mRelayout.setVisibility(View.VISIBLE);
                    mError.setText(R.string.no_abnormal);
                    mError.setTextColor(getResources().getColor(R.color.color_see));
                    mSwitchSee.setVisibility(View.VISIBLE);
                    setParmers(true);
                }
            }
        }
    }


    // 提交巡检的参数
    public void setParmers(boolean isError) {
        MyHashMap<String> map = new MyHashMap<>();
        if (mTitle != null) {
            map.put("region", mTitle);
        }
        map.put("title", mTvTitle.getText().toString().trim());
        map.put("status", String.valueOf(mState));
        map.put("message", mContent.getText().toString().trim());
        if (imgLists != null) {
            StringBuilder sb = new StringBuilder();
            for (String dataBean : imgLists) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(dataBean);
            }
            // 存放异常的图片
            if (isError) {
                ContensUtils.setPictureList("errorImglist", imgLists, config);
                map.put("image", sb.toString());
            } else {
                map.put("image", "");
            }
        }
        /**
         * 存放视频路径
         */
        if (videoLists != null) {
            StringBuilder sb = new StringBuilder();
            for (String dataBean : videoLists) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(dataBean);
            }
            // 存放异常的图片
            if (isError) {
                ContensUtils.setPictureList("errorVideolist", videoLists, config);
                map.put("video", sb.toString());
            } else {
                map.put("video", "");
            }
        }
        mapList.add(map);

    }
}
