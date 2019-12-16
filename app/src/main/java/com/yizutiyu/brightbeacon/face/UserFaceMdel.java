package com.yizutiyu.brightbeacon.face;

/**
 * 人脸查询Model封装
 */
@BaseModel
public class UserFaceMdel {
    @BaseUnique
    private String userId        ;
    @BaseUnique
    private String loginName     ;
    private String userName      ;
    private String userType      ;
    private String email         ;
    private String phonenumber   ;
    private String sex           ;
    private String avatar        ;
    private String password      ;
    private String salt          ;
    private String status        ;
    private String delFlag       ;
    private String loginIp       ;
    private String loginDate     ;
    private String createBy      ;
    private String createTime    ;
    private String updateBy      ;
    private String updateTime    ;
    private String remark        ;
    private String storeNum      ;
    private String orgId         ;

    @BaseUnique
    private Integer faceUserId         ;
    private String faceUserName       ;
    private String faceAge            ;
    private String faceSex            ;
    private String faceIdcard         ;
    private String facePhnoeNum      ;
    private String faceBirthday       ;
    private String faceId            ;

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public Integer getFaceUserId() {
        return faceUserId;
    }

    public void setFaceUserId(Integer faceUserId) {
        this.faceUserId = faceUserId;
    }

    public String getFaceUserName() {
        return faceUserName;
    }

    public void setFaceUserName(String faceUserName) {
        this.faceUserName = faceUserName;
    }


    public String getFaceAge() {
        return faceAge;
    }

    public void setFaceAge(String faceAge) {
        this.faceAge = faceAge;
    }

    public String getFaceSex() {
        return faceSex;
    }

    public void setFaceSex(String faceSex) {
        this.faceSex = faceSex;
    }

    public String getFaceIdcard() {
        return faceIdcard;
    }

    public void setFaceIdcard(String faceIdcard) {
        this.faceIdcard = faceIdcard;
    }

    public String getFacePhnoeNum() {
        return facePhnoeNum;
    }

    public void setFacePhnoeNum(String facePhnoeNum) {
        this.facePhnoeNum = facePhnoeNum;
    }

    public String getFaceBirthday() {
        return faceBirthday;
    }

    public void setFaceBirthday(String faceBirthday) {
        this.faceBirthday = faceBirthday;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        String sex = "其他";
        if("0".equals(getSex())){
            sex = "男";
        }else if("1".equals(getSex())){
            sex = "女";
        }
        return "姓名"+getUserName()+"性别 "+getSex()+" 电话号码 "+getPhonenumber();
    }

    public String toRead(){
        String sex = "其他";
        if("0".equals(getFaceAge())){
            sex = "男";
        }else if("1".equals(getFaceAge())){
            sex = "女";
        }
        return "姓名"+getFaceUserName()+"性别 "+getFaceAge()+" 电话号码 "+getFacePhnoeNum()+" 生日"+getFaceBirthday()+"身份证号" +
                ""+getFaceIdcard()+"";
    }
}
