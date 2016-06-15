package com.zzptc.joker.baiduguard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import com.zzptc.joker.baiduguard.servers.LocationService;

/**
 * 判断用户按键的时间间隔，如果按了四次就震动 定位发送短信给紧急联系人
 */
public class ScreenReceiver extends BroadcastReceiver {

    private long screenOffTime = 0;
    private long screenOnTime = 0;
    //用户操作的次数
    private int pressCount = 0;

    public ScreenReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            screenOffTime = System.currentTimeMillis();
        }
        if (action.equals(Intent.ACTION_SCREEN_ON)) {
            screenOnTime = System.currentTimeMillis();
        }


        if (screenOnTime - screenOffTime <= 1000) {
            pressCount++;

            if (pressCount == 4) {
                pressCount = 0;

                //震动
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000);

                //启动服务：定位，发短信
                context.startService(new Intent(context, LocationService.class));

                Log.i("screenReceiver", "#################################");
            }
        }

    }
}
