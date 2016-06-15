package com.zzptc.joker.baiduguard.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.adapter.UrgentContactAdapter;
import com.zzptc.joker.baiduguard.bean.Contact;
import com.zzptc.joker.baiduguard.receiver.ScreenReceiver;
import com.zzptc.joker.baiduguard.utils.CommonUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;


public class UrgentContactFragment extends Fragment {

    private ListView listView;
    private UrgentContactAdapter urgentContactAdapter;
    private ImageView iv_shake;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_urgent_contact, container, false);

        initView(view);



        iv_shake.setImageResource(R.drawable.image_shake);
        AnimationDrawable shakeAnim = (AnimationDrawable) iv_shake.getDrawable();
        shakeAnim.start();


        try {
            List<Contact> contacts = CommonUtils.getUrgentContacts();
            if(contacts!=null){
                urgentContactAdapter = new UrgentContactAdapter(contacts,getActivity());
                listView.setAdapter(urgentContactAdapter);
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

        return view;
    }

    public void initView(View view){
        listView = (ListView) view.findViewById(R.id.listView);
        iv_shake = (ImageView) view.findViewById(R.id.iv_shake);
    }
}
