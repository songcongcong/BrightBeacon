package com.yizutiyu.brightbeacon.info;

import com.brtbeacon.sdk.BRTBeacon;

/**
 * @author
 * @data 2019/9/24
 */
public class BrtBeaconInfo {
    @Override
    public String toString() {
        return "BrtBeaconInfo{" +
                "brtBeacon=" + brtBeacon +
                '}';
    }

    private BRTBeacon brtBeacon;

    public BRTBeacon getBrtBeacon() {
        return brtBeacon;
    }

    public BrtBeaconInfo(BRTBeacon brtBeacon) {
        this.brtBeacon = brtBeacon;
    }

    public void setBrtBeacon(BRTBeacon brtBeacon) {
        this.brtBeacon = brtBeacon;
    }
}
