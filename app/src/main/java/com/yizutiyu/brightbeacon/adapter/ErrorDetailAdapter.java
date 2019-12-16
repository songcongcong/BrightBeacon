package com.yizutiyu.brightbeacon.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yizutiyu.brightbeacon.R;
import com.yizutiyu.brightbeacon.base.BaseAdapter;

import java.util.List;

/**
 * @author
 * @data 2019/9/22
 */
public class ErrorDetailAdapter extends BaseAdapter<String, BaseViewHolder> {
    public ErrorDetailAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    public ErrorDetailAdapter(List<String> data) {
        super(data);
    }

    public ErrorDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView mImg = helper.getView(R.id.im_picture_item);
        String s = item.replaceAll("\"", "");
        Glide.with(mContext).load(s).into(mImg);
    }

    //点击条目监听
    private onItemChilkLisener onItemChilkLisener;

    public interface onItemChilkLisener {
        void OnLisener(ImageView imageView);
    }

    public void setOnItemChilkLisener(onItemChilkLisener onItemChilkLisener) {
        this.onItemChilkLisener = onItemChilkLisener;
    }
}
