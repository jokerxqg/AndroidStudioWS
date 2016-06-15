package com.zzptc.joker.baiduguard.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by joker on 2016/5/9/009.
 */

@Table(name = "urgentContacts")
public class Contact implements Serializable{

    @Column(name="id" ,isId = true)
    private int contactId;
    @Column(name="name")
    private String name;
    @Column(name = "phone")
    private String phone;

    private String attribute;

    private int headColor;

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public int getHeadColor() {
        return headColor;
    }

    public void setHeadColor(int headColor) {
        this.headColor = headColor;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", attribute='" + attribute + '\'' +
                ", headColor=" + headColor +
                '}';
    }

}
