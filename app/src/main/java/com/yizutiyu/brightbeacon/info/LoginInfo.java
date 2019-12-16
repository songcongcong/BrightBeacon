package com.yizutiyu.brightbeacon.info;

import java.util.List;

/**
 * @author
 * @data 2019/9/18
 */
public class LoginInfo {
    /**
     * code : 200
     * data : [{"store_id":"eyJhbGciOiJIUzI1NiJ9.eyJzdGEiOjE1Njg3ODcyMDcxNzksInN0b3JlSWQiOiIzIiwiZXhwIjo2ODA5MjkyNDQ5MTQ0NDQzOTE1fQ.i6EMb2d9LvyVpTARElisf81hzTASNOqS8iGyG7y1kro","emergency_contact_number":"31111111111","birthday":"1980/3/2","image":"http://yizutiyu.oss-cn-beijing.aliyuncs.com/huiji/touxiang/20190719155843","id_number":"370882147147447111","emergency_contact_relationship":"3","address":"2","create_time":"1970-01-01","city":"110100","sex":"1","autograph":"2","password":"eyJhbGciOiJIUzI1NiJ9.eyJzdGEiOjE1Njg3ODcyMDcxODAsImV4cCI6MTU3MDkzNDY5MDgyNywic2ltcGxlSWQiOiIxMjM0NTYifQ.jM4OyHOMbpQNpy-voKuaVkJekyNCUxvi_UOn5bnwSdQ","employee_type":"1","province":"110000","work_state":"0","org_id":"234892mid","name":"李飞","nickname":"李飞昵称","phone_number":"18500198888","id":"eyJhbGciOiJIUzI1NiJ9.eyJzdGEiOjE1Njg3ODcyMDcxODAsImV4cCI6MTU3MDkzNDY5MDgyNywic2ltcGxlSWQiOiIxIn0.PizNUXiJif4Yx1dHLZwOTbVv8AgaopWMTr1ozoMvnKA","state":"1","age":"2","emergency_contact":"3"}]
     */

    private String code;

    @Override
    public String toString() {
        return "LoginInfo{" +
                "code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "store_id='" + store_id + '\'' +
                    ", emergency_contact_number='" + emergency_contact_number + '\'' +
                    ", birthday='" + birthday + '\'' +
                    ", image='" + image + '\'' +
                    ", id_number='" + id_number + '\'' +
                    ", emergency_contact_relationship='" + emergency_contact_relationship + '\'' +
                    ", address='" + address + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", city='" + city + '\'' +
                    ", sex='" + sex + '\'' +
                    ", autograph='" + autograph + '\'' +
                    ", password='" + password + '\'' +
                    ", employee_type='" + employee_type + '\'' +
                    ", province='" + province + '\'' +
                    ", work_state='" + work_state + '\'' +
                    ", org_id='" + org_id + '\'' +
                    ", name='" + name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", phone_number='" + phone_number + '\'' +
                    ", id='" + id + '\'' +
                    ", state='" + state + '\'' +
                    ", age='" + age + '\'' +
                    ", emergency_contact='" + emergency_contact + '\'' +
                    '}';
        }

        /**
         * store_id : eyJhbGciOiJIUzI1NiJ9.eyJzdGEiOjE1Njg3ODcyMDcxNzksInN0b3JlSWQiOiIzIiwiZXhwIjo2ODA5MjkyNDQ5MTQ0NDQzOTE1fQ.i6EMb2d9LvyVpTARElisf81hzTASNOqS8iGyG7y1kro
         * emergency_contact_number : 31111111111
         * birthday : 1980/3/2
         * image : http://yizutiyu.oss-cn-beijing.aliyuncs.com/huiji/touxiang/20190719155843
         * id_number : 370882147147447111
         * emergency_contact_relationship : 3
         * address : 2
         * create_time : 1970-01-01
         * city : 110100
         * sex : 1
         * autograph : 2
         * password : eyJhbGciOiJIUzI1NiJ9.eyJzdGEiOjE1Njg3ODcyMDcxODAsImV4cCI6MTU3MDkzNDY5MDgyNywic2ltcGxlSWQiOiIxMjM0NTYifQ.jM4OyHOMbpQNpy-voKuaVkJekyNCUxvi_UOn5bnwSdQ
         * employee_type : 1
         * province : 110000
         * work_state : 0
         * org_id : 234892mid
         * name : 李飞
         * nickname : 李飞昵称
         * phone_number : 18500198888
         * id : eyJhbGciOiJIUzI1NiJ9.eyJzdGEiOjE1Njg3ODcyMDcxODAsImV4cCI6MTU3MDkzNDY5MDgyNywic2ltcGxlSWQiOiIxIn0.PizNUXiJif4Yx1dHLZwOTbVv8AgaopWMTr1ozoMvnKA
         * state : 1
         * age : 2
         * emergency_contact : 3
         */

        private String store_id;
        private String emergency_contact_number;
        private String birthday;
        private String image;
        private String id_number;
        private String emergency_contact_relationship;
        private String address;
        private String create_time;
        private String city;
        private String sex;
        private String autograph;
        private String password;
        private String employee_type;
        private String province;
        private String work_state;
        private String org_id;
        private String name;
        private String nickname;
        private String phone_number;
        private String id;
        private String state;
        private String age;
        private String emergency_contact;

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getEmergency_contact_number() {
            return emergency_contact_number;
        }

        public void setEmergency_contact_number(String emergency_contact_number) {
            this.emergency_contact_number = emergency_contact_number;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getId_number() {
            return id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }

        public String getEmergency_contact_relationship() {
            return emergency_contact_relationship;
        }

        public void setEmergency_contact_relationship(String emergency_contact_relationship) {
            this.emergency_contact_relationship = emergency_contact_relationship;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmployee_type() {
            return employee_type;
        }

        public void setEmployee_type(String employee_type) {
            this.employee_type = employee_type;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getWork_state() {
            return work_state;
        }

        public void setWork_state(String work_state) {
            this.work_state = work_state;
        }

        public String getOrg_id() {
            return org_id;
        }

        public void setOrg_id(String org_id) {
            this.org_id = org_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getEmergency_contact() {
            return emergency_contact;
        }

        public void setEmergency_contact(String emergency_contact) {
            this.emergency_contact = emergency_contact;
        }
    }
}
