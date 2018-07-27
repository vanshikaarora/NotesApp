package com.android.vanshika.notes.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

//sir yeh tjeek h?kahan gaye sir aap?
@Database(entities = {user.class,myuser.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
  private static AppDatabase INSTANCE;

  public abstract UserDao userDao();
  public static AppDatabase getAppDatabase(Context context){
    if(INSTANCE==null){
      INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"users")
          .allowMainThreadQueries()
          .build();
    }return INSTANCE;
  }
}
// humare kotlin main to directly array ban jata hei, isme kaise banega?yeh toh mujhe bhi nhi ppta sir mujhe c++ zyaada aati h as compared to java. ok ma'am
//i sec sir muje yaad aa gaya java mein array k liye array variable={"first string","second string"}; bhia upar kar de change jo bhi hoga.
//nhi ho rha yeh naya object bna k kese karte hain yaad nhi