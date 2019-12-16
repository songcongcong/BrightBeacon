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
public class ErrorPictureAdapter extends BaseAdapter<Integer, BaseViewHolder> {
    public ErrorPictureAdapter(int layoutResId, List<Integer> data) {
        super(layoutResId, data);
    }

    public ErrorPictureAdapter(List<Integer> data) {
        super(data);
    }

    public ErrorPictureAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        ImageView mImg = helper.getView(R.id.im_picture_item);
        Glide.with(mContext).load(item).into(mImg);
        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemChilkLisener != null) {
                    onItemChilkLisener.OnLisener(mImg);
                }
            }
        });
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
