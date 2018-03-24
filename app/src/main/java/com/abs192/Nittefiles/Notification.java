package com.abs192.Nittefiles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.widget.ScrollView;
import android.widget.TextView;
import com.pushbots.*;

public class Notification extends Activity {
    String str = "";
    TextView message;
    SharedPreferences pos;
public Notification()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        pos = getSharedPreferences(Global.Notice, 0);

        Global.notices = pos.getString("pwd", "");

        ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
        message = (TextView) findViewById(R.id.message);
        Bundle extras = getIntent().getExtras();
        str = extras.getString("message");
        Global.notices = Global.notices + str + "\n";

        message.setText(str);
        SharedPreferences.Editor editor = pos.edit();
        editor.putString("pwd", Global.notices);
        editor.commit();
        // String str= getIntent().getStringExtra("notifications");

        // Intent n1 = new Intent(this,Collected.class);
        // n1.putExtra("mystring",Global.notices);

        //startActivity(n1);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent n1 = new Intent(this, MainActivity.class);
            startActivity(n1);
            return true;

        } else {

            return super.onKeyDown(keyCode, event);
        }

    }
}