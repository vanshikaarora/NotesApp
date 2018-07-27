package com.android.vanshika.notes.frag;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import courses.android.com.todo.MainActivity;
import android.widget.CheckBox;
import android.widget.Toast;
import com.android.vanshika.notes.MainActivity;
import com.android.vanshika.notes.R;
import com.android.vanshika.notes.data.AppDatabase;
import com.android.vanshika.notes.data.myuser;
import com.android.vanshika.notes.signup;
//import courses.android.com.todo.MainActivity;
//import courses.android.com.todo.MainActivity;
//import courses.android.com.todo.R;
//import courses.android.com.todo.data.AppDatabase;
////import courses.android.com.todo.mydata.myAppDatabase;
//import courses.android.com.todo.data.myuser;
//import courses.android.com.todo.listAdapter;
//import courses.android.com.todo.signup;
import java.util.List;
import java.util.Map;

public class Info extends AppCompatActivity {
  List<myuser> credentials;
  RecyclerView.LayoutManager nLayoutManager;
  RecyclerView recyclerView2;
  AppDatabase db1;
  CheckBox checkBox;
  infoAdapter adapter;
  FloatingActionButton fab,fab2,fab3;
  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.create_todo, menu);

    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id){
      case R.id.resetp:Intent intent=new Intent(Info.this,signup.class);
        intent.putExtra("reset",true);
        startActivity(intent);
        return true;
      case R.id.deletei:
        boolean bool = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("DeleteEnabled",false);
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("DeleteEnabled",!bool).commit();
        //checkBox.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
        fab.setVisibility(View.GONE);
        fab2.setVisibility(View.VISIBLE);
        fab3.setVisibility(View.VISIBLE);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_info);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    db1 = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mylock")
        .build();
    new task1().execute("");
    //AsyncTask.execute(new Runnable() {
    //  @Override public void run() {
    //
    //    //Log.v("infoclass",db1.userDao().getUser().isEmpty()?"empty":"not");
    //    //Log.v("infoclass",credentials.isEmpty()?"empty":"not");
    //
    //  }
    //});

    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("DeleteEnabled",false).commit();



    fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent=new Intent(Info.this,addCredentials.class);
        startActivity(intent);
      }
    });
    fab2 = (FloatingActionButton) findViewById(R.id.fab2);
    fab3 = (FloatingActionButton) findViewById(R.id.fab3);
    fab2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        new task2().execute("");
        fab2.setVisibility(View.GONE);
        fab3.setVisibility(View.GONE);
        Intent intent = getIntent();
        startActivity(intent);
        finish();
        Toast.makeText(Info.this,"Action successfully performed",Toast.LENGTH_SHORT).show();
      }
    });
    fab3.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //checkBox.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.GONE);
        //fab2.setVisibility(View.GONE);
        boolean bool = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("DeleteEnabled",true);
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("DeleteEnabled",!bool).commit();
        adapter.notifyDataSetChanged();
        fab3.setVisibility(View.GONE);
      }
    });


    //Runnable r=new Runnable(){
    //  @Override public void run() {
    //
    //    recyclerView=findViewById(R.id.recyclerView2);
    //    nLayoutManager = new LinearLayoutManager(getApplication());
    //    recyclerView.setItemAnimator(new DefaultItemAnimator());
    //    infoAdapter adapter = new infoAdapter(getApplicationContext(), credentials);
    //    recyclerView.setAdapter(adapter);
    //    recyclerView.setLayoutManager(nLayoutManager);
    //  }
    //};
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    //Intent intent=new Intent(Info.this, MainActivity.class);
    //startActivity(intent);
    finish();
  }
  public class task1 extends AsyncTask<String,String,String> {

    @Override protected String doInBackground(String... strings) {
      credentials = db1.userDao().getUser();
      return null;
    }

    @Override protected void onPostExecute(String s) {
      super.onPostExecute(s);
      //credentials=db1.userDao().getUser();
      if (credentials!=null){
        adapter = new infoAdapter(getApplicationContext(), credentials);
      }
      recyclerView2 = findViewById(R.id.recyclerView2);
      nLayoutManager =
          new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
      recyclerView2.setItemAnimator(new DefaultItemAnimator());
      //recyclerView2.setItemAnimator(new DefaultItemAnimator());
      recyclerView2.setAdapter(adapter);
      recyclerView2.setLayoutManager(nLayoutManager);

    }
  }
  public class task2 extends AsyncTask<String, String, String>{
    @Override protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override protected String doInBackground(String... strings) {
      for (final Map.Entry<String,Boolean> entry : infoAdapter.myMap2.entrySet()){
        //Runnable r=new Runnable() {
        //  @Override public void run() {
        db1.userDao().deleteMyUser(entry.getKey());
        Log.v("mainactivity",db1.toString());
        //  }
        //};

      }
      return null;
    }

    @Override protected void onPostExecute(String s) {
      super.onPostExecute(s);
    }
  }
}


