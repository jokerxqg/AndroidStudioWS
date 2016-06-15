package com.zzptc.joker.baiduguard.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.fragment.AddContactFragment;
import com.zzptc.joker.baiduguard.fragment.ForHelpFragment;
import com.zzptc.joker.baiduguard.fragment.UrgentContactFragment;

public class OneKeyForHelpActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView iv_question, iv_setting;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key_for_help);

        fragmentManager = getFragmentManager();
        initViews();

        SharedPreferences sp = getSharedPreferences("Urgent", Context.MODE_PRIVATE);
        boolean hasUrgent = sp.getBoolean("hasUrgent", false);

        if (hasUrgent) {
            replaceToUrgent();
        } else {
            fragmentManager.beginTransaction()
                    .add(R.id.forhelp_framelayout, new ForHelpFragment()).addToBackStack("help")
                    .commit();
        }
    }


    public void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_question = (ImageView) findViewById(R.id.iv_question);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceToAdd();
            }
        });


    }


    //fragement的替换，还到添加联系人的fragment
    public void replaceToAdd() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.forhelp_framelayout, new AddContactFragment())
                .commit();

        iv_question.setVisibility(View.GONE);
        iv_setting.setVisibility(View.GONE);
    }

    ////fragement的替换，还到紧急联系人的fragment
    public void replaceToUrgent() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.forhelp_framelayout, new UrgentContactFragment())
                .commit();

        iv_question.setVisibility(View.GONE);
        iv_setting.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
