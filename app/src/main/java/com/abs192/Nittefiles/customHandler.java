package com.abs192.Nittefiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.pushbots.push.PBNotificationIntent;
import com.pushbots.push.Pushbots;
import com.pushbots.push.utils.PBConstants;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;

public class customHandler extends BroadcastReceiver
{


    private static final String TAG = "customHandler";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //Bundle extras = getIntent.getExtras();
      //  extras.getString("YOUR_CUSTOM_FIELD");
        String action = intent.getAction();
       Log.d(TAG, "action=" + action);
        // Handle Push Message when opened
        if (action.equals(PBConstants.EVENT_MSG_OPEN)) {
            //Check for Pushbots Instance
            Pushbots pushInstance = Pushbots.sharedInstance();
            if(!pushInstance.isInitialized()){
              // Log.d("Initializing Pushbots.");
                Pushbots.sharedInstance().init(context.getApplicationContext());
            }

            //Clear Notification array
            if(PBNotificationIntent.notificationsArray != null)
            {
                PBNotificationIntent.notificationsArray = null;
            }

            HashMap<?, ?> PushdataOpen = (HashMap<?, ?>) intent.getExtras().get(PBConstants.EVENT_MSG_OPEN);
          Log.w(TAG, "User clicked notification with Message: " + PushdataOpen.get("message"));

            //Report Opened Push Notification to Pushbots
            if(Pushbots.sharedInstance().isAnalyticsEnabled()){
                Pushbots.sharedInstance().reportPushOpened( (String) PushdataOpen.get("PUSHANALYTICS"));
            }

            //Start lanuch Activity
            String packageName = context.getPackageName();
            Intent resultIntent = new Intent(context.getPackageManager().getLaunchIntentForPackage(packageName));
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);

             resultIntent.putExtras(intent.getBundleExtra("pushData"));
           // String str=intent.getBundleExtra("pushData");
            Pushbots.sharedInstance().startActivity(resultIntent);

            // Handle Push Message when received
        }else if(action.equals(PBConstants.EVENT_MSG_RECEIVE)){
            HashMap<?, ?> PushdataOpen = (HashMap<?, ?>) intent.getExtras().get(PBConstants.EVENT_MSG_RECEIVE);
            Log.w(TAG, "User Received notification with Message: " + PushdataOpen.get("message"));
        }
    }
}