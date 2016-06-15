package com.zzptc.joker.baiduguard.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.zzptc.joker.baiduguard.R;
import com.zzptc.joker.baiduguard.activity.CheckContactsActivity;
import com.zzptc.joker.baiduguard.activity.OneKeyForHelpActivity;
import com.zzptc.joker.baiduguard.adapter.HelpContactAdapter;
import com.zzptc.joker.baiduguard.bean.Contact;
import com.zzptc.joker.baiduguard.utils.CommonUtils;
import com.zzptc.joker.baiduguard.utils.ContactUtils;

import org.xutils.DbManager;
import org.xutils.config.DbConfigs;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择紧急联系人的fragment
 */
public class AddContactFragment extends Fragment implements View.OnClickListener {

    private ListView listView;
    private HelpContactAdapter helpContactAdapter;
    private LinearLayout ly_input_number;
    private EditText et_phone_number, et_help_msg;
    private ImageView iv_add_contacts;
    private Button btn_ok, btn_completion;
    private Boolean includeContactFlag = false;

    //紧急联系人的集合
    private ArrayList<Contact> helpContacts;
    //所有联系人的集合
    List<Contact> allContacts = ContactUtils.getContacts();

    DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("urgentContacts.db")
            .setDbVersion(1)
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    db.getDatabase().enableWriteAheadLogging();
                }
            });

    public AddContactFragment() {
        // Required empty public constructor
    }

    public void initWidget(View view) {
        iv_add_contacts = (ImageView) view.findViewById(R.id.iv_add_contacts);
        listView = (ListView) view.findViewById(R.id.listView);
        ly_input_number = (LinearLayout) view.findViewById(R.id.ly_input_number);
        et_phone_number = (EditText) view.findViewById(R.id.et_phone_number);
        btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_completion = (Button) view.findViewById(R.id.btn_completion);
        et_help_msg = (EditText) view.findViewById(R.id.et_help_msg);


        //用户输入电话号码时的监听
        iv_add_contacts.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_completion.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);

        initWidget(view);

        SharedPreferences sp = getActivity().getSharedPreferences("Urgent", Context.MODE_PRIVATE);
        boolean hasUrgent = sp.getBoolean("hasUrgent", false);


        helpContacts = new ArrayList<>();
        if (hasUrgent) {
            try {
                helpContacts = (ArrayList<Contact>) CommonUtils.getUrgentContacts();
                if (helpContacts.size() == 3) {
                    ly_input_number.setVisibility(View.GONE);
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        helpContactAdapter = new HelpContactAdapter(helpContacts, getActivity(), ly_input_number);
        listView.setAdapter(helpContactAdapter);

        et_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_phone_number.getText().length() > 0) {
                    iv_add_contacts.setVisibility(View.GONE);
                    btn_ok.setVisibility(View.VISIBLE);

                } else {
                    btn_ok.setVisibility(View.GONE);
                    iv_add_contacts.setVisibility(View.VISIBLE);
                }

                if (et_phone_number.getText().length() == 11) {
                    if (isDistinct(et_phone_number.getText().toString())) {
                        et_phone_number.setText("");
                        Toast.makeText(getActivity(), "已经存在的紧急联系人,请重新输入", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }


    //按钮的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_contacts:

                Intent intent = new Intent(new Intent(getActivity(), CheckContactsActivity.class));
                if (helpContacts != null) {
                    intent.putExtra("helpContacts", helpContacts);
                }
                startActivityForResult(intent, 0);
                break;

            //确定时候验证号码，是否为电话号码，是否重复
            case R.id.btn_ok:
                String phone = et_phone_number.getText().toString();
                if (phone.matches("^1[34578]\\d{9}$")) {

                    for (Contact contact : allContacts) {
                        if (phone.equals(contact.getPhone())) {
                            helpContacts.add(contact);
                            includeContactFlag = true;
                            break;
                        }
                    }
                    if (!includeContactFlag) {
                        Contact c = new Contact();
                        c.setPhone(phone);
                        helpContacts.add(c);
                    }


                    helpContactAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }


                et_phone_number.setText("");
                break;

            //点击完成之后把紧急联系人存进数据库，存进数据库要判断是否有了数据，有就先清空数据库
            case R.id.btn_completion:


                if (helpContacts != null && helpContacts.size() > 0) {

                    System.out.println("联系人的集合大小" + helpContacts.size());
                    DbManager dbManager = x.getDb(daoConfig);
                    try {
                        List<Contact> urgentContacts = dbManager.selector(Contact.class).findAll();

                        if (urgentContacts != null && urgentContacts.size() > 0) {
                            dbManager.delete(urgentContacts);
                        } else {
                            for (Contact contact : helpContacts) {
                                dbManager.save(contact);
                            }

                            OneKeyForHelpActivity activity = (OneKeyForHelpActivity) getActivity();
                          activity.replaceToUrgent();
                            SharedPreferences sp = getActivity().getSharedPreferences("Urgent", Context.MODE_PRIVATE);
                            sp.edit().putBoolean("hasUrgent", true).commit();
                            sp.edit().putString("helpMessage", et_help_msg.getText().toString()).commit();

                        }

                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getActivity(), "请选择紧急联系人", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //activty返回值返回时调用的函数
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            switch (resultCode) {
                case 1:
                    //选择联系人返回的联系人集合
                    ArrayList<Contact> contactList = (ArrayList<Contact>) data.getSerializableExtra("data");

                    for (Contact contact : contactList) {
                        helpContacts.add(contact);
                    }

                    if (helpContacts.size() == 3) {
                        ly_input_number.setVisibility(View.GONE);
                    }


                    helpContactAdapter.notifyDataSetChanged();

                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    //判断紧急联系人是否重复
    public boolean isDistinct(String phoneNumber) {
        boolean distinct = false;
        if (helpContacts != null) {
            for (Contact contact : helpContacts) {
                if (phoneNumber.equals(contact.getPhone())) {
                    distinct = true;
                }
            }
        }

        return distinct;
    }
}
