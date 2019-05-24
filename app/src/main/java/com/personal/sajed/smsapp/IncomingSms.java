package com.personal.sajed.smsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.net.URLEncoder;

/**
 * Created by Sajed on 08-Oct-16.
 */
public class IncomingSms extends BroadcastReceiver {
    final SmsManager smsManager= SmsManager.getDefault();

    public void onReceive(Context context, Intent intent){
        final Bundle bundle = intent.getExtras();

        try{
            if(bundle!=null){
                final Object[] pdusOjects = (Object[]) bundle.get("pdus");

                for(int i=0; i<pdusOjects.length; i++){
                    SmsMessage smsMessage;
                    if(Build.VERSION.SDK_INT>=23){
                        smsMessage = SmsMessage.createFromPdu((byte[]) pdusOjects[i], Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
                    }
                    else{
                        smsMessage = SmsMessage.createFromPdu((byte[]) pdusOjects[i]);
                    }

                    String from = smsMessage.getDisplayOriginatingAddress();
                    String smsBody = smsMessage.getMessageBody();
                    long smsTime= smsMessage.getTimestampMillis();

                    Toast toast = Toast.makeText(context, "Sender "+ from+" Message " + smsBody, Toast.LENGTH_LONG);
                    toast.show();

                    //from = URLEncoder.encode(from, "UTF-8");
                    //smsBody= URLEncoder.encode(smsBody, "UTF-8");

                    SendSmsTask sendSmsTask = new SendSmsTask();
                    //sendSmsTask.execute("http://192.168.1.138/smsApp/index.php?SmsFrom="+from+"&SmsBody="+smsBody);

                    String queryString = "from: "+ from + ", timeStamp: " + smsTime + ", smsBody: "+ smsBody;
                    Log.i("queryString", queryString);
                    queryString = URLEncoder.encode(queryString, "UTF-8");

                    sendSmsTask.execute("http://lmgmt.acland.gov.bd/index.php?option=track&msg="+queryString);

                    //  http://localhost/smsApp/index.php?SmsFrom=%2212456789%22&SmsBody=%22This%20is%20a%20test%20sms%22

                    Log.i("sms", "Msg received");
                }
            }
        }catch (Exception e){
            Log.e("Sms Receiver","Exception "+e);
        }

    }
}
