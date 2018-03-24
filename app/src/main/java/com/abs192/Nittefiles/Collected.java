package com.abs192.Nittefiles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;

import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.abs192.Nittefiles.MainActivity;
import com.abs192.Nittefiles.R;
import com.abs192.Nittefiles.frags.FragHome;
import com.abs192.Nittefiles.frags.FragTeacher;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import com.abs192.Nittefiles.frags.FragHome;
import com.abs192.Nittefiles.misc.CustomListViewAdapter;
import com.abs192.Nittefiles.misc.RowItem;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Collected extends Activity {
    public Collected()
    {

    }
    ListView lvv;ArrayList<RowItem> rowItems = new ArrayList<RowItem>();
public String str="";TextView tv1;SharedPreferences pos;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collected);
       // Intent intent = getIntent();
       // str= intent.getStringExtra("mystring");
        //Global.notices = Global.notices + str;
      //  SharedPreferences pos;


       // ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
      //  message = (TextView) findViewById(R.id.message);


      //  message.setText(str);


        pos = getSharedPreferences(Global.Notice, 0);
          lvv=(ListView)findViewById(R.id.listview1);
          String onenote="";
        String data = pos.getString("pwd", "");
           for(int i = 0; i <data.length();i++)
           {
               char mn=data.charAt(i);
                String nm1=Character.toString(mn);
                onenote=onenote+nm1;
               if(nm1.equals("\n"))
               {
                   RowItem item = new RowItem(1, R.drawable.ic_bell1, onenote,"nmamit",true);
                   rowItems.add(item);
                   CustomListViewAdapter adapter = new CustomListViewAdapter(getApplicationContext(),R.layout.listitem2, rowItems);

                   lvv.setAdapter(adapter);
                   onenote="";
               }
           }
       // tv1 = (TextView)findViewById(R.id.messages);
        //tv1.setText(data);



    }


}
