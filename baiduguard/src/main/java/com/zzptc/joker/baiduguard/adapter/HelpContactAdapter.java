package com.zzptc.joker.baiduguard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.bean.Contact;

import java.util.ArrayList;

/**
 * Created by joker on 2016/5/10/010.
 * 紧急联系人的适配器
 */
public class HelpContactAdapter extends BaseAdapter {

    private ArrayList<Contact> contactArrayList;
    private Context context;
    private LayoutInflater layoutInflater;
    private LinearLayout ly_input_number;


    public HelpContactAdapter(ArrayList<Contact> contactArrayList, Context context, LinearLayout ly_input_number) {
        this.contactArrayList = contactArrayList;
        this.context = context;
        this.ly_input_number = ly_input_number;
        layoutInflater = layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return contactArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.help_contact_item, null);
            viewHolder.tv_contact_name = (TextView) convertView.findViewById(R.id.tv_contact_name);
            viewHolder.tv_contact_number = (TextView) convertView.findViewById(R.id.tv_contact_number);
            viewHolder.iv_cancel = (ImageView) convertView.findViewById(R.id.iv_cancel);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Contact contact = contactArrayList.get(position);

        viewHolder.tv_contact_name.setText(contact.getName());
        viewHolder.tv_contact_number.setText(contact.getPhone());
        viewHolder.iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactArrayList.remove(position);
                notifyDataSetChanged();
                if(contactArrayList.size()==3){
                    ly_input_number.setVisibility(View.GONE);
                }else{
                    ly_input_number.setVisibility(View.VISIBLE);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView tv_contact_name, tv_contact_number;
        ImageView iv_cancel;
    }
}
