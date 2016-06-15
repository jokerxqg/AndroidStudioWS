package com.zzptc.joker.baiduguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.bean.UpdateInfo;
import com.zzptc.joker.baiduguard.constant.Constants;
import com.zzptc.joker.baiduguard.fragment.NetworkFragmentDialog;

public class UserRoomActivity extends Activity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {
    private Toolbar toolbar;
    private RelativeLayout updateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_room);

        initViews();


    }


    //初始化控件以及绑定监听
    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        updateLayout = (RelativeLayout) findViewById(R.id.update_layout);

        toolbar.inflateMenu(R.menu.user_room_menu);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        updateLayout.setOnClickListener(this);
    }

    //菜单条目的监听
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_guard:
                startActivity(new Intent(this, AboutGuardActivity.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }

        return false;
    }

    //布局文件里的控件点击事件的监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_layout:
                startActivityForResult(new Intent(this, UpdateDialogActivity.class), Constants.REQUEST_CODE);
                break;
        }
    }

    //子activity返回值的处理函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==Constants.REQUEST_CODE){
            switch (resultCode){
                case Constants.NOT_NETWORK:
                    NetworkFragmentDialog.newInstance("亲，请检查你的网络").show(getFragmentManager(),null);
                    break;
                case Constants.ERROR_NETWORK:
                    NetworkFragmentDialog.newInstance("网络异常").show(getFragmentManager(),null);
                    break;
                case Constants.CANCEL_DOWNLOAD:
                    NetworkFragmentDialog.newInstance("你取消了下载").show(getFragmentManager(),null);
                    break;

                case Constants.AVAILABLE_UPDATE:
                    UpdateInfo updateInfo = (UpdateInfo) data.getSerializableExtra("updateInfo");
                    NetworkFragmentDialog.newInstance(updateInfo.getDescription(),updateInfo).show(getFragmentManager(),null);
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


}
