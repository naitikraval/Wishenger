package com.wishenger.android.wishengerr.Settings;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wishenger.android.wishengerr.R;

import java.util.List;

public class Autostartsettings extends AppCompatActivity {

    final String modelname= Build.MODEL;
    final String devicename= Build.BRAND;
    TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autostart_set);


        Button btn = (Button) findViewById(R.id.btn_permission);
        TextView dName= (TextView) findViewById(R.id.deviceName);
        txt2=findViewById(R.id.txt2);

        dName.setText(devicename +" "+ modelname);
        txt2.setText("Enable Autostart Permission for "+devicename+" "+modelname+" Device");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAutoStartup();
            }
        });


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void addAutoStartup() {

        try {
            Intent intent = new Intent();
            String manufacturer = android.os.Build.MANUFACTURER;

            if ("xiaomi".equalsIgnoreCase(manufacturer))
            {
                Toast.makeText(this, "Make sure permission is on", Toast.LENGTH_LONG).show();
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            }
            else if ("oppo".equalsIgnoreCase(manufacturer))
            {
                Toast.makeText(this, "Make sure permission is on", Toast.LENGTH_LONG).show();
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
            }
            else if ("vivo".equalsIgnoreCase(manufacturer))
            {
                Toast.makeText(this, "Make sure permission is on", Toast.LENGTH_LONG).show();
                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
            }
            else if ("Letv".equalsIgnoreCase(manufacturer))
            {
                Toast.makeText(this, "Make sure permission is on", Toast.LENGTH_LONG).show();
                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
            }
            else if ("Honor".equalsIgnoreCase(manufacturer))
            {
                Toast.makeText(this, "Make sure permission is on", Toast.LENGTH_LONG).show();
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
            }
            else if("oneplus".equalsIgnoreCase(manufacturer)) {
                Toast.makeText(this, "Make sure permission is on", Toast.LENGTH_LONG).show();
                intent.setComponent(new ComponentName("com.oneplus.security", "com.oneplus.security.chainlaunch.view.ChainLaunchAppListAct‌​ivity")); }

            else
            {
                Toast.makeText(this, "App works fine in your device!", Toast.LENGTH_SHORT).show();
            }

            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() > 0) {
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            Log.e("exc", String.valueOf(e));
        }
    }
}
