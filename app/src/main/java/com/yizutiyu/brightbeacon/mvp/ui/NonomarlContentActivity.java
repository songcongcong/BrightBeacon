package com.yizutiyu.brightbeacon.mvp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.adapter.ErrorDetailAdapter;
import com.yizutiyu.brightbeacon.utils.ContensUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * NonomarlContentActivity
 */
public class NonomarlContentActivity extends AppCompatActivity {
    /**
     * ivHeaderBack
     */
    @BindView(R.id.iv_header_back)
    ImageView ivHeaderBack;
    /**
     * relayBack
     */
    @BindView(R.id.relay_back)
    RelativeLayout relayBack;
    /**
     * tvBackHeader
     */
    @BindView(R.id.tv_back_header)
    TextView tvBackHeader;
    /**
     * ivNote
     */
    @BindView(R.id.iv_note)
    ImageView ivNote;
    /**
     * tvErrorSwitch
     */
    @BindView(R.id.tv_error_switch)
    TextView tvErrorSwitch;
    /**
     * tvErrorContent
     */
    @BindView(R.id.tv_error_content)
    TextView tvErrorContent;
    /**
     * rlErrorContent
     */
    @BindView(R.id.rl_error_content)
    RelativeLayout rlErrorContent;
    /**
     * llContent
     */
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    /**
     * viewError
     */
    @BindView(R.id.view_error)
    View viewError;
    /**
     * viewErrorTop
     */
    @BindView(R.id.view_error_top)
    View viewErrorTop;
    /**
     * textviewPicture
     */
    @BindView(R.id.textview_picture)
    TextView textviewPicture;
    /**
     * revyvleview
     */
    @BindView(R.id.revyvleview)
    RecyclerView revyvleview;
    /**
     * tvReturn
     */
    @BindView(R.id.tv_return)
    TextView tvReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonomarl_content);
        ButterKnife.bind(this);
        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        tvBackHeader.setText("异常详情");
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        List<String> mList = getIntent().getStringArrayListExtra("normallist");

//        // 从本地取出数据（json格式）
//        List<MyHashMap<String>> myHashMaps = ContensUtils.getDataList("maplist", config);
//        Log.d("song","巡检详情页："+myHashMaps.toString());
//        for (int i = 0; i < myHashMaps.size(); i++) {
////            MyHashMap<String> stringMyHashMap = myHashMaps.get(i);
//            Log.d("song","遍历："+myHashMaps.get(i));
//        }
        List<String> mList = ContensUtils.getPictureDataList("errorImglist", config);
        List<String> mVideoList = ContensUtils.getPictureDataList("errorVideolist", config);
        if (mList.size() > 0) {
            ErrorDetailAdapter errorDetailsAdapter = new ErrorDetailAdapter(R.layout.picture_layout_item, mList);
            revyvleview.setLayoutManager(new GridLayoutManager(this, 3));
            revyvleview.setAdapter(errorDetailsAdapter);
        }
    }
}
