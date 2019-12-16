package com.yizutiyu.brightbeacon.mvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.adapter.RerrorListAdapter;
import com.yizutiyu.brightbeacon.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RerrorListActivity extends BaseActivity {

    @BindView(R.id.iv_header_back)
    ImageView ivHeaderBack;
    @BindView(R.id.relay_back)
    RelativeLayout relayBack;
    @BindView(R.id.tv_back_header)
    TextView tvBackHeader;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.error_recycle)
    RecyclerView errorRecycle;
    @BindView(R.id.tv_submit_result)
    TextView tvSubmitResult;

    private List<String> mList = Arrays.asList("电源异常", "电源异常", "电源异常", "电源异常", "电源异常", "电源异常", "电源异常", "电源异常");
    private String[] mImg = {"http://avatar.csdn.net/4/E/8/1_ylscp.jpg", "http://avatar.csdn.net/4/E/8/1_ylscp.jpg", "http://avatar.csdn.net/4/E/8/1_ylscp.jpg", "http://avatar.csdn.net/4/E/8/1_ylscp.jpg",
            "http://avatar.csdn.net/4/E/8/1_ylscp.jpg", "http://avatar.csdn.net/4/E/8/1_ylscp.jpg", "http://avatar.csdn.net/4/E/8/1_ylscp.jpg", "http://avatar.csdn.net/4/E/8/1_ylscp.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rerror_list);
        ButterKnife.bind(this);
        tvBackHeader.setText(R.string.tv_error_detail);
        ivHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSubmitResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final List<String> errorNormalList = getIntent().getStringArrayListExtra("errorNormalList");
        RerrorListAdapter adapter = new RerrorListAdapter(mList, mImg, this);
        errorRecycle.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        errorRecycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new RerrorListAdapter.onItemClickListener() {
            @Override
            public void OnClick() {
                Intent intent = new Intent(RerrorListActivity.this, NonomarlContentActivity.class);
                intent.putStringArrayListExtra("normallist", (ArrayList<String>) errorNormalList);
                startActivity(intent);
            }
        });
    }
}
