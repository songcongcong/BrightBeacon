package com.yizutiyu.brightbeacon.info;

import com.brtbeacon.sdk.BRTBeacon;

/**
 * @author
 * @data 2019/9/24
 */
public class BrtBeaconInfo {

    @Override
    public String toString() {
        return "BrtBeaconInfo{"
                + "brtBeacon=" + brtBeacon
                + '}';
    }

    /**
     * brtBeacon
     */
    private BRTBeacon brtBeacon;

    /**
     * getBrtBeacon
     * @return BRTBeacon
     */
    public BRTBeacon getBrtBeacon() {
        return brtBeacon;
    }

    /**
     * BrtBeaconInfo
     * @param brtBeacon brtBeacon
     */
    public BrtBeaconInfo(BRTBeacon brtBeacon) {
        this.brtBeacon = brtBeacon;
    }

    /**
     * setBrtBeacon
     * @param brtBeacon brtBeacon
     */
    public void setBrtBeacon(BRTBeacon brtBeacon) {
        this.brtBeacon = brtBeacon;
    }
}
