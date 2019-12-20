package com.yizutiyu.brightbeacon.api;

import com.example.retrofitmvplibrary.base.BaseResponse;
import com.yizutiyu.brightbeacon.info.FaceDateMessageInfo;
import com.yizutiyu.brightbeacon.info.SuccessInfo;
import com.yizutiyu.brightbeacon.info.LoginInfo;
import com.yizutiyu.brightbeacon.info.PictureInfo;
import com.yizutiyu.brightbeacon.info.RegionListInfo;
import com.yizutiyu.brightbeacon.info.VideoInfo;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 作者：宋聪聪 on 2019/9/2.
 */

public interface RetrofitApi {
    /**
     * 用户登录
     *
     * @param telephone telephone
     * @param pwd pwd
     * @return LoginInfo
     */
    @POST("ezapp/login2")
    @FormUrlEncoded
    Observable<LoginInfo> normalLogin(@Field("usernameOfphone") String telephone,
                                      @Field("password") String pwd);

    /**
     * 保存扫描到的设备
     *
     * @param key  key
     * @param name name
     * @return String
     */
    @POST("ezapp/inspection/saveInspectionBluetoothMessage")
    @FormUrlEncoded
    Observable<String> saveBluetoothMessage(@Field("bluetooth_key") String key,
                                            @Field("region") String name);

    /**
     * 设备列表页
     * @param name name
     * @return BaseResponse<List<RegionListInfo>>
     */
    @POST("ezapp/inspection/getInspectionBluetoothMessage")
    @FormUrlEncoded
    Observable<BaseResponse<List<RegionListInfo>>> getRegionList(@Field("id") String name);

    /**
     * 添加设备接口
     * @param key key
     * @param name name
     * @return  SuccessInfo
     */
    @POST("ezapp/inspection/saveInspectionBluetoothMessage")
    @FormUrlEncoded
    Observable<SuccessInfo> addRegion(@Field("bluetooth_key") String key,
                                      @Field("region") String name);

    /**
     * 上传图片接口
     * @param parts parts
     * @return PictureInfo
     */
    @POST("ezapp/inspection/uploadImage")
    @Multipart
    Observable<PictureInfo> uploadImg(@Part MultipartBody.Part parts);

    /**
     * 上传录制视频接口
     * @param parts parts
     * @return VideoInfo
     */
    @POST("ezapp/inspection/uploadVideo")
    @Multipart
    Observable<VideoInfo> uploadVideo(@Part MultipartBody.Part parts);

    /**
     * 提交巡检结果
     * @param message message
     * @param startTime startTime
     * @param endTime endTime
     * @param state state
     * @param detail detail
     * @return SuccessInfo
     */
    @POST("ezapp/inspection/saveInspectionMessage")
    @FormUrlEncoded
    Observable<SuccessInfo> submitResult(@Field("message") String message,
                                         @Field("start_time") String startTime,
                                         @Field("end_time") String endTime,
                                         @Field("state") String state,
                                         @Field("detail") String detail);

    /**
     * 提交巡检结果
     * @param face face
     * @return FaceDateMessageInfo
     */
    @POST("ezapp/inspection/getInspectionFaceCompare")
    @FormUrlEncoded
    Observable<FaceDateMessageInfo> getFaceData(@Field("faceId") String face);

}
