package com.android.vanshika.notes.frag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import com.android.vanshika.notes.R;
//import courses.android.com.todo.MainActivity;
//import courses.android.com.todo.R;

public class Safe extends AppCompatActivity
    implements TextWatcher, View.OnKeyListener, View.OnFocusChangeListener {
  protected static EditText et_digit1, et_digit2, et_digit3, et_digit4;
  //In this et_digit1 is Most significant digit and et_digit4 is least significant digit
  private int whoHasFocus;
  private static char[] code = new char[4];
  private static String code1 = null;
  //public static finalimage String PREFS_NAME = "MyPrefsFile";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
    super.onCreate(savedInstanceState);
    getSupportActionBar().hide();
    setContentView(R.layout.activity_safe);
    et_digit1 = (EditText) findViewById(R.id.e1);
    et_digit2 = (EditText) findViewById(R.id.e2);
    et_digit3 = (EditText) findViewById(R.id.e3);
    et_digit4 = (EditText) findViewById(R.id.e4);
    //code1=getIntent().getStringExtra("password");
    // store jahan kiya hei wo dha.
    // uninstall krke install krke chala/ok sir
    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
    // aa gaya valunhi sir maine 1234 daala tha
    ////Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
    code1 = settings.getString("password", null);
    setListners();
  }
  // close ho gaya apne aap
  private void setListners() {
    et_digit1.addTextChangedListener(this);
    et_digit2.addTextChangedListener(this);
    et_digit3.addTextChangedListener(this);
    et_digit4.addTextChangedListener(this);

    et_digit1.setOnKeyListener(this);
    et_digit2.setOnKeyListener(this);
    et_digit3.setOnKeyListener(this);
    et_digit4.setOnKeyListener(this);

    et_digit1.setOnFocusChangeListener(this);
    et_digit2.setOnFocusChangeListener(this);
    et_digit3.setOnFocusChangeListener(this);
    et_digit4.setOnFocusChangeListener(this);
  }

  @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override public void afterTextChanged(Editable editable) {

    switch (whoHasFocus) {
      case 1:
        if (!et_digit1.getText().toString().isEmpty()) {
          code[0] = et_digit1.getText().toString().charAt(0);
          et_digit2.requestFocus();
        }
        break;

      case 2:
        if (!et_digit2.getText().toString().isEmpty()) {
          code[1] = et_digit2.getText().toString().charAt(0);
          et_digit3.requestFocus();
        }
        break;

      case 3:
        if (!et_digit3.getText().toString().isEmpty()) {
          code[2] = et_digit3.getText().toString().charAt(0);
          et_digit4.requestFocus();
        }
        break;

      case 4: if (!et_digit4.getText().toString().isEmpty()) {
        code[3] = et_digit4.getText().toString().charAt(0);
        checkPin();
      }
        break;

      default:
        break;
    }
  }
  private void checkPin() {
    Log.v("code 1", String.valueOf(code1.charAt(1)));
    if ((code[0]==code1.charAt(0)
        && (code[1] == code1.charAt(1))
        && (code[2] == code1.charAt(2))
        && code[3] == code1.charAt(3))) {
      Intent intent = new Intent(Safe.this, Info.class);
      if (getIntent().getBooleanExtra("reset", false)) {
        //finish();
        Toast.makeText(this, "Password successfully changed", Toast.LENGTH_SHORT).show();
      }
      startActivity(intent);//else {and yeh activity start honi thi
      //  Toast.makeText(this, "Sorry wrong password", Toast.LENGTH_SHORT).show();
      //  Intent intent1=new Intent(Safe.this,MainActivity.class);
      //  finish();startActivity(intent);
      //}

      finish();
    } else {
      Toast.makeText(this, "Passwords incorrect", Toast.LENGTH_SHORT).show();//yeh waaala toast execute hua, aur hona kaunsa chahiye tha
    }
  }
  // konsi do files thi? dkha
  // d
  @Override public void onFocusChange(View view, boolean b) {
    // bhai, booolean value se pata chalta hei ki kis edittext ke paas focus gaya ya aaya, tune wo toh use kiya nahi hei.sir by defaultst waal pe hoga isa assume kiya h then after 1 number apne aap aage shift ho jayega focus
    switch (view.getId()) {
      case R.id.e1:
        whoHasFocus = 1;
        break;

      case R.id.e2:
        whoHasFocus = 2;
        break;

      case R.id.e3:
        whoHasFocus = 3;
        break;

      case R.id.e4:
        whoHasFocus = 4;
        break;

      default:
        break;
    }
  }

  @Override public boolean onKey(View view, int i, KeyEvent event) {
    if (event.getAction() == KeyEvent.ACTION_DOWN) {
      if (i == KeyEvent.KEYCODE_DEL) {
        switch (view.getId()) {
          case R.id.e2:
            if (et_digit2.getText().toString().isEmpty()) {
              et_digit1.requestFocus();
            }
            break;

          case R.id.e3:
            if (et_digit3.getText().toString().isEmpty()) {
              et_digit2.requestFocus();
            }
            break;

          case R.id.e4:
            if (et_digit4.getText().toString().isEmpty()) {
              et_digit3.requestFocus();
            }
            break;

          default:
            break;
        }
      }
    }
    return false;
  }
}


