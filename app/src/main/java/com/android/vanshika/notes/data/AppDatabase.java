package com.android.vanshika.notes.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


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
