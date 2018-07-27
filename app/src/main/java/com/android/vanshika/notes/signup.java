package com.android.vanshika.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.vanshika.notes.R;
//import courses.android.com.todo.frag.Safe;
import com.android.vanshika.notes.frag.Safe;
import java.util.Objects;


public class signup extends AppCompatActivity implements TextWatcher,View.OnKeyListener,View.OnFocusChangeListener {

  protected static EditText es_digit1, es_digit2, es_digit3, es_digit4;

  private int whoHasFocus;
  private static char[] code = new char[4];
  TextView welx,intro,repin,crepin;

  //private final Handler mHideHandler = new Handler();
  private LinearLayout mContentView;

  private boolean mVisible;


  // @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
    super.onCreate(savedInstanceState);
    getSupportActionBar().hide();
    setContentView(R.layout.activity_signup);

    es_digit1 = (EditText) findViewById(R.id.es1);
    es_digit2 = (EditText) findViewById(R.id.es2);
    es_digit3 = (EditText) findViewById(R.id.es3);
    es_digit4 = (EditText) findViewById(R.id.es4);
    //Boolean yourBool = Objects.requireNonNull(getIntent().getExtras()).getBoolean("reset");
    if(getIntent().getBooleanExtra("reset",false)){
      findViewById(R.id.welc).setVisibility(View.GONE);
      findViewById(R.id.intro).setVisibility(View.GONE);
      repin=findViewById(R.id.createNew);
      repin.setText("Recreate your security pin");
      es_digit1.setText("");
      es_digit2.setText("");
      es_digit3.setText("");
      es_digit4.setText("");
    }else{
      repin=findViewById(R.id.createNew);
      repin.setText("Create a security pin");
    }
    setListners();

    mVisible = true;
    //mControlsView = findViewById(R.id.fullscreen_content_controls);
    mContentView = findViewById(R.id.fullscreen_content);

    // Set up the user interaction to manually show or hide the system UI.


    //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    // ye method execute krwa. button pr click k
    findViewById(R.id.dummy_button).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(signup.this);; // 0 - for private mode
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("firstTime", true);
        editor.putString("password",String.valueOf(code[0]) + String.valueOf(code[1]) + String.valueOf(code[2]) + String.valueOf(code[3]));
        editor.commit();
        Intent intent=new Intent(signup.this, Safe.class);
        intent.putExtra("coming",1);
        startActivity(intent);
        finish();
      }
    });
  }

  private void setListners() {
    es_digit1.addTextChangedListener(this);
    es_digit2.addTextChangedListener(this);
    es_digit3.addTextChangedListener(this);
    es_digit4.addTextChangedListener(this);

    es_digit1.setOnKeyListener(this);
    es_digit2.setOnKeyListener(this);
    es_digit3.setOnKeyListener(this);
    es_digit4.setOnKeyListener(this);

    es_digit1.setOnFocusChangeListener(this);
    es_digit2.setOnFocusChangeListener(this);
    es_digit3.setOnFocusChangeListener(this);
    es_digit4.setOnFocusChangeListener(this);
  }
  @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override public void afterTextChanged(Editable editable) {
    switch (whoHasFocus) {
      case 1:
        if (!es_digit1.getText().toString().isEmpty()) {
          code[0] = es_digit1.getText().toString().charAt(0);
          es_digit2.requestFocus();
        }
        Log.e("case 1",String.valueOf(code[0]));
        break;
      case 2:
        if (!es_digit2.getText().toString().isEmpty()) {
          code[1] = es_digit2.getText().toString().charAt(0);
          es_digit3.requestFocus();
        }
        Log.e("case 2",String.valueOf(code[1]));
        break;

      case 3:
        if (!es_digit3.getText().toString().isEmpty()) {
          code[2] = es_digit3.getText().toString().charAt(0);
          es_digit4.requestFocus();
        }
        Log.e("case 3",String.valueOf(code[2]));
        break;

      case 4:
        if (!es_digit4.getText().toString().isEmpty()) {
          code[3] = es_digit4.getText().toString().charAt(0);
          //checkPin();
        }
        Log.e("case 4",String.valueOf(code[3]));
        break;

      default:
        break;
    }
  }




  //private void hide() {
  //  // Hide UI first
  //  ActionBar actionBar = getSupportActionBar();
  //  if (actionBar != null) {
  //    actionBar.hide();
  //  }
  //  mControlsView.setVisibility(View.GONE);
  //  mVisible = false;
  //
  //
  //}



  @Override public boolean onKey(View view, int i, KeyEvent keyEvent) {
    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
    {
      if (i == KeyEvent.KEYCODE_DEL)
      {
        switch(view.getId())
        {
          case R.id.es2:
            if (es_digit2.getText().toString().isEmpty())
              es_digit1.requestFocus();
            break;

          case R.id.es3:
            if (es_digit3.getText().toString().isEmpty())
              es_digit2.requestFocus();
            break;

          case R.id.es4:
            if (es_digit4.getText().toString().isEmpty())
              es_digit3.requestFocus();
            break;

          default:
            break;
        }
      }
    }
    return false;
  }

  @Override public void onFocusChange(View view, boolean b) {
    switch (view.getId()) {
      case R.id.es1:
        whoHasFocus = 1;
        break;

      case R.id.es2:
        whoHasFocus = 2;
        break;

      case R.id.es3:
        whoHasFocus = 3;
        break;

      case R.id.es4:
        whoHasFocus = 4;
        break;

      default:
        break;
    }
  }
}
