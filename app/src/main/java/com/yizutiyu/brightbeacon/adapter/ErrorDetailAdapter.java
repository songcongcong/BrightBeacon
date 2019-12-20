package com.yizutiyu.brightbeacon.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.base.BaseAdapter;

import java.util.List;

/**
 * @author scc
 * @data 2019/9/22
 */
public class ErrorDetailAdapter extends BaseAdapter<String, BaseViewHolder> {
    /**
     * ErrorDetailAdapter
     * @param layoutResId layoutResId
     * @param data data
     */
    public ErrorDetailAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    /**
     * ErrorDetailAdapter
     * @param data data
     */
    public ErrorDetailAdapter(List<String> data) {
        super(data);
    }

    /**
     * ErrorDetailAdapter
     * @param layoutResId layoutResId
     */
    public ErrorDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView mImg = helper.getView(R.id.im_picture_item);
        String s = item.replaceAll("\"", "");
        Glide.with(mContext).load(s).into(mImg);
    }

    /**
     * 点击条目监听
     */
    private onItemChilkLisener onItemChilkLisener;

    /**
     * onItemChilkLisener
     */
    public interface onItemChilkLisener {
        /**
         * OnLisener
         * @param imageView imageView
         */
        void OnLisener(ImageView imageView);
    }

    /**
     * setOnItemChilkLisener
     * @param onItemChilkLisener onItemChilkLisener
     */
    public void setOnItemChilkLisener(onItemChilkLisener onItemChilkLisener) {
        this.onItemChilkLisener = onItemChilkLisener;
    }
}
