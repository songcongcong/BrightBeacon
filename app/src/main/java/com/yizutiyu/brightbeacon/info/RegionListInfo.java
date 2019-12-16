package com.yizutiyu.brightbeacon.info;

import android.os.Parcel;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 * @data 2019/9/21
 */
public class RegionListInfo implements Serializable {
    @Override
    public String toString() {
        return "DataBean{" +
                "id=" + id +
                ", region='" + region + '\'' +
                ", bluetoothKey='" + bluetoothKey + '\'' +
                ", isRegionNum=" + isRegionNum +
                ", isFlag=" + isFlag +
                ", isState=" + isState +
                ", projectList=" + projectList +
                '}';
    }

    /**
     * id : 1
     * region : 313123123
     * bluetoothKey : dsaad
     * projectList : [{"project":"发多少否"},{"project":"固定法规定的"},{"project":"2131"}]
     */

    private int id;
    private String region;
    private String bluetoothKey;
    private boolean isRegionNum;
    private int isState = -1;
    private boolean isFlag = false;

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public boolean isRegionNum() {
        return isRegionNum;
    }

    public void setRegionNum(boolean regionNum) {
        isRegionNum = regionNum;
    }

    public int getIsState() {
        return isState;
    }

    public void setIsState(int isState) {
        this.isState = isState;
    }

    private List<ProjectListBean> projectList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBluetoothKey() {
        return bluetoothKey;
    }

    public void setBluetoothKey(String bluetoothKey) {
        this.bluetoothKey = bluetoothKey;
    }

    public List<ProjectListBean> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectListBean> projectList) {
        this.projectList = projectList;
    }

    public static class ProjectListBean implements Serializable {
        /**
         * project : 发多少否
         */

        private String project;
        private int isState = -1;

        public int getIsState() {
            return isState;
        }

        public void setIsState(int isState) {
            this.isState = isState;
        }

        protected ProjectListBean(Parcel in) {
            project = in.readString();
        }

        @Override
        public String toString() {
            return "ProjectListBean{" +
                    "project='" + project + '\'' +
                    '}';
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }
    }
}
