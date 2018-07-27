package com.android.vanshika.notes.frag;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.vanshika.notes.R;
import com.android.vanshika.notes.data.AppDatabase;
import com.android.vanshika.notes.data.myuser;
//import courses.android.com.todo.MainActivity;
//import courses.android.com.todo.R;
//import courses.android.com.todo.createTodo;
//import courses.android.com.todo.data.AppDatabase;
//import courses.android.com.todo.data.myuser;


public class addCredentials extends AppCompatActivity {
  EditText ref,name,mailid,numb,pass,note;
  String title;
  Button button;
  TextView addReference,enterDetail;
  MenuItem item1;

  AppDatabase db1;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_credentials);
    ref = findViewById(R.id.input_filename);
    name = findViewById(R.id.input_username);
    mailid = findViewById(R.id.input_id);
    numb = findViewById(R.id.input_phone);
    pass = findViewById(R.id.input_password);
    note = findViewById(R.id.sumary);
    button = findViewById(R.id.button);
    addReference=findViewById(R.id.addAReference);
    enterDetail=findViewById(R.id.enterYourDetails);


    //if (extraIntent.getIntExtra("fresh",0)==1) {
    //  startFresh();
    //} else{
    //button.setVisibility(View.VISIBLE);
    //item1.setVisible(false);
    //addReference.setText("Add a reference");
    //enterDetail.setText("Enter your details");
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        title = ref.getText().toString();
        final myuser credList =
            new myuser(title,  mailid.getText().toString(),name.getText().toString(),
                numb.getText().toString(), pass.getText().toString()
                , note.getText().toString());
        AsyncTask.execute(new Runnable() {
          @Override public void run() {
            AppDatabase
                db1 = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mylock")
                .build();
            db1.userDao().insert(credList);
          }
        });
        Intent intent = new Intent(addCredentials.this, Info.class);
        startActivity(intent);
        finish();
      }
    });
  }



  @Override public boolean onCreateOptionsMenu(Menu menu) {

    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {

    return super.onOptionsItemSelected(item);
  }
}

