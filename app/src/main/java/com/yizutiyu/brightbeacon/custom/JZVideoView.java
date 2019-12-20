package com.yizutiyu.brightbeacon.custom;

import android.content.Context;
import android.util.AttributeSet;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayerStandard;

public class JZVideoView extends JZVideoPlayerStandard {
 
public JZVideoView(Context context) {
 
  super(context);
 
}
 
public JZVideoView(Context context, AttributeSet attrs) {
 
  super(context, attrs);
 
}

    @Override
    public int getLayoutId() {
        return super.getLayoutId();
    }
}