package com.zzptc.joker.baiduguard.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.bean.UpdateInfo;
import com.zzptc.joker.baiduguard.constant.Constants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class UpdateDialogActivity extends Activity {

    private ImageView load_one, load_two;
    private static final String HTTPURL = "";
    private int currentVersionCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dialog);

        initAnim();

        checkNetwork();


    }


    //检查是否有网络,以及请求服务器 来判断是否要更新版本
    private void checkNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        if (networkInfo != null) {
            RequestParams requestParams = new RequestParams(HTTPURL);
            x.http().get(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    UpdateInfo updateInfo = new UpdateInfo();
                    if (!result.equals("")) {
                        updateInfo = new Gson().fromJson(result, UpdateInfo.class);

                        try {
                            currentVersionCode = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS).versionCode;
                            if (updateInfo.getVersionCode() > currentVersionCode) {

                                Intent intent = new Intent();
                                intent.putExtra("updateInfo", updateInfo);
                                setResult(Constants.AVAILABLE_UPDATE, intent);
                                finish();

                            } else {
//是最新版本
                                setResult(Constants.ISNEW_VERSION);
                                finish();
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    setResult(Constants.ERROR_NETWORK);
                    finish();
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    setResult(Constants.CANCEL_DOWNLOAD);
                    finish();

                }

                @Override
                public void onFinished() {

                }
            });


        } else {
            setResult(Constants.NOT_NETWORK);
            finish();
        }
    }


    //更新动画的启动
    public void initAnim() {
        load_one = (ImageView) findViewById(R.id.load_one);
        load_two = (ImageView) findViewById(R.id.load_two);


        final Animation oneAnimation = AnimationUtils.loadAnimation(this, R.anim.update_dialog_anim);
        final Animation twoAnimation = AnimationUtils.loadAnimation(this, R.anim.update_dialog_anim);
        load_one.startAnimation(oneAnimation);

        oneAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                load_two.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                load_two.startAnimation(twoAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        twoAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                load_one.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                load_one.startAnimation(oneAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
