package com.zzptc.joker.baiduguard.task;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.zzptc.joker.baiduguard.bean.AppInfo;
import com.zzptc.joker.baiduguard.utils.MemoryUtils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 2016/5/30/030.
 * 用synctask 扫描用户应用
 */
public class ScanAppTask {

    PackageManager packageManager;
    ActivityManager activityManager;
    private Context context;
    List<AppInfo> appInfoList;

    public ScanAppTask() {
        context = x.app().getApplicationContext();
        packageManager = context.getPackageManager();
        activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
    }

    public interface ScanAppListener {
        void onScanStart(Context context);

        void onScanRunning(Context context, int current, int total);

        void onScanOver(Context context, List<AppInfo> appInfoList);
    }

    private ScanAppListener scanAppListener;

    public void setScanAppListener(ScanAppListener scanAppListener) {
        this.scanAppListener = scanAppListener;
    }

    public void excute() {
        //只能在主线程里执行
        new Scannow().execute();
    }


    //扫描应用的task
    class Scannow extends AsyncTask<Void, Integer, List<AppInfo>> {


        //        执行之前调用的方法
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (scanAppListener != null) {
                scanAppListener.onScanStart(context);
            }
        }

        //        后代执行，线程
        @Override
        protected List<AppInfo> doInBackground(Void... params) {
            appInfoList = new ArrayList<>();


            int progress = 0;

            //ctrl + alt + v  / alt + enter + enter (分号的前面)  快速生成局部变量
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcesses) {
//                //更新进度
//                publishProgress(++progress, runningAppProcesses.size());

                System.out.println(processInfo.processName);



                //得到进程名




                ApplicationInfo appInfo = null;
                try {
                    appInfo = packageManager.getApplicationInfo(processInfo.processName, 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }




                    if(appInfo!=null){

                        AppInfo appProcessInfo = new AppInfo();

                        //图标
                        appProcessInfo.setAppIcon(appInfo.loadIcon(packageManager));
                        //应用程序的名称
                        appProcessInfo.setAppName(appInfo.loadLabel(packageManager).toString());

                        appProcessInfo.setPackageName(processInfo.processName);

                        long memroy = activityManager.getProcessMemoryInfo(new int[]{processInfo.pid})[0].getTotalPrivateDirty() * 1024;
                        appProcessInfo.setAppMemory(MemoryUtils.convertStorage(memroy));

                        appInfoList.add(appProcessInfo);

                    }








                //获取内存





            }




            return appInfoList;
        }

        //      执行完成
        @Override
        protected void onPostExecute(List<AppInfo> appInfos) {
            super.onPostExecute(appInfos);

            if (scanAppListener != null) {
                scanAppListener.onScanOver(context,appInfos);
            }
        }

        //     进度更新
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }


    }
}
