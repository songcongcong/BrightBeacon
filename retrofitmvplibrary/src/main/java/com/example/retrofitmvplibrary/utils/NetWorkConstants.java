package com.example.retrofitmvplibrary.utils;

/**
 * Description:
 * Created by RockPhoenixHIAPAD on 2017/12/18 on 10:48.
 */

public class NetWorkConstants {

    public static final int VIEW_THROTTLE_TIME = 50;

    /**
     * 主站域名
     */
//    private static final String MAIN_HOST_TEST = "192.168.1.159" + ":7777";
    private static final String MAIN_HOST_TEST = "test.dykj.gooday123.com";
//    private static final String MAIN_HOST_TEST = "shangchao.qt.hg.jergavin.com";
    /**
     * API主机域名
     */
    private static final String MAIN_HOST_FOR_PING = MAIN_HOST_TEST;

    public static final String MAIN_HOST_URL = "http://" + MAIN_HOST_FOR_PING;

    public static final String WEB_BASE_URL = MAIN_HOST_URL + "/service/";
    /**
     * 请求URL
     */
//    public   static String MURL = "https://oper.yizutiyu.com/";
    public   static String MURL = "http://192.168.0.106/";
//    public static String MURL = "https://dev.yizutiyu.com/";


//        public static final String UPDATE_PIC_URL = "http://ht.dykj.gooday123.com/ht/";
//    public static final String UPDATE_PIC_URL = "http://192.168.1.159:8081/ht/";

    public static final String UPDATE_PIC_URL = "http://ht.dykj.gooday123.com/ht/";
    public static final String DOWNLOAD_BASE_URL = "http://ht.dykj.gooday123.com/uplode/";


//    public static final String DOWNLOAD_BASE_URL = "http://192.168.1.159:8081/uplode/";
//    public static final String DOWNLOAD_BASE_URL = "http://ht.dykj.gooday123.com/uplode/";
}
