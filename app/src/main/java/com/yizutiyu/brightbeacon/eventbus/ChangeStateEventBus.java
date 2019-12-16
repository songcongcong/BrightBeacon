package com.yizutiyu.brightbeacon.eventbus;

import com.brtbeacon.sdk.BRTBeacon;

/**
 * @author
 * @data 2019/9/24
 */
public class ChangeStateEventBus {
    private boolean isStare;
    private String mAdress;


    public ChangeStateEventBus(boolean isStare, String mAdress) {
        this.isStare = isStare;
        this.mAdress = mAdress;
    }

    public String getmAdress() {
        return mAdress;
    }

    public void setmAdress(String mAdress) {
        this.mAdress = mAdress;
    }

    public boolean isStare() {
        return isStare;
    }

    public void setStare(boolean stare) {
        isStare = stare;
    }
}
