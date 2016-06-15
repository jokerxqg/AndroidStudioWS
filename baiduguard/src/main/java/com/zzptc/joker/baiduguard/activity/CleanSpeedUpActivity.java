package com.zzptc.joker.baiduguard.activity;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ant.liao.GifView;
import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.adapter.AppInfoAdapter;
import com.zzptc.joker.baiduguard.bean.AppInfo;
import com.zzptc.joker.baiduguard.task.ScanAppTask;

import java.util.List;

/*
* 清理加速的act
*
* */
public class CleanSpeedUpActivity extends AppCompatActivity implements ScanAppTask.ScanAppListener {

    private TextView tv_title, tv_clean_size, tv_unit,tv_run_app;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Button btn_cleanAll;
    private GifView gif_wait;
    private RelativeLayout rl_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_speed_up);

//        初始化控件，设置监听
        initView();

        ScanAppTask scanAppTask = new ScanAppTask();

        scanAppTask.setScanAppListener(this);

        scanAppTask.excute();


    }


    /*
    * */
    public void initView() {
        tv_clean_size = (TextView) findViewById(R.id.tv_clean_size);
        tv_title = (TextView) findViewById(R.id.tv_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btn_cleanAll = (Button) findViewById(R.id.btn_cleanAll);
        gif_wait = (GifView) findViewById(R.id.gif_wait);
        rl_show = (RelativeLayout) findViewById(R.id.rl_show);
        tv_run_app = (TextView) findViewById(R.id.tv_run_app);


        gif_wait.setGifImage(R.drawable.wait);
        gif_wait.setShowDimension(50, 50);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.inflateMenu(R.menu.speed_up_menu);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onScanStart(Context context) {
        System.out.println("开始扫描····");
    }

    @Override
    public void onScanRunning(Context context, int current, int total) {

    }

    @Override
    public void onScanOver(Context context, List<AppInfo> appInfoList) {
        gif_wait.setVisibility(View.GONE);
//        rl_show.setVisibility(View.VISIBLE);
        btn_cleanAll.setVisibility(View.VISIBLE);

        tv_run_app.setText("正在运行的程序("+appInfoList.size()+")");



        AppInfoAdapter appInfoAdapter = new AppInfoAdapter(appInfoList, this);
        recyclerView.setAdapter(appInfoAdapter);
    }
}
