package com.android.vanshika.notes.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "users")
public class user {
  @PrimaryKey(autoGenerate = true)
  ////@ColumnInfo(name = "userid")
  int id;
  private boolean isSelected = false;

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @ColumnInfo(name = "title")
   String mTitle;
  @ColumnInfo(name = "des")
  String summary;

  public user(String mTitle, String summary) {
    //this.id = id;
    this.mTitle = mTitle;
    this.summary = summary;
  }
  @Ignore
  public user() {
  }

// qa error hei?mTitle ka geeter field nhi mil rha tha

  public String getmTitle() {
    return mTitle;
  }

  public void setmTitle(String mTitle) {
    this.mTitle = mTitle;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }
}
