package com.personal.sajed.smsapp;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int REQUEST_CODE_ASK_PERMISSION=123;
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, 123);

        readSMS();
    }

    void readSMS(){
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
            Log.i("reading...","reading");
            if (cursor.moveToFirst()) { // must check the result to prevent exception
                do {

                    String msgData = "";
                    for(int idx=0;idx<cursor.getColumnCount();idx++)
                    {
                        msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                    }

                    Log.i("sms1",msgData);
                    // use msgData
                } while (cursor.moveToNext());
            } else {
                // empty box, no SMS
            }
        }
    }
}
