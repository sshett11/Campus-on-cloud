package com.abs192.Nittefiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.abs192.Nittefiles.AndroidMultiPartEntity.ProgressListener;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;


public class main extends Activity implements OnClickListener {

    private static final int REQUEST_PICK_FILE = 1;
    String uploadId;

    private TextView filePath;
    private Button Browse;
    private File selectedFile;
    Button upload;
    public String final_url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent inn1= getIntent();
         final_url=inn1.getStringExtra("urls");
         final_url=final_url.replace("http://192.168.201.1:81/", "");
        final_url=final_url.replace("%20"," ");
        filePath = (TextView)findViewById(R.id.file_path);
        Browse = (Button)findViewById(R.id.browse);
        Browse.setOnClickListener(this);
        upload=(Button)findViewById(R.id.button);
        //upload.setOnClickListener(this);

        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
     //           new sendpost().execute();

                uploadMultipart(getApplicationContext());
                filePath.setText(selectedFile.getPath() + "got it" + final_url);
            }
        });
    }

    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.browse:
                Intent intent = new Intent(this,FilePicker.class);
                startActivityForResult(intent, REQUEST_PICK_FILE);
                break;

            default:


                  break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            switch(requestCode) {

                case REQUEST_PICK_FILE:

                    if(data.hasExtra(FilePicker.EXTRA_FILE_PATH)) {

                        selectedFile = new File(data.getStringExtra(FilePicker.EXTRA_FILE_PATH));
                        filePath.setText(selectedFile.getPath());
                    }
                    break;
            }
        }


    }


    public void uploadMultipart(final Context context) {
        try {
            uploadId =
                    new MultipartUploadRequest(getApplicationContext(), "http://192.168.201.1:81/fileUploads.php?urls="+ URLEncoder.encode(final_url))
                            .addFileToUpload(selectedFile.getPath(), "Fileupload")
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(20)
                            .startUpload();

        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
    }


}