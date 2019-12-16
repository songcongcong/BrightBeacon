package com.yizutiyu.brightbeacon.adapter;

import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.base.BaseAdapter;
import com.yizutiyu.brightbeacon.info.RegionListInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @data 2019/9/22
 */
public class PictureAdapter extends BaseAdapter<RegionListInfo.ProjectListBean, BaseViewHolder> {
    private List<Integer> mFlagList = new ArrayList<>();
    public List<Integer> getmFlagList() {
        return mFlagList;
    }
    public PictureAdapter(int layoutResId, List<RegionListInfo.ProjectListBean> data) {
        super(layoutResId, data);
    }

    public PictureAdapter(List<RegionListInfo.ProjectListBean> data) {
        super(data);
    }

    public PictureAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, RegionListInfo.ProjectListBean item) {
        int position = helper.getAdapterPosition();
        item.setIsState(-1);
        TextView mTitle = helper.getView(R.id.tv_switch_title);
        EditText mTitleContent = helper.getView(R.id.tv_switch_content);
        TextView mError = helper.getView(R.id.tv_error_switch);
        TextView mNormal = helper.getView(R.id.tv_nromal_switch);
        LinearLayout mLinNormal = helper.getView(R.id.lin_switch_normal);
        RelativeLayout mSwitchNormal = helper.getView(R.id.relay_switch_normal);
        TextView mSwitchError = helper.getView(R.id.tv_switch_error);
        TextView mSee = helper.getView(R.id.tv_detail_switch_see);
        if (item.getProject() != null) {
            mTitle.setText(item.getProject());
        }
        if (item.getIsState() == 1) {
            mLinNormal.setVisibility(View.GONE);
            mSwitchNormal.setVisibility(View.VISIBLE);
            mSwitchError.setText(R.string.no_abnormal);
            mSwitchError.setTextColor(mContext.getResources().getColor(R.color.color_see));
            mSee.setVisibility(View.VISIBLE);
        } else if (item.getIsState() == 0) {
            mLinNormal.setVisibility(View.GONE);
            mSwitchNormal.setVisibility(View.VISIBLE);
            mSwitchError.setText(R.string.no_normal);
            mSwitchError.setTextColor(mContext.getResources().getColor(R.color.log_colot));
            mSee.setVisibility(View.INVISIBLE);
        }


        mError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setIsState(1);
                if (onChilkLisener != null) {
                    onChilkLisener.OnLisener(mLinNormal, mSwitchNormal, mSwitchError, mSee, mTitle, mTitleContent, item.getIsState(), position);
                }
                mFlagList.add(position);
            }
        });

        mNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setIsState(0);
                mLinNormal.setVisibility(View.GONE);
                mSwitchNormal.setVisibility(View.VISIBLE);
                mSwitchError.setText(R.string.no_normal);
                mSwitchError.setTextColor(mContext.getResources().getColor(R.color.log_colot));
                mSee.setVisibility(View.INVISIBLE);
                if (onChilkLisener != null) {
                    onChilkLisener.OnNormalLisener(mLinNormal, mSwitchNormal, mSwitchError, mSee, mTitle, mTitleContent, item.getIsState(), position);
                }
                mFlagList.add(position);
            }
        });


        mSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSeeLisener != null) {
                    onItemSeeLisener.OnLisener();
                }
            }
        });

    }


    //点击条目监听
    private onChilkLisener onChilkLisener;

    public interface onChilkLisener {
        void OnLisener(LinearLayout linearLayout, RelativeLayout relativeLayout, TextView switchError, TextView mSee, TextView title, TextView content, int state, int position);

        void OnNormalLisener(LinearLayout linearLayout, RelativeLayout relativeLayout, TextView switchError, TextView mSee, TextView title, TextView content, int state, int position);
    }

    public void setOnChilkLisener(onChilkLisener onChilkLisener) {
        this.onChilkLisener = onChilkLisener;
    }

    //点击条目监听
    private onItemSeeLisener onItemSeeLisener;

    public interface onItemSeeLisener {
        void OnLisener();
    }

    public void setOnItemSeeLisener(onItemSeeLisener onItemSeeLisener) {
        this.onItemSeeLisener = onItemSeeLisener;
    }

}
