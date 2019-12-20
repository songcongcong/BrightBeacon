package com.yizutiyu.brightbeacon.info;

public class VideoInfo {

    /**
     * video : http://yizutiyu.oss-cn-beijing.aliyuncs.com/xunjian/20190828142318
     * success : true
     */

    private String video;
    private boolean success;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "PictureInfo{" +
                "video='" + video + '\'' +
                ", success=" + success +
                '}';
    }

}
