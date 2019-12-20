package com.yizutiyu.brightbeacon.eventbus;

import com.brtbeacon.sdk.BRTBeacon;

/**
 * @author
 * @data 2019/9/24
 */
public class BrtBeaconEventBus {
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
     * BrtBeaconEventBus
     * @param brtBeacon brtBeacon
     */
    public BrtBeaconEventBus(BRTBeacon brtBeacon) {
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
