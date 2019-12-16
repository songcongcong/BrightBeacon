package com.yizutiyu.brightbeacon.info;

/**
 * @author
 * @data 2019/9/22
 */
public class SuccessInfo {
    /**
     * success : true
     */

    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "SuccessInfo{" +
                "success=" + success +
                '}';
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
