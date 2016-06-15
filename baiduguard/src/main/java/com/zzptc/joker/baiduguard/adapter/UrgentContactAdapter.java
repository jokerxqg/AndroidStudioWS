package com.zzptc.joker.baiduguard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.bean.Contact;

import java.util.List;

/**
 * Created by joker on 2016/5/18/018.
 */
public class UrgentContactAdapter extends BaseAdapter {

    private List<Contact> urgentContacts;
    private LayoutInflater layoutInflater;

    public UrgentContactAdapter(List<Contact> urgentContacts, Context context) {
        this.urgentContacts = urgentContacts;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return urgentContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return urgentContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View  view = layoutInflater.inflate(R.layout.urgent_contact_item,null);

        TextView tv_phone_number= (TextView) view.findViewById(R.id.tv_phone_number);
        TextView tv_contact_name = (TextView) view.findViewById(R.id.tv_contact_name);

        Contact contact = urgentContacts.get(position);
        tv_contact_name.setText(contact.getName());
        tv_phone_number.setText(contact.getPhone());

        return view;
    }
}
