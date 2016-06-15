package com.zzptc.joker.baiduguard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.zzptc.joker.baiduguard.activity.SplashActivity;
import com.zzptc.joker.baiduguard.activity.WelcomeActivity;
import com.zzptc.joker.baiduguard.bean.Contact;
import com.zzptc.joker.baiduguard.utils.ContactUtils;

import org.xutils.x;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    //界面跳转判断，如果第一次使用app.先进入引导界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("appinfo", Activity.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("isFirst", true)) {
            startActivity(new Intent(this, SplashActivity.class));
        } else {
                startActivity(new Intent(this, WelcomeActivity.class));
        }
        finish();

        x.task().run(new Runnable() {
            @Override
            public void run() {
                ContactUtils.copyDatabase(MainActivity.this);

                List<Contact> contactList = ContactUtils.getContacts();

                if (contactList == null) {
                    contactList = ContactUtils.queryAllContacts(MainActivity.this);
                    ContactUtils.setContacts(contactList);
                }
            }
        });
    }


}
