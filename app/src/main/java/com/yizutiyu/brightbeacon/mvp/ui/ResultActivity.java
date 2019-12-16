package com.yizutiyu.brightbeacon.mvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.activity.MainActivity;
import com.yizutiyu.brightbeacon.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends BaseActivity {

    @BindView(R.id.tv_success)
    TextView tvSuccess;
    @BindView(R.id.relay_result)
    RelativeLayout relayResult;
    @BindView(R.id.img_sc)
    ImageView imgSc;
    @BindView(R.id.tv_sc_title)
    TextView tvScTitle;
    @BindView(R.id.tv_success_time)
    TextView tvSuccessTime;
    @BindView(R.id.lin_sc)
    LinearLayout linSc;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.linear_error)
    LinearLayout linearError;
    @BindView(R.id.tv_result)
    TextView tvResult;
    // 记录异常状态
    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        ArrayList<Integer> errornum = getIntent().getIntegerArrayListExtra("errornum");
        String data = getIntent().getStringExtra("data");
        for (int i = 0; i < errornum.size(); i++) {
            if (errornum.get(i) == 1) {
                mCount++;
            }
        }
        tvError.setText(Html.fromHtml(getResources().getString(R.string.result_error_num, mCount)));
        tvSuccessTime.setText(String.format(getResources().getString(R.string.result_time), data));
        tvSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, LoginActivity.class));
                finish();
            }
        });

        tvResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
