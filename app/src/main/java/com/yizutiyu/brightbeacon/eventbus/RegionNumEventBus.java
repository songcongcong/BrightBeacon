package com.yizutiyu.brightbeacon.eventbus;

/**
 * 记录已完成区域的数量
 */
public class RegionNumEventBus {
    /**
     * RegionNumEventBus
     */
    private int mNum;

    /**
     * RegionNumEventBus
     * @param mNum mNum
     */
    public RegionNumEventBus(int mNum) {
        this.mNum = mNum;
    }

    /**
     * getmNum
     * @return int
     */
    public int getmNum() {
        return mNum;
    }

    /**
     * setmNum
     * @param mNum mNum
     */
    public void setmNum(int mNum) {
        this.mNum = mNum;
    }
}
