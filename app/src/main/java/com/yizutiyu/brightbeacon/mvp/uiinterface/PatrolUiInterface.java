package com.yizutiyu.brightbeacon.mvp.uiinterface;

import com.example.retrofitmvplibrary.base.BaseUiInterface;
import com.yizutiyu.brightbeacon.info.FaceDateMessageInfo;
import com.yizutiyu.brightbeacon.info.RegionListInfo;
import com.yizutiyu.brightbeacon.info.SuccessInfo;

import java.util.List;

/**
 * @author
 * @data 2019/9/20
 */
public interface PatrolUiInterface extends BaseUiInterface {
    /**
     * saveBluetoothSuccess
     * @param data data
     */
    void saveBluetoothSuccess(String data);

    /**
     * getRegionListSuccess
     * @param regionListInfo regionListInfo
     */
    void getRegionListSuccess(List<RegionListInfo> regionListInfo);

    /**
     * submitResuleSuccess
     * @param successInfo successInfo
     */
    void submitResuleSuccess(SuccessInfo successInfo);

    /**
     * getFaceData
     * @param faceDateMessageInfo faceDateMessageInfo
     * @param position position
     */
    void getFaceData(FaceDateMessageInfo faceDateMessageInfo, int position);
}
