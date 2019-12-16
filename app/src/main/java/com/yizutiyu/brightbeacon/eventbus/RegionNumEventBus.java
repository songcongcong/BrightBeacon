package com.yizutiyu.brightbeacon.eventbus;

/**
 * 记录已完成区域的数量
 */
public class RegionNumEventBus {
    /**
     * RegionNumEventBus
     */
    private int mNum;

    public RegionNumEventBus(int mNum) {
        this.mNum = mNum;
    }

    public int getmNum() {
        return mNum;
    }

    public void setmNum(int mNum) {
        this.mNum = mNum;
    }
}
