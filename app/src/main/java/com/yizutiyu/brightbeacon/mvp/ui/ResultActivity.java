package com.yizutiyu.brightbeacon.mvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ResultActivity
 */
public class ResultActivity extends BaseActivity {
    /**
     * tvSuccess
     */
    @BindView(R.id.tv_success)
    TextView tvSuccess;
    /**
     * relayResult
     */
    @BindView(R.id.relay_result)
    RelativeLayout relayResult;
    /**
     * imgSc
     */
    @BindView(R.id.img_sc)
    ImageView imgSc;
    /**
     * tvScTitle
     */
    @BindView(R.id.tv_sc_title)
    TextView tvScTitle;
    /**
     * tvSuccessTime
     */
    @BindView(R.id.tv_success_time)
    TextView tvSuccessTime;
    /**
     * linSc
     */
    @BindView(R.id.lin_sc)
    LinearLayout linSc;
    /**
     * tvError
     */
    @BindView(R.id.tv_error)
    TextView tvError;
    /**
     * linearError
     */
    @BindView(R.id.linear_error)
    LinearLayout linearError;
    /**
     * tvResult
     */
    @BindView(R.id.tv_result)
    TextView tvResult;
    /**
     * 记录异常状态
     */
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
