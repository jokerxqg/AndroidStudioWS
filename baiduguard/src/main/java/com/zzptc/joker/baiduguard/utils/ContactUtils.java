package com.zzptc.joker.baiduguard.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.github.lzyzsd.randomcolor.RandomColor;
import com.zzptc.joker.baiduguard.bean.Contact;
import com.zzptc.joker.baiduguard.bean.MobileAttribute;

import org.apache.commons.io.FileUtils;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 2016/5/9/009.
 */
public class ContactUtils {
    public static List<Contact> contacts;

    public static List<Contact> getContacts() {
        return contacts;
    }

    public static void setContacts(List<Contact> contacts) {
        ContactUtils.contacts = contacts;
    }

    //读取手机通讯录
    //1、将数据库文件导入到项目的 assets 目录下，然后 将数据库拷贝到系统数据库路径 data/data/packagename/databases/
    //2、首先读取系统通讯录
    //3、根据系统通讯录中的电话号码，查询外部数据库，得到手机归属地
    //4、添加集合当中
    public static String databasePath = "data/data/%s/databases";

    public static String getDatabasePath(Context context){
        return String.format(databasePath,context.getPackageName());
    }

    public static String getDatabaseFile(Context context,String dbFile){
        return getDatabasePath(context) + File.separator + dbFile;
    }

    //准备拷贝
    public static void copyDatabase(Context context){
        //首先判断文件夹是否存在  如果不存在  创建一个文件夹
        //再判断文件是否存在  如果文件不存在  创建一个文件
        //将数据库拷贝到文件中
        File fileDir = new File(getDatabasePath(context));
        if(!fileDir.exists()){
            fileDir.mkdir();
        }

        File dbFile = new File(getDatabaseFile(context, "mobile.db"));
        if(!dbFile.exists()){
            try {

                    dbFile.createNewFile();


                //开始拷贝  如何通过网络依赖第三方jar包
                //得到assets目录下的文件
                //InputStream is = context.getAssets().open("mobile.db");
                //FileUtils.copyInputStreamToFile()  commons-io  2.5

                URL url = context.getClass().getClassLoader().getResource("assets/mobile.db");
                FileUtils.copyURLToFile(url, dbFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //读取系统通讯录  如何读取系统通讯录
    //系统通讯录以contentprovider对外提供数据  使用contentresolver读取数据
    //创建类  Contact
    public static List<Contact> queryAllContacts(Context context){
        List<Contact> contacts = new ArrayList<>();

        ContentResolver mResolver = context.getContentResolver();

        //首先查raw_contact  id  display_name  has_phone
        //要据上一张表，首先要判断是否有电话号码，只有在有电话号码的情况下，我再去查询 ContactsContract.CommonDataKinds.Phone
        //查询 ContactsContract.CommonDataKinds.Phone同时，根据电话号码 ，查询手机归属地的表
        //查询完成，添加到集合              查询的url                            查询的字段落 条件  条件参数 排序
        Cursor raw_cursor = mResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

        while(raw_cursor != null && raw_cursor.moveToNext()){
            Contact contact = new Contact();
            //id
            int id = raw_cursor.getInt(raw_cursor.getColumnIndex(ContactsContract.Contacts._ID));
            contact.setContactId(id);
            //姓名
            String name = raw_cursor.getString(raw_cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contact.setName(name);
            //判断是否有电话号码
            int has_phone_number = raw_cursor.getInt(raw_cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            String phone = getPhone(id,has_phone_number,mResolver);
            if(phone != null){
                contact.setPhone(phone);

                //根据电话号码来查询手机归属地  xutils3
                //看一下xutils sample，看它如何操作数据库
                String phoneAttribute = getPhoneAttribute(phone);
                contact.setAttribute(phoneAttribute);

                //头像颜色
                RandomColor randomColor = new RandomColor();
                int color = randomColor.randomColor();

                contact.setHeadColor(color);

                contacts.add(contact);
            }
        }
        raw_cursor.close();
        return contacts;
    }

    /**
     * 查询电话号码
     * @param id
     * @param has_phone_number
     * @param mResolver
     * @return
     */
    private static String getPhone(int id,int has_phone_number,ContentResolver mResolver){
        if(has_phone_number > 0){
            Cursor cursor = mResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                    new String[]{id+""},null);
            //if  movetofirst  默认情况下，我只查询该联系人的第一个电话号码
            //如果你需要查询该联系人的所有号码  你需要将contact中的 String  phone ==> List<String> phone;
            if(cursor != null && cursor.moveToFirst()){
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                return phone.replace("-","").replace(" ","");
            }

            cursor.close();
        }
        return null;
    }

    /**
     * 根据手机号码查询手机归属地
     * @param phone
     * @return
     */
    private static String getPhoneAttribute(String phone){

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("mobile.db")
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });

        DbManager dbManager = x.getDb(daoConfig);

        // 查询的思路   1 87-1116-1317 ==》 18711161317 ==》判断是否为手机号码 ==》 截取 ==》 查询手机归属地
        //正则表达式  手机号友
        if(phone.matches("^1[34578]\\d{9}$")){
            try {
                MobileAttribute mobileAttribute = dbManager.selector(MobileAttribute.class).where("MobileNumber","=",phone.substring(0,7)).findFirst();

                if(mobileAttribute != null){
                    return mobileAttribute.getMobileArea() + "\n" + mobileAttribute.getMobileType();
                }else{
                    return "非手机号码";
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
