package com.example.retrofitmvplibrary.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * Created by RockPhoenixHIAPAD on 2017/12/4 on 19:04.
 */

public class ActivityContainer {

    private static List<Activity> activityList = new LinkedList<>();

    /**
     * 添加到Activity容器中
     */
    public static void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    /**
     * 便利所有Activigty并finish
     */
    public static void finishActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
    }

    /**
     * 结束指定的Activity
     */
    public static void finishSingleActivity(Activity activity) {
        if (activity != null) {
            if (activityList.contains(activity)) {
                activityList.remove(activity);
            }
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity 在遍历一个列表的时候不能执行删除操作，所有我们先记住要删除的对象，遍历之后才去删除。
     */
    public static void finishSingleActivityByClass(Class<?> cls) {
        Activity tempActivity = null;
        for (Activity activity : activityList) {
            if (activity.getClass().equals(cls)) {
                tempActivity = activity;
            }
        }

        finishSingleActivity(tempActivity);
    }

    /**
     * 判断一个Activity 是否存在
     *
     * @param clz
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static <T extends Activity> boolean isActivityExist(Class<T> clz) {
        boolean res;
        Activity activity = getActivity(clz);
        if (activity == null) {
            res = false;
        } else {
            if (activity.isFinishing() || activity.isDestroyed()) {
                res = false;
            } else {
                res = true;
            }
        }

        return res;
    }

    /**
     * 获得指定activity实例
     *
     * @param clazz Activity 的类对象
     * @return activity实例
     */
    public static <T extends Activity> T getActivity(Class clazz) {
        int index = -1;
        for (int i = 0; i < activityList.size(); i++) {
            if (activityList.get(i).getClass().getSimpleName().equals(clazz.getSimpleName())) {
                index = i;
            }
        }
        if (index == -1) {
            return null;
        } else {
            return (T) activityList.get(index);
        }
    }

    public static int size() {
        return activityList.size();
    }

}
