package com.yizutiyu.brightbeacon.base;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * Description:
 * Created by RockPhoenixHIAPAD on 2017/12/28 on 17:02.
 */

public abstract class BaseAdapter<T,K extends BaseViewHolder> extends BaseQuickAdapter<T,K> {

    public BaseAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        init();
    }

    public BaseAdapter(@Nullable List<T> data) {
        super(data);
        init();
    }

    public BaseAdapter(int layoutResId) {
        super(layoutResId);
        init();
    }

    private void init(){
        setEnableLoadMore(true);
        openLoadAnimation();//开启默认动画载入（仅开启加载新item时开启动画）
    }
}
