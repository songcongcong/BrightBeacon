package com.yizutiyu.brightbeacon.eventbus;

import com.brtbeacon.sdk.BRTBeacon;
import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.callback.BRTBeaconMonitorListener;

/**
 * @author
 * @data 2019/9/24
 */
public class TimerServiceEventBus {
    private BRTBeaconManager beaconManager;

    private BRTBeaconMonitorListener   monitorListener;

    public BRTBeaconManager getBeaconManager() {
        return beaconManager;
    }

    public void setBeaconManager(BRTBeaconManager beaconManager) {
        this.beaconManager = beaconManager;
    }

    public BRTBeaconMonitorListener getMonitorListener() {
        return monitorListener;
    }

    public void setMonitorListener(BRTBeaconMonitorListener monitorListener) {
        this.monitorListener = monitorListener;
    }

    public TimerServiceEventBus(BRTBeaconManager beaconManager, BRTBeaconMonitorListener monitorListener) {
        this.beaconManager = beaconManager;
        this.monitorListener = monitorListener;
    }

}
