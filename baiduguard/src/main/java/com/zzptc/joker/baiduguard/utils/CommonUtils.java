package com.zzptc.joker.baiduguard.utils;

import android.telephony.SmsManager;

import com.zzptc.joker.baiduguard.bean.Contact;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 2016/5/18/018.
 * 通用工具类
 */
public class CommonUtils {


    public static List<Contact> getUrgentContacts() throws DbException {

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("urgentContacts.db")
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });

        List<Contact> urgentList = x.getDb(daoConfig).selector(Contact.class).findAll();

        return urgentList;
    }

    //发送短信
    public static void sendMes(String phoneNumber, String content) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> text = smsManager.divideMessage(content);
        for (String str : text) {
            smsManager.sendTextMessage(phoneNumber, null, str, null, null);
        }
    }
}
