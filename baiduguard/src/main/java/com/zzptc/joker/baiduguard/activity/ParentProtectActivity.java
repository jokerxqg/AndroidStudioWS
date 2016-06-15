package com.zzptc.joker.baiduguard.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.zzptc.joker.baiduguard.R;

public class ParentProtectActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout ly_onekey;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_protect);

        initView();
    }

    public void initView(){
        ly_onekey = (LinearLayout) findViewById(R.id.ly_onekey);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        ly_onekey.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly_onekey:
                startActivity(new Intent(this,OneKeyForHelpActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
        }
    }
}
