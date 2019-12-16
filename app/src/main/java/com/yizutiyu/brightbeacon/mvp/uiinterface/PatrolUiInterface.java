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
    void saveBluetoothSuccess(String data);
    void getRegionListSuccess(List<RegionListInfo> regionListInfo);
    void submitResuleSuccess(SuccessInfo successInfo);
    void getFaceData(FaceDateMessageInfo faceDateMessageInfo, int position);
}
