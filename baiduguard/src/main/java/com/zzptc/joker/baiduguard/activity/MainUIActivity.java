package com.zzptc.joker.baiduguard.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.baseviews.DanceWageTimer;
import com.zzptc.joker.baiduguard.baseviews.RatingBar;
import com.zzptc.joker.baiduguard.baseviews.RatingView;
import com.zzptc.joker.baiduguard.receiver.ScreenReceiver;

import org.xutils.x;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/*
* 主界面activity,如果有了紧急联系人，就会注册广播接收者，监听用户按了四次电源键就发送紧急短信*/
public class MainUIActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Toolbar toolbar;
    private TextView up_box, tv_rate, parent_protect, tv_speedup,garbageClean;
    private RatingView ratingView;
    private RatingBar sofeBar, fluentBar, cleanBar;
    private RelativeLayout bottomLayout;
    private ScreenReceiver screenReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);




        initViews();



        //打分功能的执行
        initRateView();

        //有紧急联系人才注册多次按电源键的广播接收者

        SharedPreferences sp = getSharedPreferences("Urgent", Context.MODE_PRIVATE);
        final boolean hasUrgent = sp.getBoolean("hasUrgent", false);

        x.task().run(new Runnable() {
            @Override
            public void run() {
                if (hasUrgent) {
                    screenReceiver = new ScreenReceiver();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(Intent.ACTION_SCREEN_ON);
                    intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
                    registerReceiver(screenReceiver, intentFilter);

//            stopLocation();
                    System.out.println("注册了");

                } else {
                    System.out.println("没有注册");
                }
            }
        });


    }

    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        up_box = (TextView) findViewById(R.id.box_up);
        tv_rate = (TextView) findViewById(R.id.tv_rate);
        ratingView = (RatingView) findViewById(R.id.rating_view);
        parent_protect = (TextView) findViewById(R.id.parentProtect);
        bottomLayout = (RelativeLayout) findViewById(R.id.bottomlayout);
        tv_speedup = (TextView) findViewById(R.id.tv_speedup);
        garbageClean = (TextView) findViewById(R.id.garbageClean);


        toolbar.inflateMenu(R.menu.main_ui_menu);
        toolbar.setOnMenuItemClickListener(this);

        up_box.setOnClickListener(this);
        parent_protect.setOnClickListener(this);
        tv_speedup.setOnClickListener(this);
        garbageClean.setOnClickListener(this);
    }


    private void initRateView() {
        int sofeRate = 1 + new Random().nextInt(10);
        int fluentRate = 1 + new Random().nextInt(10);
        int cleanRate = 1 + new Random().nextInt(10);

        int total_wage = (int) (sofeRate * 0.5 + fluentRate * 0.3 + cleanRate * 0.2) * 10;
        DanceWageTimer danceWageTimer = new DanceWageTimer(DanceWageTimer.getTotalExecuteTime(total_wage, 50), 50, tv_rate, total_wage);
        danceWageTimer.start();


        // >= 8  高    >=4  中    >= 0  差


        //三元运算符  ？
        sofeBar = new RatingBar(sofeRate, (sofeRate < 4 ? "安全度差" : sofeRate >= 4 & sofeRate < 8 ? "安全度中" : "安全度高"));
        fluentBar = new RatingBar(fluentRate, (fluentRate < 4 ? "流畅度差" : fluentRate >= 4 & fluentRate < 8 ? "流畅度中" : "流畅度高"));
        cleanBar = new RatingBar(cleanRate, (cleanRate < 4 ? "清洁度差" : cleanRate >= 4 & cleanRate < 8 ? "清洁度中" : "清洁度高"));

        ratingView.addRatingBar(sofeBar);
        ratingView.addRatingBar(fluentBar);
        ratingView.addRatingBar(cleanBar);


        ratingView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ratingView.show();
            }
        }, DanceWageTimer.getTotalExecuteTime(total_wage, 50));
    }

    //activity结束后，取消注册
    @Override
    protected void onDestroy() {

        if (screenReceiver != null) {
            unregisterReceiver(screenReceiver);
        }
        super.onDestroy();
    }



    //返回到主界面activity触发动画
    @Override
    protected void onRestart() {
        super.onRestart();

        Animation downAnim = AnimationUtils.loadAnimation(this, R.anim.main_ui_up_down);
        Animation upAnim = AnimationUtils.loadAnimation(this, R.anim.main_ui_down_up);

        ratingView.startAnimation(downAnim);
        bottomLayout.startAnimation(upAnim);
    }

    //用户中心的监听
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        startActivity(new Intent(this, UserRoomActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return false;
    }

    //模块的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.box_up:
                startActivity(new Intent(this, TreasureBoxActivity.class));
                overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
                break;
            case R.id.parentProtect:
                Animation upAnim = AnimationUtils.loadAnimation(this, R.anim.main_ui_up_up);
                Animation downAnim = AnimationUtils.loadAnimation(this, R.anim.main_ui_down_down);

                ratingView.startAnimation(upAnim);
                bottomLayout.startAnimation(downAnim);

                upAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(MainUIActivity.this, ParentProtectActivity.class));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;

            case R.id.tv_speedup:
                startActivity(new Intent(this, CleanSpeedUpActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;

            case R.id.garbageClean:
                startActivity(new Intent(this, GarbageCleanActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
        }
    }
}
