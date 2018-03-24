package com.abs192.Nittefiles;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pushbots.push.Pushbots;


public  class TeacherLogin extends Activity implements View.OnClickListener{

    TextView tv;
    EditText et1;
    EditText et2;
    public int chk;
   // @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher);



        tv = (TextView) findViewById(R.id.tv1);
        tv.setOnClickListener(this);
        et1 = (EditText) findViewById(R.id.ed1);
        et2 = (EditText) findViewById(R.id.ed2);
        //loadData();


    }


    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.tv1:
                if(et1.getText().toString().equals("user") && et2.getText().toString().equals("pass"))
                {


                    Global.posfin++;
                    Global.x=0;
                    Toast.makeText(getApplicationContext(),"VALID", Toast.LENGTH_SHORT).show();
                    Intent n=new Intent(this, MainActivity.class);

                    startActivity(n);
                    //Toast.makeText(getActivity(),"Successful", Toast.LENGTH_LONG).show();


                   //  n.putExtra("check",chk);
                    //startActivity(n);
                }
                else
                    Toast.makeText(getApplicationContext(),"Incorrect credentials;Enter again!", Toast.LENGTH_LONG).show();
                break;
            default:

                break;

        }
    }

}
