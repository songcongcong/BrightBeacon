package com.yizutiyu.brightbeacon.eventbus;


/**
 * @author
 * @data 2019/9/24
 */
public class ChangeStateEventBus {
    /**
     * isStare
     */
    private boolean isStare;
    /**
     * mAdress
     */
    private String mAdress;

    /**
     * ChangeStateEventBus
     * @param isStare isStare
     * @param mAdress mAdress
     */
    public ChangeStateEventBus(boolean isStare, String mAdress) {
        this.isStare = isStare;
        this.mAdress = mAdress;
    }

    /**
     * getmAdress
     * @return String
     */
    public String getmAdress() {
        return mAdress;
    }

    /**
     * setmAdress
     * @param mAdress mAdress
     */
    public void setmAdress(String mAdress) {
        this.mAdress = mAdress;
    }

    /**
     * isStare
     * @return boolean
     */
    public boolean isStare() {
        return isStare;
    }

    /**
     * setStare
     * @param stare stare
     */
    public void setStare(boolean stare) {
        isStare = stare;
    }
}
