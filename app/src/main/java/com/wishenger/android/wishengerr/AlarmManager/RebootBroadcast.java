package com.wishenger.android.wishengerr.AlarmManager;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.wishenger.android.wishengerr.R;

import java.util.ArrayList;

public class RebootBroadcast extends BroadcastReceiver {
    MediaPlayer mp;
    int _id;
    SharedPreferences prefs;
    String smsNumber, smsText;
    Bundle bundle;

    private static final String CHANNEL_ID = "Wishenger";
    private static final String CHANNEL_NAME = "Wishenger";
    private static final String CHANNEL_DESC = "Wishenger Notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        mp = MediaPlayer.create(context, R.raw.stairs);
        mp.start();
        prefs = context.getSharedPreferences("sharedpref", Context.MODE_PRIVATE);

        bundle = intent.getExtras();
        assert bundle != null;

        String receiverName = bundle.getString("Pname");
        String uid = bundle.getString("uid");
        _id = Integer.parseInt(uid);

        try {
            sendSMS(intent);
            switch (getResultCode()) {
                case Activity.RESULT_CANCELED:
                    Toast.makeText(context, "Message Sent!",
                            Toast.LENGTH_SHORT).show();

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(context, CHANNEL_ID)
                                    .setSmallIcon(R.drawable.logo)
                                    .setContentTitle("Message Sent!")
                                    .setContentText(smsText)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                    notificationManagerCompat.notify(_id, mBuilder.build());

                    break;

            }
        } catch (Exception e) {
//            Toast.makeText(context, "Message doesn't sent", Toast.LENGTH_SHORT).show();
            switch (getResultCode()) {
                case Activity.RESULT_CANCELED:
                    Toast.makeText(context, "Sms Not Sent",
                            Toast.LENGTH_SHORT).show();

                    NotificationCompat.Builder Builder =
                            new NotificationCompat.Builder(context, CHANNEL_ID)
                                    .setSmallIcon(R.drawable.logo)
                                    .setContentTitle("Message failed to sent!")
                                    .setContentText(smsText)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    NotificationManagerCompat mnotificationManagerCompat = NotificationManagerCompat.from(context);
                    mnotificationManagerCompat.notify(_id, Builder.build());

                    break;
            }
            e.printStackTrace();
        }


        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            assert v != null;
            v.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            assert v != null;
            v.vibrate(150);
        }

    }

    private void sendSMS(Intent intent) {
        SmsManager smsManager = SmsManager.getDefault();

        smsNumber = bundle.getString("pnumber");
        smsText = bundle.getString("msg");

        ArrayList<String> parts = smsManager.divideMessage(smsText);
        smsManager.sendMultipartTextMessage(smsNumber, null, parts, null, null);
    }
}

