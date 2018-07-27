package com.android.vanshika.notes;

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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.BaseKeyListener;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.android.vanshika.notes.data.AppDatabase;
import com.android.vanshika.notes.data.user;
//import courses.android.com.todo.data.AppDatabase;
//import courses.android.com.todo.data.user;
import java.util.List;

public class editTodo extends AppCompatActivity {
  private List<user> items;
  EditText editText;
  String note,name;
  int position;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_todo);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    editText=(EditText) findViewById(R.id.editNote);
    Intent intent = getIntent();
    note=intent.getStringExtra("item");
    editText.setCursorVisible(false);
    //editText.setEnabled(false);
    editText.setTextColor(getResources().getColor(R.color.black));
    position=intent.getIntExtra("position",0);
    name=intent.getStringExtra("name");
    editText.setBackgroundResource(R.color.transparent);
    editText.setText(note);
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        editText.setCursorVisible(true);
        editText.setEnabled(true);
        editText.setSelection(note.length());
        editText.requestFocus();
        //editText.setShowSoftInputOnFocus(true);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
      }
    });
  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.edit_todo, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id){
      case R.id.ok:
        showMsg();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void showMsg() {
    if(!editText.getText().toString().equals(note)){
      final AlertDialog.Builder alert = new AlertDialog.Builder(editTodo.this);
      new AlertDialog.Builder(editTodo.this);
      alert.setTitle("Are you sure you want to save your changes");
      alert.setCancelable(true);
      alert.setPositiveButton("Yes! Save", new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialogInterface, int i) {
          final AppDatabase
              db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"users")
              .build();
          Runnable r=new Runnable() {
            @Override public void run() {
              items=db.userDao().getAllItems();

            }
          };
          note=editText.getText().toString();
          //items.get(position).setSummary(note);
          //finalimage user todoList=new user();
          //todoList.setSummary(note);
          //todoList.setmTitle(name);
          //db.userDao().updateUser(todoList);
          AsyncTask.execute(new Runnable() {
            @Override public void run() {
              db.userDao().updateUser(name,note);
            }
          });
          //db.userDao().updateUser(items);
          Intent intent=new Intent(editTodo.this,MainActivity.class);
          startActivity(intent);
          finish();
        }
      });
      alert.setNegativeButton("No! Don't Save", new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialogInterface, int i) {
          Intent intent=new Intent(editTodo.this,MainActivity.class);
          startActivity(intent);
          finish();
        }
      });
      alert.show();
    } else super.onBackPressed();
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    showMsg();
  }
}
/*
* editText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void afterTextChanged(Editable editable) {

      }
    });
*/

