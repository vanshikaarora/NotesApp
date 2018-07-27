package com.android.vanshika.notes;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.vanshika.notes.data.AppDatabase;
import com.android.vanshika.notes.data.user;
//import courses.android.com.todo.data.AppDatabase;
//import courses.android.com.todo.data.user;
import java.util.ArrayList;
import java.util.List;

public class createTodo extends AppCompatActivity {
  public EditText mTodo;
  public String mPara;
  public Button button;
  public String fTitle;
  public List<user> items;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_todo);
    button=findViewById(R.id.save);
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        mTodo=(EditText) findViewById(R.id.para);
        mPara=mTodo.getText().toString();
        final AlertDialog.Builder alert = new AlertDialog.Builder(createTodo.this);
        new AlertDialog.Builder(createTodo.this);
        final EditText edittext = new EditText(createTodo.this);
        final AppDatabase
            db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"users")
            .build();

        //alert.setMessage("Enter Your Message");
        alert.setTitle("Enter Your Title");

        alert.setView(edittext);

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            fTitle = edittext.getText().toString();
            final user todoList=new user(fTitle,mPara);
            AsyncTask.execute(new Runnable() {
              @Override public void run() {
                db.userDao().insertAll(todoList);
              }
            });
            Intent intent=new Intent(createTodo.this,MainActivity.class);
            //intent.setData()
            startActivity(intent);
            finish();
          }
        });


        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {

          }
        });

        alert.show();

      }
    });
  }
}

