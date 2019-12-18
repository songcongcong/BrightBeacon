package com.yizutiyu.brightbeacon;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.yizutiyu.brightbeacon.eventbus.TimerServiceEventBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author
 * @data 2019/12/18
 */
public class TimeService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("song", "onStartCommand：:");

//            startMonit();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    // 刷新标题UI
    @Subscribe(sticky = true )
    public void onMessageTimerEvent(TimerServiceEventBus serviceEventBus) {
        Log.d("song", "接收数据：:"+serviceEventBus);
        if (serviceEventBus != null) {
            try {
//            while (true) {
//                if (isSuccess) {
//                    return;
//                }
//                Thread.sleep(10);
                Log.d("song", "开始：:");
                serviceEventBus.getBeaconManager().setBeaconMonitorListener(serviceEventBus.getMonitorListener());
                serviceEventBus.getBeaconManager().startMonitoring();
//            }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("song", "异常：:" + e.toString());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
