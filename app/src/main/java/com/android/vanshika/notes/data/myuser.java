package com.android.vanshika.notes.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "mylock")
public class myuser {
  @PrimaryKey(autoGenerate = true)
  //@ColumnInfo(name = "userid")
      int uid;
  @ColumnInfo(name = "title")
  String mTitle;
  @ColumnInfo(name = "mailid")
  String mid;
  @ColumnInfo(name = "user")
  String mUser;
  @ColumnInfo(name = "phone")
  String  mPhone;
  @ColumnInfo(name = "password")
  String mPass;
  @ColumnInfo(name = "note")
  String mNote="";

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public String getmTitle() {
    return mTitle;
  }

  public void setmTitle(String mTitle) {
    this.mTitle = mTitle;
  }

  public String getMid() {
    return mid;
  }

  public void setMid(String mid) {
    this.mid = mid;
  }

  public String getmUser() {
    return mUser;
  }

  public void setmUser(String mUser) {
    this.mUser = mUser;
  }

  public String getmPhone() {
    return mPhone;
  }

  public void setmPhone(String mPhone) {
    this.mPhone = mPhone;
  }

  public String getmPass() {
    return mPass;
  }

  public void setmPass(String mPass) {
    this.mPass = mPass;
  }

  public String getmNote() {
    return mNote;
  }

  public void setmNote(String mNote) {
    this.mNote = mNote;
  }

  public myuser(String mTitle, String mid, String mUser, String mPhone, String mPass,
      String mNote) {
    this.mTitle = mTitle;
    this.mUser = mUser;
    this.mid = mid;
    this.mPhone = mPhone;
    this.mPass = mPass;
    this.mNote = mNote;
  }
}
