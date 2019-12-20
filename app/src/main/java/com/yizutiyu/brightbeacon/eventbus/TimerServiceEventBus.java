package com.yizutiyu.brightbeacon.eventbus;

import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.callback.BRTBeaconMonitorListener;

/**
 * @author scc
 * @data 2019/9/24
 */
public class TimerServiceEventBus {
    /**
     * beaconManager
     */
    private BRTBeaconManager beaconManager;
    /**
     * monitorListener
     */
    private BRTBeaconMonitorListener   monitorListener;

    /**
     * getBeaconManager
     * @return BRTBeaconManager
     */
    public BRTBeaconManager getBeaconManager() {
        return beaconManager;
    }

    /**
     * setBeaconManager
     * @param beaconManager beaconManager
     */
    public void setBeaconManager(BRTBeaconManager beaconManager) {
        this.beaconManager = beaconManager;
    }

    /**
     * getMonitorListener
     * @return BRTBeaconMonitorListener
     */
    public BRTBeaconMonitorListener getMonitorListener() {
        return monitorListener;
    }

    /**
     * setMonitorListener
     * @param monitorListener monitorListener
     */
    public void setMonitorListener(BRTBeaconMonitorListener monitorListener) {
        this.monitorListener = monitorListener;
    }

    /**
     * TimerServiceEventBus
     * @param beaconManager beaconManager
     * @param monitorListener monitorListener
     */
    public TimerServiceEventBus(BRTBeaconManager beaconManager, BRTBeaconMonitorListener monitorListener) {
        this.beaconManager = beaconManager;
        this.monitorListener = monitorListener;
    }

}
