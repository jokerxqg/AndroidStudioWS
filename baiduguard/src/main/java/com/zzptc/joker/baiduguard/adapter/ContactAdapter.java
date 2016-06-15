package com.zzptc.joker.baiduguard.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkcool.circletextimageview.CircleTextImageView;
import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.bean.Contact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joker on 2016/5/9/009.
 */
public class ContactAdapter extends BaseAdapter {

    private List<Contact> contactList;
    private LayoutInflater layoutInflater;
    private Context context;
    private TextView tv_select_contact, tv_ok_select;
    private Map<Integer, Boolean> itemMap;
    private int checkCount = 0;
    private int helpContactSize;


    public ContactAdapter(List<Contact> contactList, Context context, TextView tv_select_contact, TextView tv_ok_select, int helpContactSize) {
        this.contactList = contactList;
        this.context = context;
        this.layoutInflater = layoutInflater.from(context);
        this.tv_select_contact = tv_select_contact;
        this.tv_ok_select = tv_ok_select;
        this.helpContactSize = helpContactSize;

        itemMap = new HashMap<>();
        for (int position = 0; position < contactList.size(); position++) {
            itemMap.put(position, false);
        }
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.contact_item, null);
            viewHolder.circle = (CircleTextImageView) convertView.findViewById(R.id.circle);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_phone_number = (TextView) convertView.findViewById(R.id.tv_phone_number);
            viewHolder.tv_attribute = (TextView) convertView.findViewById(R.id.tv_attribute);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Contact contact = contactList.get(position);

        String name = contact.getName();

        viewHolder.circle.setFillColor(contact.getHeadColor());
        viewHolder.circle.setText(name.substring(name.length() - 1));
        viewHolder.tv_name.setText(name);
        viewHolder.tv_phone_number.setText(contact.getPhone());
        viewHolder.tv_attribute.setText(contact.getAttribute());
        viewHolder.checkBox.setChecked(itemMap.get(position));

        final ViewHolder finalViewHolder = viewHolder;

        /*
        *
        * */
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contact.getAttribute() != null) {
                    if (finalViewHolder.checkBox.isChecked()) {
                        if (checkCount < 3 - helpContactSize) {
                            itemMap.put(position, true);

                            checkCount++;
                        } else {
                            Toast.makeText(context, "你已经选择了三个号码", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        itemMap.put(position, false);

                        checkCount--;
                    }
                } else {
                    Toast.makeText(context, "非手机号码不可选", Toast.LENGTH_SHORT).show();
                }

                tv_select_contact.setText("选择联系人  (" + checkCount + ")");
                if (checkCount > 0) {
                    tv_ok_select.setClickable(true);
                    tv_ok_select.setTextColor(Color.WHITE);
                } else {
                    tv_ok_select.setClickable(false);
                    tv_ok_select.setTextColor(Color.GRAY);
                }
                notifyDataSetChanged();

            }
        });

        return convertView;
    }

    //viewholder的用处，复用convertView,提高执行效率，
    class ViewHolder {
        CircleTextImageView circle;
        TextView tv_name, tv_phone_number, tv_attribute;
        CheckBox checkBox;
    }

    public Map<Integer, Boolean> getItemMap() {
        return itemMap;
    }
}
