package com.zzptc.joker.baiduguard.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.adapter.ContactAdapter;
import com.zzptc.joker.baiduguard.bean.Contact;
import com.zzptc.joker.baiduguard.utils.ContactUtils;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ContentView(value = R.layout.activity_check_contacts)
public class CheckContactsActivity extends AppCompatActivity {
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.listView)
    private ListView listView;
    @ViewInject(R.id.tv_select_contact)
    private TextView tv_select_contact;
    @ViewInject(R.id.tv_ok_select)
    private TextView tv_ok_select;

    private ContactAdapter contactAdapter;

    //所有的联系人
    private List<Contact> contactList;

    //传过来的紧急联系人，最多为两个
    private ArrayList<Contact> helpContacts;



    Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);

        initToolbar();


        contactList = ContactUtils.getContacts();


        data = getIntent();

        if (data != null) {
            helpContacts = (ArrayList<Contact>) data.getSerializableExtra("helpContacts");
        }

        distinctContact();

        contactAdapter = new ContactAdapter(contactList, this, tv_select_contact, tv_ok_select,helpContacts.size());
        listView.setAdapter(contactAdapter);


    }


    public void initToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Event(value = R.id.tv_ok_select)
    private void clickOk(View v) {

        data = getIntent();

        ArrayList<Contact> checkContactList = new ArrayList<>();

        Map<Integer, Boolean> contactItems = contactAdapter.getItemMap();

        for (Integer key : contactItems.keySet()) {
            boolean isChecked = contactItems.get(key);

            if (isChecked) {
                Contact contact = contactList.get(key);
                checkContactList.add(contact);
            }
        }

        data.putExtra("data", checkContactList);
        setResult(1, data);
        finish();
    }

    //去除联系人，如果已经存在了的紧急联系人就删除掉
    public void distinctContact() {

        //使用迭代器
        for (Iterator iterator = contactList.iterator();iterator.hasNext();) {
            Contact contact = (Contact) iterator.next();
            for (Iterator iterator1 = helpContacts.iterator();iterator1.hasNext();) {
                Contact contact1 = (Contact) iterator1.next();
                if(contact.getPhone().equals(contact1.getPhone())){
                    iterator.remove();
                }
            }
        }
    }
}
