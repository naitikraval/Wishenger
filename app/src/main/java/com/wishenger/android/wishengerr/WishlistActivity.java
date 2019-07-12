package com.wishenger.android.wishengerr;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wishenger.android.wishengerr.AlarmManager.MyBroadcastReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {



    ListView listView_msges;
    DatabaseReference msgDatabase;
    List<SaveMsgModelClass> msgList;
    FirebaseUser user;
    String uid;
    String msgId;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    Context context;
    SharedPreferences sharedPreferences;
    TextView updatephone_no, updatetv_date, updatetv_time,update_idno,update_hr12Time;
    EditText editText, update_editText, update_editText1;
    Button date, time, updatedate, updatetime, update_btn, delete_btn;
    SharedPreferences.Editor editor;

//    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //ad
//        mAdView = findViewById(R.id.adView);
//        MobileAds.initialize(this, "ca-app-pub-1263412267579401~4599642648");
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        sharedPreferences = getSharedPreferences("sharedpref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        context = this;
        msgList = new ArrayList<>();
        listView_msges = findViewById(R.id.msgListView1);

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();
        msgDatabase = FirebaseDatabase.getInstance().getReference("MsgDetails").child(uid);


        listView_msges.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {

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

                SaveMsgModelClass update = msgList.get(i);
                final String f = update.get_id();
                final String msg_id = update.getMsgId();
//                showUpdateDialog(update.getMsgId(), update.getName(), update.getMsg(), update.getDate(), update.getTime(), update.getContact_no(),update.get_id());


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(WishlistActivity.this);

                alertDialog.setPositiveButton("Yes", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int _ids = Integer.parseInt(f);
                        Intent intent = new Intent(getBaseContext(), MyBroadcastReceiver.class);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _ids, intent, PendingIntent.FLAG_ONE_SHOT);
                        pendingIntent.cancel();
                        assert alarmManager != null;
                        alarmManager.cancel(pendingIntent);

                        sharedPreferences.edit().clear().apply();
                        msgDatabase.child(msg_id).removeValue();

                        Toast.makeText(context, "Message Deleted", Toast.LENGTH_SHORT).show();

                    }
                });

                alertDialog.setNegativeButton("No", null);

                alertDialog.setMessage("Do you want to Delete?");
                alertDialog.setTitle("Wishenger");
                alertDialog.setIcon(R.drawable.logo);
                alertDialog.show();

                return true;
            }
        });



        //To get data from listview
        listView_msges.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//
//                final SaveMsgModelClass data = msgList.get(i);
//                String ids = data.get_id();
//
//                String s = data.getMsg();
//                String s1 = data.getContact_no();
//                Intent intent = new Intent(SaveMessage.this, alarm.class);
//                intent.putExtra("txt", s);
//                intent.putExtra("number", s1);
//                startActivity(intent);
//
//                int _ids = Integer.parseInt(ids);
//                Intent intent = new Intent(getBaseContext(), MyBroadcastReceiver.class);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _ids, intent, PendingIntent.FLAG_ONE_SHOT);
//                pendingIntent.cancel();
//                assert alarmManager != null;
//                alarmManager.cancel(pendingIntent);
//                delete_this_msg(msgId);
//                Toast.makeText(context, "Alarm canceled"+ids, Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void showUpdateDialog(final String msgId, String name, String message, String udate, String utime, String contact_no,String idno, String hr12Time) {
        builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.msg_update_dialog, null);
        builder.setView(dialogView);

        update_editText = (EditText) dialogView.findViewById(R.id.update_name);
        update_editText1 = (EditText) dialogView.findViewById(R.id.update_msg);
        updatedate = (Button) dialogView.findViewById(R.id.select_date_update);
        updatetime = (Button) dialogView.findViewById(R.id.select_time_update);
        updatetv_date = (TextView) dialogView.findViewById(R.id.update_Date);
        updatetv_time = (TextView) dialogView.findViewById(R.id.update_time);
        update_btn = (Button) dialogView.findViewById(R.id.update);
        updatephone_no = (TextView) dialogView.findViewById(R.id.up_contact_no);
        delete_btn = (Button) dialogView.findViewById(R.id.delete_msg);
        update_idno = (TextView) dialogView.findViewById(R.id.up_id_no);
        update_hr12Time = (TextView) dialogView.findViewById(R.id.up_12_hrTime);

        SaveMsgModelClass saveMsgModelClass = new SaveMsgModelClass(msgId, name, message, udate, utime, contact_no,idno,hr12Time);

        String a = saveMsgModelClass.getMsg();
        String b = saveMsgModelClass.getName();
        String c = saveMsgModelClass.getDate();
        String d = saveMsgModelClass.getTime();
        String e = saveMsgModelClass.getContact_no();
        final String f = saveMsgModelClass.get_id();
        String g= saveMsgModelClass.get_hr12Time();

        update_editText.setText(b);
        update_editText1.setText(a);
        updatetv_date.setText(c);
        updatetv_time.setText(d);
        updatephone_no.setText(e);
        update_idno.setText(f);
        update_hr12Time.setText(g);

        alertDialog = builder.create();
        alertDialog.show();

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int _ids = Integer.parseInt(f);
//                Intent intent = new Intent(getBaseContext(), MyBroadcastReceiver.class);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _ids, intent, PendingIntent.FLAG_ONE_SHOT);
//                pendingIntent.cancel();
//                assert alarmManager != null;
//                alarmManager.cancel(pendingIntent);
//
//
//                Toast.makeText(context, "Alarm canceled"+_ids, Toast.LENGTH_SHORT).show();
//                delete_this_msg(msgId);
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String up_msg = update_editText1.getText().toString();
                String up_name = update_editText.getText().toString();
                String up_3 = updatetv_date.getText().toString();
                String up_4 = updatetv_time.getText().toString();
                String up_5 = updatephone_no.getText().toString();
                String up_6 = update_idno.getText().toString();
                String up_7 = update_hr12Time.getText().toString();


                if (TextUtils.isEmpty(up_msg)) {
                    update_editText1.setError("Field Required!");
                    return;
                } else if (TextUtils.isEmpty(up_name)) {
                    update_editText.setError("Field Required!");
                    return;
                }
                updatemsg(msgId, up_name, up_msg, up_3, up_4, up_5,up_6,up_7);

            }
        });


    }

    private void delete_this_msg(String msgId) {


//        msgDatabase.child(msgId).removeValue();
//        alertDialog.dismiss();
        Toast.makeText(context, "Message deleted!", Toast.LENGTH_SHORT).show();
    }

    private void updatemsg(String msgId, String name, String msg, String date, String time, String contact_no,String idno,String hr12Time) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MsgDetails").child(uid).child(msgId);
        SaveMsgModelClass saveMsgModelClass = new SaveMsgModelClass(msgId, name, msg, date, time, contact_no,idno,hr12Time);
        databaseReference.setValue(saveMsgModelClass);
        alertDialog.dismiss();
        Toast.makeText(this, "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        CheckInternet();

    }

    public void CheckInternet() {
        if (!isNetworkAvailable()) {
            Intent NoInternetIntent = new Intent(WishlistActivity.this, InternetActivity.class);
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
    protected void onStart() {
        super.onStart();


        final ProgressDialog Dialog = new ProgressDialog(WishlistActivity.this);
        Dialog.setMessage("Fetching Data...");
        Dialog.setCancelable(false);
        Dialog.setCanceledOnTouchOutside(false);
        Dialog.show();

        msgDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                msgList.clear();

                if(dataSnapshot.exists()) {

                    for (DataSnapshot msgSnapshot : dataSnapshot.getChildren()) {
                        SaveMsgModelClass saveMsgModelClass = msgSnapshot.getValue(SaveMsgModelClass.class);
                        msgList.add(saveMsgModelClass);

                    }
                }
                else
                    {


                        Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
                    }

                Gson gson = new Gson();
                String s = gson.toJson(msgList);
                Log.e("*** Data - ",s);

                editor.putString("MyObject", s);

                editor.apply();

                Log.e("*** S Data - ",sharedPreferences.getString("MyObject",""));

                MsgList adapter = new MsgList(WishlistActivity.this, msgList);



                listView_msges.setAdapter(adapter);

                Dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


    }

}






