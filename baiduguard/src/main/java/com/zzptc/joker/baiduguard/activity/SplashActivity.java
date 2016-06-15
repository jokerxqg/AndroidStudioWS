package com.zzptc.joker.baiduguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity implements View.OnClickListener {
    private ViewPager viewPager;
    private List<View> views;
    private View view1, view2, view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initViews();

    }

    public void initViews() {
        //准备需要的视图
        views = new ArrayList<View>();
        view1 = View.inflate(this, R.layout.splash_first_layout, null);
        view2 = View.inflate(this, R.layout.splash_second_layout, null);
        view3 = View.inflate(this, R.layout.splash_three_layout, null);


        views.add(view1);
        views.add(view2);
        views.add(view3);


        //找出控件
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Button startBtn = (Button) view3.findViewById(R.id.startBtn);
        startBtn.setOnClickListener(this);

        //加载设置适配器
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(views);
        viewPager.setAdapter(pagerAdapter);
    }

    //点击监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startBtn:
                startActivity(new Intent(this, WelcomeActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {

        SharedPreferences sharedPreferences = getSharedPreferences("appinfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFirst", false);

        editor.commit();
        super.onDestroy();
    }
}
