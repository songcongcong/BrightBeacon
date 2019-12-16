package com.yizutiyu.brightbeacon.info;

/**
 * @author
 * @data 2019/9/22
 */
public class FaceDateMessageInfo {
    /**
     * code : 200
     * data:true
     */

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String code;
    private boolean is_exist;

    public boolean isIs_exist() {
        return is_exist;
    }

    public void setIs_exist(boolean is_exist) {
        this.is_exist = is_exist;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

}
