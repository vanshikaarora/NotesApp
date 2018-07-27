package com.android.vanshika.notes.frag;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.android.vanshika.notes.R;
import com.android.vanshika.notes.data.AppDatabase;
import com.android.vanshika.notes.data.myuser;
//import courses.android.com.todo.R;
//import courses.android.com.todo.data.AppDatabase;
//import courses.android.com.todo.data.myuser;
//import courses.android.com.todo.data.user;
//import courses.android.com.todo.editTodo;
import java.util.List;

public class editCredentials extends AppCompatActivity {
  String mTitle,mName,mId,mPhone,mPass,mNote;
  EditText ref,name,mailid,numb,pass,note;
  Intent extraIntent;
  private List<myuser> items;
  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.edit_credentials, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id=item.getItemId();
    if (id==R.id.ok){
      showMsg();
    }
    return super.onOptionsItemSelected(item);
  }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_credentials);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    extraIntent = getIntent();

    ref = findViewById(R.id.input_filename);
    name = findViewById(R.id.input_username);
    mailid = findViewById(R.id.input_id);
    numb = findViewById(R.id.input_phone);
    pass = findViewById(R.id.input_password);
    note = findViewById(R.id.sumary);


    ref.setCursorVisible(false);
    ref.setTextColor(getResources().getColor(R.color.black));
    ref.setBackgroundResource(R.color.transparent);
    mTitle=extraIntent.getStringExtra("heading");
    ref.setText(mTitle);

    name.setCursorVisible(false);
    name.setTextColor(getResources().getColor(R.color.black));
    name.setBackgroundResource(R.color.transparent);
    mName=extraIntent.getStringExtra("name");
    name.setText(mName);

    mailid.setCursorVisible(false);
    mailid.setTextColor(getResources().getColor(R.color.black));
    mailid.setBackgroundResource(R.color.transparent);
    mId=extraIntent.getStringExtra("mailid");
    mailid.setText(mId);

    numb.setCursorVisible(false);
    numb.setTextColor(getResources().getColor(R.color.black));
    numb.setBackgroundResource(R.color.transparent);
    mPhone=extraIntent.getStringExtra("phone");
    numb.setText(mPhone);

    pass.setCursorVisible(false);
    pass.setTextColor(getResources().getColor(R.color.black));
    pass.setBackgroundResource(R.color.transparent);
    mPass=extraIntent.getStringExtra("password");
    pass.setText(mPass);

    mNote=extraIntent.getStringExtra("note");
    if(mNote!=null){
      note.setCursorVisible(false);
      note.setTextColor(getResources().getColor(R.color.black));
      note.setBackgroundResource(R.color.transparent);
      mNote=extraIntent.getStringExtra("note");
      note.setText(mNote);
    }
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        allowEditing();
      }
    });
  }

  private void allowEditing() {
    name.setCursorVisible(true);
    name.setEnabled(true);
    name.setSelection(note.length());
    name.requestFocus();

    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);

    //name.setEnabled(true);
    mailid.setEnabled(true);
    numb.setEnabled(true);
    pass.setEnabled(true);
    note.setEnabled(true);

    name.setCursorVisible(true);
    mailid.setCursorVisible(true);
    numb.setCursorVisible(true);
    pass.setCursorVisible(true);
    note.setCursorVisible(true);
  }
  private void showMsg() {
    if(!name.getText().toString().equals(mName) ||
        !mailid.getText().toString().equals(mId) ||
        !numb.getText().toString().equals(mPhone) ||
        !pass.getText().toString().equals(mPass) ||
        !note.getText().toString().equals(mNote)){
      final AlertDialog.Builder alert =new AlertDialog.Builder(editCredentials.this);
      new AlertDialog.Builder(editCredentials.this);
      alert.setTitle("Are you sure you want to save your changes");
      alert.setCancelable(true);
      alert.setPositiveButton("Yes! Save", new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialogInterface, int i) {
          final AppDatabase
              db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"mylock")
              .build();
          Runnable r=new Runnable() {
            @Override public void run() {
              items=db.userDao().getUser();

            }
          };
          mTitle=ref.getText().toString();
          mName=name.getText().toString();
          mId=mailid.getText().toString();
          mPhone=numb.getText().toString();
          mPass=pass.getText().toString();
          mNote=note.getText().toString();

          AsyncTask.execute(new Runnable() {
            @Override public void run() {
              db.userDao().updateMyUser(mTitle,mName,mId,mPhone,mPass,mNote);
            }
          });
          Intent intent=new Intent(editCredentials.this,Info.class);
          startActivity(intent);
          finish();

        }
      });
      alert.setNegativeButton("No! Don't Save", new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialogInterface, int i) {
          Intent intent=new Intent(editCredentials.this,Info.class);
          startActivity(intent);
          finish();
        }
      });alert.show();
    }else {
      super.onBackPressed();
    }
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    showMsg();
  }
}

