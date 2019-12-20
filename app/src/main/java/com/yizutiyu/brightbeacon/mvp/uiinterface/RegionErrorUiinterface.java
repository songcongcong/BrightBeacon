package com.yizutiyu.brightbeacon.mvp.uiinterface;

import com.example.retrofitmvplibrary.base.BaseUiInterface;
import com.yizutiyu.brightbeacon.info.PictureInfo;
import com.yizutiyu.brightbeacon.info.VideoInfo;

/**
 * @author
 * @data 2019/9/22
 */
public interface RegionErrorUiinterface extends BaseUiInterface {
    void updataimgSuccess(PictureInfo pictureInfo);
    void uploadVideoSuccess(VideoInfo videoInfo);
}
