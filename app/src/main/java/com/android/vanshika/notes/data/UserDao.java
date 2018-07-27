package com.android.vanshika.notes.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface UserDao {
  //public UserDao mUserDao=null;
  @Query("SELECT * FROM users")
  List<user> getAllItems();

  @Insert
  void insertAll(user... userList);
  @Query("UPDATE users SET  des=:note  WHERE title =:topic")
  public void updateUser(String topic, String note);
  @Query("DELETE FROM users WHERE title=:topic")
  public void deleteUser(String topic);


  @Query("SELECT * FROM mylock")
  List<myuser> getUser();
  @Query("UPDATE mylock SET user=:name,mailid=:mailid, phone=:numb,password=:pass, note=:note WHERE title=:ref")
  public void updateMyUser(String ref, String name, String mailid, String numb, String pass,
      String note);
  @Query("DELETE FROM mylock WHERE title=:ref")
  public void deleteMyUser(String ref);
  @Insert
  void insert(myuser... myuserList);

}


