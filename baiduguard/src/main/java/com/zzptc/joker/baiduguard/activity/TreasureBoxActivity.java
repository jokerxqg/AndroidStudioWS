package com.zzptc.joker.baiduguard.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zzptc.joker.baiduguard.R;

public class TreasureBoxActivity extends Activity implements View.OnClickListener {

    private TextView box_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_box);

        initView();
    }


    public void initView() {
        box_down = (TextView) findViewById(R.id.box_down);

        box_down.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.box_down:
                finish();
                overridePendingTransition(0, R.anim.push_top_out);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.push_top_out);
    }
}
