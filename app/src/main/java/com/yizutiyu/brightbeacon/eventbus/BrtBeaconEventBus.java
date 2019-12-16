package com.yizutiyu.brightbeacon.eventbus;

import com.brtbeacon.sdk.BRTBeacon;

/**
 * @author
 * @data 2019/9/24
 */
public class BrtBeaconEventBus {
    private BRTBeacon brtBeacon;

    public BRTBeacon getBrtBeacon() {
        return brtBeacon;
    }

    public BrtBeaconEventBus(BRTBeacon brtBeacon) {
        this.brtBeacon = brtBeacon;
    }

    public void setBrtBeacon(BRTBeacon brtBeacon) {
        this.brtBeacon = brtBeacon;
    }
}
