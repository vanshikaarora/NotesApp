package com.android.vanshika.notes;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.vanshika.notes.data.AppDatabase;
import com.android.vanshika.notes.data.user;
//import courses.android.com.todo.data.AppDatabase;
//import courses.android.com.todo.data.user;
//import courses.android.com.todo.frag.Safe;
import com.android.vanshika.notes.frag.Safe;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
  RecyclerView recyclerView;
  CheckBox checkBox;
  RecyclerView.LayoutManager mLayoutManager;
  //public ArrayList<Global> items;
  private Paint p = new Paint();
  public TextView mEmpty;
  AppDatabase db;
  List<user> items;
  listAdapter adapter;
  ImageButton button;
  FloatingActionButton fab,fab2,fab3;
  public static final String PREFS_NAME = "MyPrefsFile";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("DeleteEnabled",false).commit();
    //if(items.isEmpty()){
    //  mEmpty=(TextView) findViewById(R.id.empty); laptop charge ho gaya, charger nikal de.hehehe nikal deti hun
    //  mEmpty.setVisibility(View.VISIBLE);
    //}

    db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "users")
        .build();
    new task().execute("");
    //button=findViewById(R.id.delete_button);
    //button.setOnClickListener(new View.OnClickListener() {
    //  @Override public void onClick(View view) {
    //
    //    }
    //  }
    //});
    /*
    //but sir jabh pehle ek hi room tha tabh toh sabh work kar rha tha, caches delete krke restart krke dekh, fir chal jna chahiye.ok sir but sir ek aur problem thi dasso
    runOnUiThread(new Runnable() {
      @Override public void run() {
        items = db.userDao().getAllItems();
        recyclerView = findViewById(R.id.recyclerView);
        mLayoutManager =
            new LinearLayoutManager(getApplication(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        listAdapter adapter = new listAdapter(getApplicationContext(), items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayoutManager);

      }
    });yahin par sir?
    */

    //new Runnable() {
    //  @Override public void run() {
    //  }
    //}.run();//yeh waala jo code h yeh maine swipe kk delete waale feature k liye daala tha but yeh bhi nhi chal rha tha
    // ye dekhna pdega baith kr, abhi mujhe thoda kaaaad dekhta hun. tb tk tu sir maar le isme.ok sir

    //catch(JSONException e){
    //    Log.d(TAG, e.getMessage());
    //  }
    //Thread newThread = new Thread(r);
    //newThread.start();
    //items=new ArrayList<>();
    //items.add(new Global("MyToDO",""));

    fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, createTodo.class);
        startActivity(intent);
      }
    });
    fab2=(FloatingActionButton) findViewById(R.id.fab2);
    fab2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        new  task1().execute("");
        fab2.setVisibility(View.GONE);
        fab3.setVisibility(View.GONE);
        Intent intent = getIntent();
        startActivity(intent);
        finish();
        Toast.makeText(MainActivity.this,"Items successfully deleted",Toast.LENGTH_SHORT).show();
      }
    });
    fab3=(FloatingActionButton) findViewById(R.id.fab3);
    fab3.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //checkBox.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.GONE);
        fab2.setVisibility(View.GONE);
        Toast.makeText(MainActivity.this,"Cancelled",Toast.LENGTH_SHORT).show();
      }
    });


  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.abhi bhi sirf last pe aaya#face_palm firse sirf last
    int id = item.getItemId();
    switch (id) {
      case R.id.action_settings:
        boolean bool = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("DeleteEnabled",false);
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("DeleteEnabled",!bool).commit();
        adapter.notifyDataSetChanged();
        fab.setVisibility(View.GONE);
        fab2.setVisibility(View.VISIBLE);
        fab3.setVisibility(View.VISIBLE);
        return true;
      case R.id.secure:
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        ////Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("firstTime", false);

        if (!hasLoggedIn) {
          Intent intent = new Intent(MainActivity.this, signup.class);
          startActivity(intent);
        } else {
          Intent intent = new Intent(MainActivity.this, Safe.class);
          startActivity(intent);
        }
    }

    return super.onOptionsItemSelected(item);
  }

  public class task extends AsyncTask<String, String, String> {

    @Override protected String doInBackground(String... strings) {
      items = db.userDao().getAllItems();
      return null;
    }

    @Override protected void onPostExecute(String s) {
      super.onPostExecute(s);
      recyclerView = findViewById(R.id.recyclerView);
      final LinearLayout noteScreen = findViewById(R.id.empty);
      if (items.isEmpty()) {

        noteScreen.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        Log.v("main", "db is null");
      } else {
        noteScreen.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        mLayoutManager =
            new LinearLayoutManager(getApplication(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new listAdapter(getApplicationContext(), items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayoutManager);
      }
    }
  }
  public class task1 extends AsyncTask<String, String, String>{

    @Override protected String doInBackground(String... strings) {
      for (final Map.Entry<String,Boolean> entry : listAdapter.myMap.entrySet()){
        //Runnable r=new Runnable() {
        //  @Override public void run() {
        db.userDao().deleteUser(entry.getKey());
        Log.v("mainactivity",db.toString());
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