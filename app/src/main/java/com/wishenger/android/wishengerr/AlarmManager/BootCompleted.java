package com.wishenger.android.wishengerr.AlarmManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BootCompleted extends BroadcastReceiver {
    SharedPreferences prefs;
    String uid;
    String convertTime;
    String convertDate;
    int _id;
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            prefs = context.getSharedPreferences("sharedpref", Context.MODE_PRIVATE);
//        String smsText=prefs.getString("message","no name");
//        String smsNumber=prefs.getString("phone","no name");

            Gson gson = new Gson();
            String json = prefs.getString("MyObject", "");

            try {
                JSONArray jsonArr = new JSONArray(json);

                for (int j = 0; j < jsonArr.length(); j++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(j);
                    uid = jsonObj.optString("_id");
                    String id1 = jsonObj.optString("contact_no");
                    convertDate = jsonObj.optString("date");
                    String id3 = jsonObj.optString("msg");
                    String id4 = jsonObj.optString("msgId");
                    String Pname = jsonObj.optString("name");
                    convertTime = jsonObj.optString("time");


//                    String dateTime = prefs.getString("datetime", "no name");
//                    String uid = prefs.getString("uid", "no name");

                    String dateTime = convertDate + " " + convertTime;


                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    Date date1 = null;
                    try {
                        date1 = simpleDateFormat.parse(dateTime);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    final Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date1);

                    Calendar current = Calendar.getInstance();

                    Calendar cal = Calendar.getInstance();
                    cal.set(calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE));

                    if (cal.compareTo(current) <= 0) {
                        Toast.makeText(context, "INVALID DATE/TIME", Toast.LENGTH_SHORT).show();
                        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            assert v != null;
                            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            assert v != null;
                            v.vibrate(500);
                        }
                    } else {
                        _id = Integer.parseInt(uid);
                        Intent i = new Intent(context, RebootBroadcast.class);
                        i.putExtra("pnumber",id1);
                        i.putExtra("msg",id3);
                        i.putExtra("uid",uid);
                        i.putExtra("msgTo",Pname);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, _id, i, PendingIntent.FLAG_ONE_SHOT);
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                        assert alarmManager != null;
                        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);


    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            throw new UnsupportedOperationException("Not yet implemented");
        }

    }

    public void cancelMsg()
    {
//        int _ids = Integer.parseInt(uid);
        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, _id, intent, PendingIntent.FLAG_ONE_SHOT);
        pendingIntent.cancel();
        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
    }
}

