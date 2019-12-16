package com.yizutiyu.brightbeacon.info;

public class PictureInfo {

    /**
     * image : http://yizutiyu.oss-cn-beijing.aliyuncs.com/xunjian/20190828142318
     * success : true
     */

    private String image;
    private boolean success;

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "PictureInfo{" +
                "image='" + image + '\'' +
                ", success=" + success +
                '}';
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
