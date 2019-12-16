package com.yizutiyu.brightbeacon.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.yizutiyu.brightbeacon.custom.MyHashMap;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @data 2019/9/20
 */
public class ContensUtils {

    /**
     * lastTime
     */
    private long lastTime;
    private static boolean isFirst;

    /**
     * @param activity    activity
     * @param permissions 权限数组
     * @param requestCode 申请码
     * @return true 有权限  false 无权限
     */
    public static boolean checkAndApplyfPermissionActivity(Activity activity, String[] permissions,
                                                           int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions = checkPermissions(activity, permissions);
            if (permissions != null && permissions.length > 0) {
                activity.requestPermissions(permissions, requestCode);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * @param context     上下文
     * @param permissions 权限数组
     * @return 还需要申请的权限
     */
    private static String[] checkPermissions(Context context, String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            return new String[0];
        }
        ArrayList<String> permissionLists = new ArrayList<>();
        permissionLists.addAll(Arrays.asList(permissions));
        for (int i = permissionLists.size() - 1; i >= 0; i--) {
            if (ContextCompat.checkSelfPermission(context, permissionLists.get(i))
                    == PackageManager.PERMISSION_GRANTED) {
                permissionLists.remove(i);
            }
        }

        String[] temps = new String[permissionLists.size()];
        for (int i = 0; i < permissionLists.size(); i++) {
            temps[i] = permissionLists.get(i);
        }
        return temps;
    }

    /**
     * 检查申请的权限是否全部允许
     *
     * @param grantResults 权限
     * @return 结果
     */
    public static boolean checkPermission(int[] grantResults) {
        if (grantResults == null || grantResults.length == 0) {
            return true;
        } else {
            int temp = 0;
            for (int i : grantResults) {
                if (i == PackageManager.PERMISSION_GRANTED) {
                    temp++;
                }
            }
            return temp == grantResults.length;
        }
    }

    /**
     * 删除单个应用
     *
     * @param context context
     * @param key     value
     * @param appkey  Appkey
     */
    private static void removeAppkey(Context context, String key, String appkey, SharedPreferences sharedPreferences) {
        List<String> installingApps = getAppKey(context, appkey, sharedPreferences);
        if (installingApps.size() > 0) {
            for (int i = 0; i < installingApps.size(); i++) {
                if (installingApps.get(i).equals(key)) {
                    installingApps.remove(i);
                    break;
                }
            }
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < installingApps.size(); i++) {
                stringBuffer.append(installingApps.get(i)).append(",");
            }
            String values = stringBuffer.toString();
//            AppSettings.getDefaultPackageSettings(context).putString(appkey, values);
            sharedPreferences.edit().remove(appkey).apply();
        }
    }

    /**
     * 添加应用的key
     *
     * @param context      context
     * @param key          value
     * @param uninstallKey Appkey
     */
    private static void setAppKey(Context context, String key, String uninstallKey, SharedPreferences preferences) {
        List<String> installingApps = getAppKey(context, uninstallKey, preferences);
        if (installingApps.size() >= 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < installingApps.size(); i++) {
                stringBuffer.append(installingApps.get(i)).append(",");
            }
            stringBuffer.append(key).append(",");
            String appKey = stringBuffer.toString();
            preferences.edit().putString(uninstallKey, appKey).apply();
        }
    }

    /**
     * 添加应用的key
     *
     * @param context context
     * @param value   value
     * @param appkey  Appkey
     */
    private static void setAppMapKey(Context context, MyHashMap<String> value, String appkey, SharedPreferences preferences) {
        List<MyHashMap<String>> installingApps = getAppMapKey(context, appkey, preferences);
        String charAt;
        if (installingApps.size() >= 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < installingApps.size(); i++) {
                stringBuffer.append(installingApps.get(i)).append(",");
            }
            stringBuffer.append(value).append(",");
            if (!isFirst) {
                charAt = stringBuffer.deleteCharAt(0).toString();
                isFirst = true;
            } else {
                charAt = stringBuffer.toString();
            }
            preferences.edit().putString(appkey, charAt).apply();
        }
    }

    /**
     * 获取应用的key
     *
     * @param context context
     * @param appkey  appkey
     * @return appList
     */
    private static List<MyHashMap<String>> getAppMapKey(Context context, String appkey, SharedPreferences preferences) {
        String string = preferences.getString(appkey, "");
        String str2 = string.replace(" ", "");
        List<String> list = Arrays.asList(str2.split(","));
        List appList = new ArrayList<>(list);
        return appList;
    }

    /**
     * 获取应用的key
     *
     * @param context      context
     * @param uninstallKey uninstallKey
     * @return appList
     */
    private static List<String> getAppKey(Context context, String uninstallKey, SharedPreferences preferences) {
        String string = preferences.getString(uninstallKey, "");
        String str2 = string.replace(" ", "");
        List<String> list = Arrays.asList(str2.split(","));
        List appList = new ArrayList<>(list);
        return appList;
    }


    /**
     * 获取卸栽的应用key
     *
     * @param context      context
     * @param uninstallKey uninstallKey
     * @return appList
     */
    public static List<String> getFileAppsKey(Context context, String uninstallKey, SharedPreferences preferences) {
        List<String> appList = getAppKey(context, uninstallKey, preferences);
        return appList;
    }

    /**
     * 添加卸栽的应用key
     *
     * @param context      context
     * @param key          key
     * @param uninstallKey uninstallKey
     */
    public static void setFileAppsKey(Context context, String key, String uninstallKey, SharedPreferences preferences) {
        setAppKey(context, key, uninstallKey, preferences);
    }

    /**
     * 删除卸栽的应用key
     *
     * @param context context
     */
    public static void removeFileAppsKey(Context context, SharedPreferences preferences, String key) {
//        removeAppkey(context, key, uninstallKey, preferences);
        preferences.edit().remove(key).apply();
    }


    /**
     * 获取卸栽的应用key
     *
     * @param context      context
     * @param uninstallKey uninstallKey
     * @return appList
     */
    public static List<String> getPictureAppsKey(Context context, String uninstallKey, SharedPreferences preferences) {
        List<String> appList = getAppKey(context, uninstallKey, preferences);
        return appList;
    }

    /**
     * 添加卸栽的应用key
     *
     * @param context      context
     * @param key          key
     * @param uninstallKey uninstallKey
     */
    public static void setPictureAppsKey(Context context, String key, String uninstallKey, SharedPreferences preferences) {
        setAppKey(context, key, uninstallKey, preferences);
    }

    /**
     * 删除卸栽的应用key
     *
     * @param context context
     */
    public static void removePictureAppsKey(Context context, SharedPreferences preferences, String key) {
        preferences.edit().remove(key).apply();
    }


    /**
     * 获取卸栽的应用key
     *
     * @param context      context
     * @param uninstallKey uninstallKey
     * @return appList
     */
    public static List<MyHashMap<String>> getMapAppsKey(Context context, String uninstallKey, SharedPreferences preferences) {
        List<MyHashMap<String>> appList = getAppMapKey(context, uninstallKey, preferences);
        return appList;
    }

    /**
     * 添加卸栽的应用key
     *
     * @param context context
     * @param value   value
     * @param appkey  appkey
     */
    public static void setMapAppsKey(Context context, MyHashMap<String> value, String appkey, SharedPreferences preferences) {
        setAppMapKey(context, value, appkey, preferences);
    }

    /**
     * 删除卸栽的应用key
     *
     * @param context context
     */
    public static void removeMapAppsKey(Context context, SharedPreferences preferences, String key) {
        preferences.edit().remove(key).apply();
    }


    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public static <T> void setDataList(String tag, List<MyHashMap<String>> datalist, SharedPreferences preferences) {
        if (null == datalist || datalist.size() <= 0) {
            return;
        }
        List<Map<String, String>> installingApps = getDataList(tag, preferences);
        if (installingApps.size() >= 0) {
            Gson gson = new Gson();
//            //转换成json数据，再保存
            String strJson = gson.toJson(datalist);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < installingApps.size(); i++) {
                stringBuffer.append(installingApps.get(i)).append(",~");
            }
            stringBuffer.append(strJson).append(",~");

            String charAt = stringBuffer.toString();
            preferences.edit().putString(tag, charAt).apply();

        }

    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public static <T> List<T> getDataList(String tag, SharedPreferences preferences) {
        List<T> datalist = new ArrayList<T>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }

        String str2 = strJson.replace(" ", "");
        String[] split = str2.split(",~");
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            String strip = StringUtils.strip(split[i], "[]");
            stringList.add(strip);
        }

//        Gson gson = new Gson();
//        datalist = gson.fromJson(strJson, new TypeToken<List<Map<String, String>>>() {
//        }.getType());
        return (List<T>) stringList;
    }


    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public static <T> void setPictureList(String tag, List<String> datalist, SharedPreferences preferences) {
        if (null == datalist || datalist.size() <= 0) {
            return;
        }
        List<Map<String, String>> installingApps = getDataList(tag, preferences);
        if (installingApps.size() >= 0) {
            Gson gson = new Gson();
//            //转换成json数据，再保存
            String strJson = gson.toJson(datalist);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < installingApps.size(); i++) {
                stringBuffer.append(installingApps.get(i)).append(",~");
            }
            stringBuffer.append(strJson).append(",~");

            String charAt = stringBuffer.toString();
            preferences.edit().putString(tag, charAt).apply();

        }

    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public static <T> List<T> getPictureDataList(String tag, SharedPreferences preferences) {
        List<T> datalist = new ArrayList<T>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }

        String str2 = strJson.replace(" ", "");
        String[] split = str2.split(",~");
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            String strip = StringUtils.strip(split[i], "[]");
            stringList.add(strip);
        }


//        Gson gson = new Gson();
//        datalist = gson.fromJson(strJson, new TypeToken<List<Map<String, String>>>() {
//        }.getType());
        return (List<T>) stringList;
    }



    /**
     * 计算按钮点击间隔时间
     *
     * @return boolean
     */
    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastTime < 2000) {
            return true;
        }
        lastTime = time;
        return false;
    }
}
