package com.abs192.Nittefiles.frags;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.R;
import com.abs192.Nittefiles.Cred;
import com.abs192.Nittefiles.MainActivity;

import com.abs192.Nittefiles.main;
import com.abs192.Nittefiles.misc.FetchPage;
import com.abs192.Nittefiles.misc.InternetCheck;
import com.abs192.Nittefiles.misc.logtheshit;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragment;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

import org.w3c.dom.Text;

import java.util.Random;

public class Fragforum extends SherlockFragment {

    TextView tvm;WebView web;CardView cvm;Button reload,back;

    public Fragforum() {
        super();
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(com.abs192.Nittefiles.R.layout.forum, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        try {
            back=(Button)getView().findViewById(com.abs192.Nittefiles.R.id.button2);
           reload= (Button)getView().findViewById(com.abs192.Nittefiles.R.id.button3);
            web = (WebView)getView().findViewById(com.abs192.Nittefiles.R.id.webView1);
            web.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(scrollY>1500) {
                        back.setVisibility(View.INVISIBLE);
                        reload.setVisibility(View.INVISIBLE);
                    }
                    else if(oldScrollY >500)
                    {
                        back.setVisibility(View.VISIBLE);
                        reload.setVisibility(View.VISIBLE);
                    }
                }
            });
            //final WebView web = new WebView(getActivity());
            web.getSettings().setJavaScriptEnabled(true);
            if (InternetCheck.haveInternet(getSherlockActivity()))

            { web.loadUrl("http://www.google.com");
                Toast.makeText(getActivity(),"Please wait", Toast.LENGTH_LONG).show();
                web.setWebViewClient(new myWebClient());
                web.setWebChromeClient(new WebChromeClient());
                //tvm.setOnClickListener();
                //  setContentView(web);
                back.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        if(web.canGoBack())
                        {
                            web.goBack();
                        }
                        else
                        {
                            backButtonHandler();
                        }


                    }
                });
                reload.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String rl=web.getUrl();
                        web.loadUrl(rl);
                    }



                });
            }

            else
                Toast.makeText(getActivity(),"Sorry...Please recheck your internet connection!", Toast.LENGTH_LONG).show();

        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);


        }
    }

    //flip screen not loading again
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }



    //  @Override
  /*  public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN)
        {
            switch(keyCode)
            {
                case KeyEvent.KEYCODE_BACK:
                    if(web.canGoBack())
                    {
                        web.goBack();
                    }
                    else
                    {
                        backButtonHandler();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }*/


    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());


        // Setting Dialog Title
        // Setting Dialog Message

        alertDialog.setTitle("Your App Name");

        // I've included a simple dialog icon in my project named "dialog_icon", which's image file is copied and pasted in all "drawable" folders of "res" folders of the project. You can include any dialog image of your wish and rename it to dialog_icon.


        alertDialog.setMessage("Exit Now?");

        // Setting Icon to Dialog
        // Setting Positive "Yes" Button

        alertDialog.setPositiveButton("Exit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        // Setting Negative "NO" Button

        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }
    @Override
    public void onStop() {
        super.onStop();
        try {
            // EasyTracker.getInstance(this).activityStop(this);
        } catch (Exception e) {

        }
    }
}
