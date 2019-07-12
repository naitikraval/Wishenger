package com.wishenger.android.wishengerr;


import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wishenger.android.wishengerr.AlarmManager.MyBroadcastReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SaveMessage extends AppCompatActivity {

    private static final String CHANNEL_ID = "Wishenger";
    private static final String CHANNEL_NAME = "Wishenger";
    private static final String CHANNEL_DESC = "Wishenger Notification";

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    private DatePickerDialog.OnDateSetListener mDateSetListner;
    static final int RESULT_PICK_CONTACT = 1;

    TextView phone_no, tv_date, tv_time,id_no,hr12TimeForemat, updatephone_no, updatetv_date, updatetv_time;
    EditText editText, editText1, update_editText, update_editText1;
    ImageButton  updatecontact_list;
    Button date,contact_list, time, save_btn, updatedate, updatetime, update_btn, delete_btn;
    ListView listView_msg;
    AlertDialog alertDialog;
    Context context;
    private ProgressDialog progressDialog;
    List<SaveMsgModelClass> msgList;
    Ringtone ringtone;
    String msgId;
    FirebaseUser user;
    String uid;
    DatabaseReference msgDatabase;
    int _id;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_message);

        context = this;
        msgList = new ArrayList<>();
//        listView_msg = findViewById(R.id.msgListView);


        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();
        msgDatabase = FirebaseDatabase.getInstance().getReference("MsgDetails").child(uid);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


//        FloatingActionButton fabbtn = (FloatingActionButton) findViewById(R.id.fab_btn);



        editText = (EditText) findViewById(R.id.et_name);
        editText1 = (EditText) findViewById(R.id.et_msg);
        date = (Button) findViewById(R.id.select_date);
        time = (Button) findViewById(R.id.select_time);
        tv_date = (TextView) findViewById(R.id.getDate);
        tv_time = (TextView) findViewById(R.id.getTime);
        phone_no = (TextView)findViewById(R.id.contact_no);
        contact_list = (Button) findViewById(R.id.contact_no_list);
        save_btn = (Button) findViewById(R.id.save);
        id_no=(TextView) findViewById(R.id.idnumber);
        hr12TimeForemat=(TextView)findViewById(R.id.am_pm_time);

        String quotes= getIntent().getStringExtra("quote");
        editText1.setText(quotes);

        Random r = new Random();
        int i1 = r.nextInt(2147483647 - 1) + 1;

        id_no.setText( String.valueOf(i1));

        contact_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
            }
        });

        final Calendar calendar = Calendar.getInstance();

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(SaveMessage.this, new OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String status = "AM";

                        if(hourOfDay > 11)
                        {
                            // If the hour is greater than or equal to 12
                            // Then the current AM PM status is PM
                            status = "PM";
                        }

                        // Initialize a new variable to hold 12 hour format hour value
                        int hour_of_12_hour_format;

                        if(hourOfDay > 11){

                            // If the hour is greater than or equal to 12
                            // Then we subtract 12 from the hour to make it 12 hour format time
                            hour_of_12_hour_format = hourOfDay - 12;
                        }
                        else {
                            hour_of_12_hour_format = hourOfDay;
                        }
                        hr12TimeForemat.setText(hour_of_12_hour_format + ":" + minute+ " " + status);
                        tv_time.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(SaveMessage.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListner,
                        year, month, day);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                datePickerDialog.show();
            }
        });

        mDateSetListner = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int month_x = month + 1;

                String date = dayOfMonth + "-" + month_x + "-" + year;
                tv_date.setText(date);

            }
        };

        //Save button

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMsg();
            }
        });

    }

    //check internet



    @Override
    protected void onResume() {
        super.onResume();
        CheckInternet();

    }

    public void CheckInternet() {
        if (!isNetworkAvailable()) {
            Intent NoInternetIntent = new Intent(SaveMessage.this, InternetActivity.class);
            startActivity(NoInternetIntent);
            finish();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    Cursor cursor = null;
                    try {
                        String phoneNo = null;
                        String name = null;
                        Uri uri = data.getData();
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int phoneIndex1 = cursor.getColumnIndex(Phone.DISPLAY_NAME);
                        phoneNo = cursor.getString(phoneIndex);
                        name = cursor.getString(phoneIndex1);

                        phone_no.setText(phoneNo);
                        editText.setText(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            Toast.makeText(this, "Failed to pick contact", Toast.LENGTH_SHORT).show();
        }
    }

    //add message method

    public void addMsg() {
        String name = editText.getText().toString().trim();
        String msg = editText1.getText().toString();
        String date = tv_date.getText().toString();
        String time = tv_time.getText().toString();
        String contact_no = phone_no.getText().toString();
        String idno1= id_no.getText().toString();
        String _hr12Time= hr12TimeForemat.getText().toString();

        if (TextUtils.isEmpty(name)) {
            editText.setError("Field Required!");
            return;
        } else if (TextUtils.isEmpty(msg)) {
            editText1.setError("Field Required!");
        } else if (TextUtils.isEmpty(date)) {
            tv_date.setError("Field Required!");
        }
//        else if (TextUtils.isEmpty(time)) {
//            tv_time.setError("Field Required!");
//        }
        else if (TextUtils.isEmpty(_hr12Time)) {
            hr12TimeForemat.setError("Field Required!");
        }
        else if (TextUtils.isEmpty(contact_no) || phone_no.length()<10) {
            phone_no.setError("Field Required!");
        } else

        {
            msgId = msgDatabase.push().getKey();

            setMsg();
            finish();
        }
    }

    public void setMsg()
    {
        String name = editText.getText().toString().trim();
        String msg = editText1.getText().toString();
        String date = tv_date.getText().toString();
        String time = tv_time.getText().toString();
        String contact_no = phone_no.getText().toString();
        String idnumber= id_no.getText().toString();
        String _hr12Time = hr12TimeForemat.getText().toString();


        SaveMsgModelClass saveMsgModelClass = new SaveMsgModelClass(msgId, name, msg, date, time, contact_no,idnumber,_hr12Time);
        String convertDate = saveMsgModelClass.getDate();
        String convertTime = saveMsgModelClass.getTime();
        String mobileNo = saveMsgModelClass.getContact_no();
        String txtMessage = saveMsgModelClass.getMsg();
        String ampmTime = saveMsgModelClass.get_hr12Time();

        String dateTime = convertDate+" "+convertTime;

//
//        SharedPreferences.Editor editor = getSharedPreferences("sharedpref", MODE_PRIVATE).edit();
//        editor.putString("message", txtMessage);
//        editor.putString("phone", mobileNo);
//        editor.putString("datetime", dateTime);
//        editor.putString("uid",idnumber);
//        editor.apply();

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
            Toast.makeText(SaveMessage.this, "INVALID DATE/TIME", Toast.LENGTH_SHORT).show();

        } else {

            saveMsgModelClass = new SaveMsgModelClass(msgId, name, msg, date, time, contact_no,idnumber,_hr12Time);
            msgDatabase.child(msgId).setValue(saveMsgModelClass);

            String pNo1 = mobileNo;
            String tMsg1 = txtMessage;


            Intent intent = new Intent(getBaseContext(), MyBroadcastReceiver.class);
            intent.putExtra("phone", pNo1);
            intent.putExtra("message", tMsg1);
            intent.putExtra("uid",idnumber);
            intent.putExtra("name",name);

//            final int _id = (int) System.currentTimeMillis();
            _id = Integer.parseInt(idnumber);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),_id, intent, PendingIntent.FLAG_ONE_SHOT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            assert alarmManager != null;
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);


            Uri urialarm = RingtoneManager.getDefaultUri(R.raw.stairs);
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.SEND_SMS)) {
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.SEND_SMS},
                            MY_PERMISSIONS_REQUEST_SEND_SMS);
                }
            }

            if (cal.getTime().equals(currentDateTimeString)) {

                ringtone = RingtoneManager.getRingtone(getApplicationContext(), urialarm);

            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription(CHANNEL_DESC);
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }
            Toast.makeText(this, "Message Set Successfully !", Toast.LENGTH_SHORT).show();
        }


//        Toast.makeText(this, "Message Saved Successfully!", Toast.LENGTH_SHORT).show();
//        alertDialog.dismiss();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

}

